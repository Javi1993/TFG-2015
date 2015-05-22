package jms.tfg.simulacion;

import webservices.tfg.simulacion.Simulate;

public class InteraccionJMS {
	private javax.jms.ConnectionFactory factory = null;
	private javax.naming.InitialContext contextoInicial = null;
	private javax.jms.Destination cola = null;
	private javax.jms.Connection Qcon = null;
	private javax.jms.Session QSes = null;
	private javax.jms.MessageProducer Mpro = null;

	public void escrituraJMS(String mensaje) {
		try {

			contextoInicial = new javax.naming.InitialContext();

			factory = (javax.jms.ConnectionFactory) contextoInicial.lookup(InformacionProperties.getQCF());
			cola = (javax.jms.Destination) contextoInicial.lookup(InformacionProperties.getQueueAsincrona());

			Qcon = factory.createConnection("user","password");
			QSes = Qcon
					.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			Mpro = QSes.createProducer(cola);

			javax.jms.TextMessage men = QSes.createTextMessage();
			men.setText(mensaje);
			Qcon.start();
			Mpro.send(men);
			
			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();
			
			Simulate sm = new Simulate();//notificamos al front-end que el proyecto se añadio a la cola
			sm.processCase("CASEINQUEUE",mensaje);
		} catch (javax.jms.JMSException e) {
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().getMessage());
			System.out
					.println(".....JHC *************************************** Error de JMS: "
							+ e.getLinkedException().toString());
		} catch (Exception e) {
			System.out
					.println("JHC *************************************** Error Exception: "
							+ e.getMessage());
		}
	}
}
