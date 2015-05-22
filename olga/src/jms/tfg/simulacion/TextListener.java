package jms.tfg.simulacion;

import javax.jms.MessageListener;
import javax.jms.Message;
import javax.jms.TextMessage;

import webservices.tfg.simulacion.Simulate;

public class TextListener implements MessageListener {


	public void onMessage(Message message) {
		TextMessage msg = null;
		try {
			if (message instanceof TextMessage) {
				msg = (TextMessage) message;
				String id = msg.getText();
				Simulate sm = new Simulate();
				Thread.sleep((long) (Math.random()*6000+1000));//espera en cola
				sm.processCase("CASELAUNCHED", id);
				Thread.sleep((long) (Math.random()*20000+8000));//espera por calculo
				sm.finish(id);
			} else {
				System.err.println("Message is not a ObjectMessage");
			}
		} catch (Throwable t) {
			System.err.println("Exception in onMessage():" + t.getMessage());
		}
	}
}