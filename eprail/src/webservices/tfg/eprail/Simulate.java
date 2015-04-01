package webservices.tfg.eprail;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import json.tfg.eprail.MessageRQ;
import json.tfg.eprail.MessageRS;
import modeldata.tfg.eprailJPA.Project;

import org.codehaus.jackson.map.ObjectMapper;

import controller.tfg.eprail.ManagementProject;
import funciones.tfg.eprail.Funciones;

@Path("/simulate")
public class Simulate {

	private static final String targetURL = "http://localhost:8180/olga/rest/simulate";
	private Timer timer;
	private int cnt;
	private boolean sendEmail;
	private String projectId;
	Logger logger = Logger.getLogger("Log-FrontEnd");//log del proceso de simulacion  
	FileHandler fh;//fichero que guardara el log de la simulacion de un proyecto  

	public Simulate()
	{

	}

	public Simulate(String ptojectId, String logPath){
		try {
			sendEmail = false;
			cnt = 0;
			this.setProjectId(ptojectId);
			fh = new FileHandler(logPath+File.separator+"logs"+File.separator+this.getProjectId()+".log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
			fh.setFormatter(formatter); 
			timer = new Timer();
			timer.schedule(new RemindTask(), 0, 5000);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void processCase (MessageRQ message) {
		if(message.getCommand().equals("CASELAUNCHED"))
		{//se empezo la simulacion del proyecto
			ManagementProject mp = new ManagementProject();
			Project project = mp.buscarJPAProyectoId(Long.parseLong(message.getParameter()));
			project.setStatuscategory(mp.buscarJPAStatus((byte)1));
			mp.updateJPAProject(project);
		}
		logger.info("["+message.getNumSeq()+" - "+message.getCommand()+" - "+message.getParameter()+"]");  
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageRS finishCase (MessageRQ message) {
		if(message.getCommand().equals("CASEFINISHED"))
		{//se finalizo la simulacion del proyecto
			ManagementProject mp = new ManagementProject();
			Project project = mp.buscarJPAProyectoId(Long.parseLong(message.getParameter()));
			project.setStatuscategory(mp.buscarJPAStatus((byte)2));
			mp.updateJPAProject(project);
			logger.info("["+message.getNumSeq()+" - "+message.getCommand()+" - "+message.getParameter()+"]"); 
			return new MessageRS(message.getNumSeq(), "OK");
		}else{
			return null;
		}
	}

	public String launchcase()
	{
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Double.toString(Math.random()*999999999+100000000), "LAUNCHCASE", getProjectId());
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

			logger.info("["+messageRS.getNumSeq()+" - "+messageRS.getParameter()+"]"); 
			httpConnection.disconnect();

			//actualizamos el estado del proyecto
			ManagementProject mp = new ManagementProject();
			Project project = mp.buscarJPAProyectoId(Long.parseLong(getProjectId()));
			if(messageRS.getNumSeq().equals(messageRQ.getNumSeq()) && messageRS.getParameter().equals("OK"))
			{
				project.setStatuscategory(mp.buscarJPAStatus((byte)0));
				mp.updateJPAProject(project);
				return messageRS.getNumSeq();
			}else{
				project.setStatuscategory(mp.buscarJPAStatus((byte)3));
				mp.updateJPAProject(project);
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
			if(launchcase()!=null)
			{//el servidor olga metio a la cola el proyecto
				timer.cancel();
				timer.purge();
				return;
			}else
			{//no hubo respuesta de OlgaNG o esta fue erronea
				setCnt(getCnt()+1);
				if(getCnt()>=10 && !isSendEmail())
				{//mandamos email notificando del comportamiento del receptor
					Funciones.sendEmail("Eprail: OlgaNG no recoge proyecto", "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
							+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>OlgaNG no recoge proyecto</h2><br>"
							+ "<p>La aplicaci&oacute;n web intenta simular el proyecto-"+getProjectId()+", pero el servidor OlgaNG no responde. Revise su estado con urgencia.</p>"
							+"</div></body></html>", "100290698@alumnos.uc3m.es");
					setSendEmail(true);
				}
			}
		}
	}
}
