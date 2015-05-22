package webservices.tfg.simulacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import funciones.tfg.simulacion.Funciones;
import jms.tfg.simulacion.InteraccionJMS;
import json.tfg.simulacion.MessageRQ;
import json.tfg.simulacion.MessageRS;

@Path("/simulate")
public class Simulate {

	private static final String targetURL = "http://localhost:8080/aplicacion/rest/simulate";
	Logger logger = Logger.getLogger("Log-Simulacion");//log del proceso de simulacion
	private Timer timer;
	private int cnt;
	private boolean sendEmail;
	private String projectId;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageRS launchCase (MessageRQ message) {
		if(message.getCommand().equals("LAUNCHCASE"))
		{//mesanje del fornt end pidiendo simular un projecto, lo añadimos a la cola de espera
			logger.info("["+message.getNumSeq()+" - "+message.getCommand()+" - "+message.getParameter()+"]");  
			InteraccionJMS jms = new InteraccionJMS();
			jms.escrituraJMS(message.getParameter());
			return new MessageRS(message.getNumSeq(), "OK");
		}else{//no reconoce quien le envia el mensaje
			return null;
		}
	}

	public void processCase (String comand, String projectId){
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("PUT");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Double.toString(Math.random()*999999999+100000000), comand, projectId);
			ObjectMapper objectMapper = new ObjectMapper();
			String authRQString = objectMapper.writeValueAsString(messageRQ);

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(authRQString.getBytes());
			outputStream.close();

			logger.info("["+messageRQ.getNumSeq()+" - "+messageRQ.getCommand()+" - "+messageRQ.getParameter()+"]");  

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			}

			httpConnection.disconnect();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			System.err.println("No hubo respuesta por parte del Front-End para el comando "+comand+". ERROR: "+e.getMessage());
		}
	}

	public void finish (String projectId)
	{
		sendEmail = false;
		cnt = 0;
		this.setProjectId(projectId);
		timer = new Timer();
		timer.schedule(new RemindTask(), 0, 2000);
	}

	public String finishedCase (String projectId){
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Double.toString(Math.random()*999999999+100000000), "CASEFINISHED", projectId);
			ObjectMapper objectMapper = new ObjectMapper();
			String authRQString = objectMapper.writeValueAsString(messageRQ);

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(authRQString.getBytes());
			outputStream.flush();

			logger.info("["+messageRQ.getNumSeq()+" - "+messageRQ.getCommand()+" - "+messageRQ.getParameter()+"]");

			if (httpConnection.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ httpConnection.getResponseCode());
			}
			BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
					(httpConnection.getInputStream())));
			String output;
			MessageRS messageRS = null;

			while ((output = responseBuffer.readLine()) != null) {
				ObjectMapper mapper = new ObjectMapper();
				messageRS = mapper.readValue(output, MessageRS.class);
			}

			httpConnection.disconnect();

			logger.info("["+messageRS.getNumSeq()+" - "+messageRS.getParameter()+"]");
			if(messageRS.getNumSeq().equals(messageRQ.getNumSeq()) && messageRS.getParameter().equals("OK"))
			{

				return messageRS.getNumSeq();
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("No hubo respuesta por parte del Front-End para el comando CASEFINISHED. ERROR: "+e.getMessage());
		} catch (RuntimeException e) {
			System.err.println("No hubo respuesta por parte del Front-End para el comando CASEFINISHED. ERROR: "+e.getMessage());
		}
		return null;
	}

	/**
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	/**
	 * @return the sendEmail
	 */
	public boolean isSendEmail() {
		return sendEmail;
	}

	/**
	 * @param sendEmail the sendEmail to set
	 */
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	/**
	 * @return the projectId
	 */
	public String getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	class RemindTask extends TimerTask {
		public void run() {
			if(finishedCase(getProjectId())!=null)
			{//el servidor de simulacios metio a la cola el proyecto
				timer.cancel();
				timer.purge();
				return;
			}else
			{//no hubo respuesta de la aplicacion o esta fue erronea
				setCnt(getCnt()+1);
				if(getCnt()>=10 && !isSendEmail())
				{//mandamos email notificando del comportamiento del receptor
					Funciones.sendEmail("TFG: Aplicación web no recibe simulación", "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
							+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Aplicación web no recibe resultado</h2><br>"
							+ "<p>El servidor de simulaciones ha terminado de simular el proyecto-"+getProjectId()+", pero la aplicaci&oacute;n web no recibe el resultado o no avisa de haberlo recibido. Revise su estado con urgencia.</p>"
							+"</div></body></html>", "100290698@alumnos.uc3m.es");
					setSendEmail(true);
				}
			}
		}
	}
}
