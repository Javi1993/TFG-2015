package funciones.tfg.eprail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import json.tfg.eprail.*;
import org.codehaus.jackson.map.ObjectMapper;

public class Comunicacion {

	private static final String targetURL = "http://localhost:8080/olga/rest/test";
	
	public static String testRest ()
	{
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");
			
			MessageRQ messageRQ = new MessageRQ("00000", "hola", "OK");
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
			
			return messageRS.getNumSeq();

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
