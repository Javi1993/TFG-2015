package funciones.tfg.eprail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import json.tfg.eprail.*;
import org.codehaus.jackson.map.ObjectMapper;

public class ComunicacionFront {

	private static final String targetURL = "http://localhost:8180/olga/rest/heartbeat";
	private Timer timer;
	private boolean olga;
	
	public ComunicacionFront(){
		timer = new Timer();
		timer.schedule(new RemindTask(), 1000, 10000);
	}
	
	public static String heartBeat ()
	{
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Double.toString(Math.random()*999999999+100000000), "IAMALIVE", "FRONTEND");
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
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @return the cnt
	 */
	public boolean getOlga() {
		return olga;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public void setOlga(boolean olga) {
		this.olga = olga;
	}
	
	class RemindTask extends TimerTask {
		public void run() {
			if(ComunicacionFront.heartBeat()!=null)
			{
				//resetear contador
				setOlga(true);
			}else{
				//no recibio respuesta de OLGA
				
				//hacerse CONTADOR Y MANDAR EMAIL A LOS X FALLOS SEGUIDOS //resetear contador
				setOlga(false);
			}
		}
	}
}
