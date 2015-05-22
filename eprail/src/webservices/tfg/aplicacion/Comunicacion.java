package webservices.tfg.aplicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

import json.tfg.aplicacion.*;

import org.codehaus.jackson.map.ObjectMapper;

import funciones.tfg.aplicacion.Funciones;


public class Comunicacion {

	private static final String targetURL = "http://localhost:8180/simulacion/rest/heartbeat";
	private Timer timer;
	private boolean server;
	private int cnt;
	private boolean sendEmail;

	public Comunicacion(){
		sendEmail = false;
		cnt = 0;
		server = false;
		timer = new Timer();
		timer.schedule(new RemindTask(), 1000, 2000);
	}

	public static String heartBeat ()
	{
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Long.toString((long)Math.random()*999999999+100000000), "IAMALIVE", "FRONTEND");
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
			System.err.println("No se pudo comunicar con el servidor de simulaciones. Error: "+e.getMessage());
		} catch (RuntimeException e) {
			System.err.println("No se pudo comunicar con el servidor de simulaciones. Error: "+e.getMessage());
		}
		return null;
	}

	/**
	 * @return the cnt
	 */
	public boolean getServer() {
		return server;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setServer(boolean server) {
		this.server = server;
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

	class RemindTask extends TimerTask {
		public void run() {
			if(heartBeat()!=null)
			{//hubo respuesta del servidor
				setCnt(0);
				setServer(true);
			}else
			{//no hubo respuesta del servidor o esta fue erronea
				setServer(false);
				setCnt(getCnt()+1);
				if(getCnt()>=10 && !isSendEmail())
				{//mandamos email notificando del comportamiento del receptor
					Funciones.sendEmail("TFG: Servidor de simulaciones no responde", "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
							+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Servidor de simulaciones no responde</h2><br>"
							+ "<p>La aplicaci&oacute;n web intenta comunicarse el servidor de simulaciones pero no recibe respuesta o esta no es correcta. Revise su estado con urgencia.</p>"
							+"</div></body></html>", "100290698@alumnos.uc3m.es");
					setSendEmail(true);
				}
			}
		}
	}
}
