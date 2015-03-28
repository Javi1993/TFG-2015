package jms.tfg.eprail;

import java.io.Serializable;

public class InteraccionJMS {
	private javax.jms.ConnectionFactory factory = null;
	private javax.naming.InitialContext contextoInicial = null;
	private javax.jms.Destination cola = null;
	private javax.jms.Connection Qcon = null;
	private javax.jms.Session QSes = null;
	private javax.jms.MessageProducer Mpro = null;
	//private javax.jms.MessageConsumer Mcon = null;

	public void escrituraJMS(/*Object objeto, String codOP, int precio, String prodList, String unidades*/String mensaje) {

		try {

			contextoInicial = new javax.naming.InitialContext();

			factory = (javax.jms.ConnectionFactory) contextoInicial.lookup(InformacionProperties.getQCF());
			cola = (javax.jms.Destination) contextoInicial.lookup(InformacionProperties.getQueueAsincrona());

			Qcon = factory.createConnection("user","password");
			QSes = Qcon
					.createSession(false, javax.jms.Session.AUTO_ACKNOWLEDGE);

			Mpro = QSes.createProducer(cola);

			javax.jms.TextMessage men = QSes.createTextMessage();
//			javax.jms.ObjectMessage men = QSes.createObjectMessage();
			men.setStringProperty("mensaje", mensaje);
//			men.setStringProperty("cod_OP", codOP);//aniadimos el codigo de operacion
//			men.setIntProperty("precio", precio);//aniadimos el precio final 
//			men.setStringProperty("prodList", prodList);//añadimos la lista de IDs de los productos
//			men.setStringProperty("unidades", unidades);//añadimos la lista de unidades de los productos
//			men.setObject((Serializable) objeto);//añadimos el objeto pedido
			//men.setJMSCorrelationID(selector);
			Qcon.start();
			Mpro.send(men);

			this.Mpro.close();
			this.QSes.close();
			this.Qcon.close();

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
