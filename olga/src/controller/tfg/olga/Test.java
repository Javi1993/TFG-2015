package controller.tfg.olga;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import json.tfg.olga.*;

@Path("/test")
public class Test {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MessageRS test (MessageRQ message) {
		return new MessageRS("007", "OK");
	}
}
