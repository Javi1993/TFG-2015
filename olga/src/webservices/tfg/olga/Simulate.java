package webservices.tfg.olga;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import jms.tfg.olga.InteraccionJMS;
import json.tfg.olga.MessageRQ;
import json.tfg.olga.MessageRS;

@Path("/simulate")
public class Simulate {

	private static final String targetURL = "http://localhost:8080/eprail/rest/simulate";
	Logger logger = Logger.getLogger("Log-OlgaNG");//log del proceso de simulacion

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
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return null;
	}
}
