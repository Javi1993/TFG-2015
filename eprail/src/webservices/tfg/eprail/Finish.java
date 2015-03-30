package webservices.tfg.eprail;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import json.tfg.eprail.MessageRQ;
import json.tfg.eprail.MessageRS;
import modeldata.tfg.eprailJPA.Project;
import controller.tfg.eprail.ManagementProject;

@Path("/finish")
public class Finish {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageRS finishCase (MessageRQ message) {
		if(message.getCommand().equals("CASEFINISHED"))
		{//se finalizo la simulacion del proyecto
			//ACTUALZIAR ESTADO EN LA BBDD
			ManagementProject mp = new ManagementProject();
			Project project = mp.buscarJPAProyectoId(Long.parseLong(message.getParameter()));
			project.setStatuscategory(mp.buscarJPAStatus((byte)2));
			mp.updateJPAProject(project);
			return new MessageRS(message.getNumSeq(), "OK");
		}else{
			return null;
		}
	}
}
