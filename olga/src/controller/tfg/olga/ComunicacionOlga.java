package controller.tfg.olga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import json.tfg.olga.*;
import org.codehaus.jackson.map.ObjectMapper;

public class ComunicacionOlga {

	private static final String targetURL = "http://localhost:8080/eprail/rest/heartbeat";
	private static int cnt = 0;

	public static String heartBeat ()
	{
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Double.toString(Math.random()*999999999+100000000), "IAMALIVE", "OLGANG");
			ObjectMapper objectMapper = new ObjectMapper();
			String authRQString = objectMapper.writeValueAsString(messageRQ);
			//System.out.println("AuthorizationRQ: "+authRQString);

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
			//System.out.println("***************************");

			while ((output = responseBuffer.readLine()) != null) {
				//System.out.println("AuthorizationRS JSON:" +output);
				ObjectMapper mapper = new ObjectMapper();
				messageRS = mapper.readValue(output, MessageRS.class);
				//System.out.println("AuthorizationRS Object:" +authRS);
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

	public void comprobarFrontEnd()
	{

			try{
				Thread.sleep(10000);
				if(heartBeat()==null)
				{//no recibio respuesta del FRONT-END
					setCnt(getCnt()+1);
					if(getCnt()>=10)
					{
						//mandar correo
						setCnt(0);
					}
				}	
			}catch(Exception e){
				e.printStackTrace();
			}

	}

	/**
	 * @return the cnt
	 */
	public static int getCnt() {
		return cnt;
	}

	/**
	 * @param cnt the cnt to set
	 */
	public static void setCnt(int cnt) {
		ComunicacionOlga.cnt = cnt;
	}
}
