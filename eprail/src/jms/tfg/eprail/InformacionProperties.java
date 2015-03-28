package jms.tfg.eprail;

import java.util.*;

public class InformacionProperties {

	private static String strQCF;

	private static String strQueueAsincrona;

	private static final String nombreProperties = "InfoAplicacion";

	// **************************************************
	public InformacionProperties() {
		super();
	}

	// **************************************************
	public static String getQCF() {

		if (strQCF == null)
			cagarProperties();

		return strQCF;
	}

	// **************************************************
	public static String getQueueAsincrona() {

		if (strQueueAsincrona == null)
			cagarProperties();

		return strQueueAsincrona;
	}

	// **************************************************
	private static void cagarProperties() throws MissingResourceException {

		PropertyResourceBundle appProperties = null;

		try {

			appProperties = (PropertyResourceBundle) PropertyResourceBundle
					.getBundle(nombreProperties);

			strQCF = appProperties.getString("Info.strQCF");
			strQueueAsincrona = appProperties.getString("Info.strQueueAsincrona");

		} catch (MissingResourceException e) {
			throw e;
		}
	}
}
