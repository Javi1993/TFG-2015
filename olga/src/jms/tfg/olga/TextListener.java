package jms.tfg.olga;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import modeldata.tfg.eprailJPA.Project;
import controller.tfg.olga.Comunicacion;


public class TextListener implements MessageListener {


	public void onMessage(Message message) {
		ObjectMessage msg = null;

		try {
			if (message instanceof ObjectMessage) {
				msg = (ObjectMessage) message;    
				System.out.println("A");
				Project mensaje = (Project)msg.getObject();
				System.out.println("B");
				Comunicacion.simular(mensaje);
			} else {
				System.err.println("Message is not a ObjectMessage");
			}
		} catch (Throwable t) {
        	System.err.println("Exception in onMessage():" + t.getMessage());
        }
	}
}