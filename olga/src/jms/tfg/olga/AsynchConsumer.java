package jms.tfg.olga;

import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.Connection;
import javax.jms.Session;
import javax.jms.MessageConsumer;
import javax.jms.JMSException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AsynchConsumer {
	
	private InitialContext contextoInicial = null;
	private ConnectionFactory factory = null;
	private Destination cola = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private TextListener listener = null;

	public void lecturaAsynch() {

		try {

			contextoInicial = new InitialContext();
			System.out.println("AAAAAAAAAAAAAAAAAAa");
			factory = (javax.jms.ConnectionFactory) 
					contextoInicial.lookup(InformacionProperties.getQCF());
			System.out.println("BBBBBBBBBBBBB");
			cola = (javax.jms.Destination) 
					contextoInicial.lookup(InformacionProperties.getQueueAsincrona());
			connection = factory.createConnection("user", "password");
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			consumer = session.createConsumer(cola);
			listener = new TextListener();
			consumer.setMessageListener(listener);
			System.out.println("LecturaAsincronaServlet -->Listener levantado");
			connection.start();

		} catch (NamingException ne) {
			System.out
					.println("lecturaAsynch.NamingException....JHC *************************************** Error de JMS: "
							+ ne.getMessage());

		} catch (JMSException e) {
			System.out
					.println("lecturaAsynch.....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().getMessage());
			System.out
					.println("lecturaAsynch.....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().toString());
		}
	}
}
