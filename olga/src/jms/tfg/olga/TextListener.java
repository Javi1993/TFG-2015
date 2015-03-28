package jms.tfg.olga;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.ObjectMessage;
//import javax.jms.JMSException;
import javax.jms.TextMessage;


public class TextListener implements MessageListener {


	public void onMessage(Message message) {
		//		ObjectMessage msg = null;
		TextMessage msg = null;

		try {
			if (message instanceof /*ObjectMessage*/TextMessage) {
				msg = (TextMessage) message;             


				String mensaje = msg.getStringProperty("mensaje");//test

				System.out.println("++++++++++++++++++++++----- "+mensaje);
			} else {
				System.err.println("Message is not a TextMessage");
			}
		} /*catch (JMSException e) {
            System.err.println("JMSException in onMessage(): " + e.toString());
        } */catch (Throwable t) {
        	System.err.println("Exception in onMessage():" + t.getMessage());
        }
	}
}