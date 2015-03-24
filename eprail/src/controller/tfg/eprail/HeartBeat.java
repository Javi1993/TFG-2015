package controller.tfg.eprail;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import json.tfg.eprail.*;

@Path("/heartbeat")
public class HeartBeat {

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageRS test (MessageRQ message) {
		if(message.getParameter().equals("OLGANG")){
			return new MessageRS(message.getNumSeq(), "OK");
		}else{//no reconoce quien le envia el mensaje
			return null;
		}
	}
}
