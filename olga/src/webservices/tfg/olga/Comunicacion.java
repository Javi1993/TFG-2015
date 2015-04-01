package webservices.tfg.olga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import json.tfg.olga.*;

import org.codehaus.jackson.map.ObjectMapper;

import funciones.tfg.olga.Funciones;

public class Comunicacion {

	private static final String targetURL = "http://localhost:8080/eprail/rest/heartbeat";
	private int cnt;
	private Timer timer;
	private boolean sendEmail;

	public Comunicacion(){
		sendEmail = false;
		cnt = 0;
		timer = new Timer();
		timer.schedule(new RemindTask(), 5000, 15000);
	}

	/**
	 * Manda un mensaje al front-end avisando de que esta operativo
	 * @return numero de sequencia o nulo en caso de no recibir respuesta o de ser erronea esta
	 */
	public static String heartBeat ()
	{
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Long.toString((long) Math.random()*999999999+100000000), "IAMALIVE", "OLGANG");
			ObjectMapper objectMapper = new ObjectMapper();
			String authRQString = objectMapper.writeValueAsString(messageRQ);

			OutputStream outputStream = httpConnection.getOutputStream();
			outputStream.write(authRQString.getBytes());
			outputStream.flush();

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

			if(messageRS.getNumSeq().equals(messageRQ.getNumSeq()) && messageRS.getParameter().equals("OK"))
			{
				return messageRS.getNumSeq();
			}else{
				return null;
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("No se pudo comunicar con el front-end. Error: "+e.getMessage());
		} catch (RuntimeException e) {
			System.err.println("No se pudo comunicar con el front-end. Error: "+e.getMessage());
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
	 * @param cnt the sendEmail to set
	 */
	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	class RemindTask extends TimerTask {
		public void run() {
			if(heartBeat()!=null)
			{//hubo respuesta correcta del front-end
				setCnt(0);
			}else
			{//no recibio respuesta del FRONT-END o esta fue erronea
				setCnt(getCnt()+1);
				if(getCnt()>=10 && !isSendEmail())
				{//mandamos email notificando del comportamiento del receptor
					Funciones.sendEmail("Eprail: Aplicación web no responde", "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
							+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>La aplicaci&oacute;n web no responde</h2><br>"
							+ "<p>El servidor OlgaNG intenta comunicarse con la aplicaci&oacute;n web pero no recibe respuesta o esta no es correcta. Revise su estado con urgencia.</p>"
							+"</div></body></html>", "100290698@alumnos.uc3m.es");
					setSendEmail(true);
				}
			}
		}
	}
}
