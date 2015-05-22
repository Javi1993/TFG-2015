package webservices.tfg.aplicacion;

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

import json.tfg.aplicacion.MessageRQ;
import json.tfg.aplicacion.MessageRS;
import modeldata.tfg.aplicacionJPA.Project;

import org.codehaus.jackson.map.ObjectMapper;

import controller.tfg.aplicacion.ManagementProject;
import funciones.tfg.aplicacion.Funciones;

@Path("/simulate")
public class Simulate {

	private static final String targetURL = "http://localhost:8180/simulacion/rest/simulate";
	private Timer timer;
	private int cnt;
	private boolean sendEmail;
	private String projectId;
	Logger logger = Logger.getLogger("Log-FrontEnd");//log del proceso de simulacion  
	FileHandler fh;//fichero que guardara el log de la simulacion de un proyecto  

	public Simulate()
	{

	}

	public Simulate(String projectId, String logPath){
		try {
			sendEmail = false;
			cnt = 0;
			this.setProjectId(projectId);
			String logsFile = logPath+File.separator+"logs";
			File fileSaveDir = new File(logsFile);
			if (!fileSaveDir.exists()) {
				fileSaveDir.mkdirs();
			}
			fh = new FileHandler(logsFile+File.separator+this.getProjectId()+".log");
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
		logger.info("["+message.getNumSeq()+" - "+message.getCommand()+" - "+message.getParameter()+"]");  
		ManagementProject mp = new ManagementProject();
		Project project = mp.buscarJPAProyectoId(Long.parseLong(message.getParameter()));
		if(message.getCommand().equals("CASELAUNCHED"))
		{//se empezo la simulacion del proyecto
			project.setStatuscategory(mp.buscarJPAStatus((byte)1));
			mp.updateJPAProject(project);
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageRS finishCase (MessageRQ message) {
		logger.info("["+message.getNumSeq()+" - "+message.getCommand()+" - "+message.getParameter()+"]"); 
		ManagementProject mp = new ManagementProject();
		Project project = mp.buscarJPAProyectoId(Long.parseLong(message.getParameter()));
		if(message.getCommand().equals("CASEFINISHED"))
		{//se finalizo la simulacion del proyecto
			project.setStatuscategory(mp.buscarJPAStatus((byte)2));
			mp.updateJPAProject(project);
			return new MessageRS(message.getNumSeq(), "OK");
		}else{
			System.out.println("PEENEE");
			project.setStatuscategory(mp.buscarJPAStatus((byte)3));
			mp.updateJPAProject(project);
			return null;
		}
	}

	public String launchcase()
	{
		ManagementProject mp = new ManagementProject();
		Project project = mp.buscarJPAProyectoId(Long.parseLong(getProjectId()));
		try {
			URL targetUrl = new URL(targetURL);
			HttpURLConnection httpConnection = (HttpURLConnection) targetUrl.openConnection();
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("POST");
			httpConnection.setRequestProperty("Content-Type", "application/json");

			MessageRQ messageRQ = new MessageRQ(Double.toString(Math.random()*999999999+100000000), "LAUNCHCASE", getProjectId());
			logger.info("["+messageRQ.getNumSeq()+" - "+messageRQ.getCommand()+" - "+messageRQ.getParameter()+"]"); 
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

			logger.info("["+messageRS.getNumSeq()+" - "+messageRS.getParameter()+"]"); 
			httpConnection.disconnect();

			//actualizamos el estado del proyecto
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
			project.setStatuscategory(mp.buscarJPAStatus((byte)3));
			mp.updateJPAProject(project);
			e.printStackTrace();
		} catch (IOException e) {
			project.setStatuscategory(mp.buscarJPAStatus((byte)3));
			mp.updateJPAProject(project);
			e.printStackTrace();
		} catch (RuntimeException e) {
			project.setStatuscategory(mp.buscarJPAStatus((byte)3));
			mp.updateJPAProject(project);
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
			{//el servidor de simulaciones metio a la cola el proyecto
				timer.cancel();
				timer.purge();
				return;
			}else
			{//no hubo respuesta del servidor de simulaciones o esta fue erronea
				setCnt(getCnt()+1);
				if(getCnt()>=10 && !isSendEmail())
				{//mandamos email notificando del comportamiento del receptor
					Funciones.sendEmail("TFG: Servidor de simulaciones no recoge proyecto", "<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
							+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Servidor de simulaciones no recoge proyecto</h2><br>"
							+ "<p>La aplicaci&oacute;n web intenta simular el proyecto-"+getProjectId()+", pero el servidor de simulaciones no responde. Revise su estado con urgencia.</p>"
							+"</div></body></html>", "100290698@alumnos.uc3m.es");
					setSendEmail(true);
				}
			}
		}
	}
}
