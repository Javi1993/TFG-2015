package funciones.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.Part;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import controller.tfg.eprail.ManagementProject;
import modeldata.tfg.eprailJPA.Project;
import modeldata.tfg.eprailJPA.Sharing;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

public class Funciones {

	private static final String SAVE_DIR = "ONGFiles";
	private static final String TEMP_DIR = "temp";

	/**
	 * Envia un email a un destino con un mensaje y asunto
	 * @param asunto
	 * @param mensaje
	 * @param to
	 */
	public static void sendEmail(String asunto, String mensaje, String to) {

		final String username = "100290698@alumnos.uc3m.es";
		final String password = "Javi.93";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(asunto);
			message.setContent(mensaje,"text/html");

			Transport.send(message);

			System.out.println("Sent message successfully....");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/** 
	 * Encripta un String con el algoritmo MD5. 
	 * @return String - cadena a encriptar
	 * @throws Exception 
	 */ 
	public static String cryptMD5(String textoPlano)
	{
		try
		{
			final char[] HEXADECIMALES = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

			textoPlano = "0208"+textoPlano;//metemos unos nuemros antes de la contraseï¿½a en claro

			MessageDigest msgdgt = MessageDigest.getInstance("MD5");
			byte[] bytes = msgdgt.digest(textoPlano.getBytes());
			StringBuilder strCryptMD5 = new StringBuilder(2 * bytes.length);
			for (int i = 0; i < bytes.length; i++)
			{//ciframos
				int low = (int)(bytes[i] & 0x0f);
				int high = (int)((bytes[i] & 0xf0) >> 4);
				strCryptMD5.append(HEXADECIMALES[high]);
				strCryptMD5.append(HEXADECIMALES[low]);
			}
			return strCryptMD5.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	/**
	 * Obtiene el nombre y descripcion del proyecto a partir del XML
	 * @param part - file
	 * @return
	 */
	public static void getFileName(String path, Project p) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(path + File.separator + "Manifest.xml");
			XPathFactory xPathfactory = XPathFactory.newInstance();
			XPath xpath = xPathfactory.newXPath();

			XPathExpression expr = xpath.compile("ONGProjectManifest/@IdProject");
			p.setProjectName((String) expr.evaluate(doc, XPathConstants.STRING));
			expr = xpath.compile("ONGProjectManifest/@Description");
			p.setProjectDescription((String) expr.evaluate(doc, XPathConstants.STRING));
			File file = new File(path + File.separator + "Manifest.xml");
			file.delete();
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Cambia los permisos de un usuario sobre un proyecto
	 * @param sharing
	 * @param perm
	 */
	public static void asignarPermiso(Sharing sharing, char perm)
	{
		Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
		ManagementProject mp = new ManagementProject();
		switch(perm)
		{
		case 'R':
			if(sharing.getAllowRecalculate()==0)
			{
				sharing.setAllowRecalculate((byte)1);
				sharing.setDateChanged(currentTimestamp);
				mp.updateJPASharing(sharing);
			}
			break;
		case 'D':
			if(sharing.getAllowDownload()==0)
			{
				sharing.setAllowDownload((byte)1);
				sharing.setDateChanged(currentTimestamp);
				mp.updateJPASharing(sharing);	
			}
			break;
		case 'S':
			if(sharing.getAllowShare()==0)
			{
				sharing.setAllowShare((byte)1);
				sharing.setDateChanged(currentTimestamp);
				mp.updateJPASharing(sharing);	
			}
			break;
		case 'X':
			if(sharing.getAllowDelete()==0)
			{
				sharing.setAllowDelete((byte)1);
				sharing.setDateChanged(currentTimestamp);
				mp.updateJPASharing(sharing);
			}
			break;
		default:
			System.out.println("Permiso "+perm+" no reconocido.");
		}
	}

	/**
	 * Valida un XML contra su esquema XSD
	 * @param path - ruta del xml
	 * @return booleano con resultado
	 */
	public static boolean validarXML(String path, String applicationPath)
	{
		try
		{
			// crear y configurar la factory de parsers de documentos XML
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);  // activar soporte para namespaces

			// cargar el documento XML
			DocumentBuilder parser = dbf.newDocumentBuilder();
			Document doc = parser.parse(new File(path + File.separator + "Manifest.xml"));

			// crear una SchemaFactory preparada para interpretar esquemas XML W3C
			SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			// cargar el esquema XSD
			Schema schema = sf.newSchema(new File(applicationPath + File.separator + "Manifest.xsd"));

			// crear el objeto validator, que será el responsable de validar el XML
			Validator validator = schema.newValidator();

			// validar el documento XML
			validator.validate(new DOMSource(doc));

			// si se llega a este punto, el documento es válido
			System.out.println("MANIFEST.XML VÁLIDO");
			return true;
		}
		catch (SAXException e)
		{
			// esta excepción indica fallo de validación
			System.err.println("DOCUMENTO INVÁLIDO");
			e.printStackTrace();
		}
		catch (ParserConfigurationException e)
		{
			// errores en la configuración del parser
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// errores de lectura
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Dado un archivo de subida (.ongf, .zip, .rar) comprueba si contiene el archivo mainfest.xml
	 * @param ongf
	 * @return True- existe | False- no
	 */
	public static boolean getManifest(Part ongf, long uid, String applicationPath)
	{
		boolean foundManifest = false;
		try {
			ZipInputStream zis = new ZipInputStream(ongf.getInputStream());
			ZipEntry entrada;
			while (null != (entrada=zis.getNextEntry()) ){
				if(entrada.getName().endsWith("Manifest.xml"))
				{

					//creamos el directorio donde se procesara el mainfest.xml
					String path = applicationPath + File.separator + TEMP_DIR + File.separator + uid;
					File fileSaveDir = new File(path);
					if (!fileSaveDir.exists()) {
						fileSaveDir.mkdirs();
					}

					FileOutputStream fos = new FileOutputStream(path + File.separator + "Manifest.xml");
					int leido;
					byte [] buffer = new byte[1024];
					while (0<(leido=zis.read(buffer))){
						fos.write(buffer,0,leido);
					}
					fos.close();

					foundManifest = validarXML(path, applicationPath);

				}
			}
			zis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return foundManifest;
	}

	/**
	 * Extrae en carpetas temporales el contenido HTML de los proyectos
	 * @param applicationPath
	 * @param uid
	 */
	public static void extraerHTML(String applicationPath, long uid) {
		try {
			File dir = new File(applicationPath + File.separator + SAVE_DIR + File.separator + uid);//directorio donde estan los proyectos del usuario
			if (!dir.exists()) {
				dir.mkdirs();
			}
			//creamos el directorio temporal del usuario
			String path = applicationPath + File.separator + TEMP_DIR + File.separator + uid;
			File tempDir = new File(path);
			if (!tempDir.exists()) {
				tempDir.mkdirs();
			}

			String[] files = dir.list();
			for(String s: files){//descomprimimos el htmlde cada proyecto
				//creamos el directorio del proyecto y sus hijos
				String pathHTML = path + File.separator + s;
				File HTMLDir = new File(pathHTML);
				if (!HTMLDir.exists()) {
					HTMLDir.mkdirs();
				}

				ZipInputStream zis = new ZipInputStream(new FileInputStream(dir.getPath() + File.separator + s));
				ZipEntry entrada;
				while (null != (entrada=zis.getNextEntry()) )
				{
					if(entrada.getName().startsWith("html/"))
					{
						File fileToWrite = new File(HTMLDir, entrada.getName());
						File folder = fileToWrite.getParentFile();
						if(!folder.mkdirs() && !folder.exists()) {
							fileToWrite.mkdirs();
							folder.mkdirs();
						}

						if(!entrada.isDirectory())
						{
							FileOutputStream fos = new FileOutputStream(fileToWrite);
							int leido;
							byte [] buffer = new byte[1024];
							while (0<(leido=zis.read(buffer))){
								fos.write(buffer,0,leido);
							}
							fos.close();
						}

						if(validarResultadoHtml(fileToWrite.getName()))
						{
							String ruta = TEMP_DIR + File.separator + uid + File.separator + s;
							modificarRutasContent(applicationPath, ruta, fileToWrite.getName());
						}
					}
				}
				zis.close();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Valida si el HTML con el resultado esta correctamente nombrado
	 * @param name - Nombre del archivo
	 * @return booleano con resultado
	 */
	public static boolean validarResultadoHtml(String name)
	{
		Pattern pat = Pattern.compile("^(index|content)(\\.)(html|xhtml|htm)$", Pattern.CASE_INSENSITIVE);
		Matcher mat = pat.matcher(name);
		if (mat.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @param applicationPath - ruta de la app
	 * @param ruta - ruta del proyecto en la carpeta temp del usuario
	 * @param name - nombre del archivo HTML
	 */
	public static void modificarRutasContent(String applicationPath, String ruta, String name)
	{
		try 
		{//modificamos las rutas relativas para que se muestren bien los enlaces e hipervinculos del html
			File input = new File(applicationPath + File.separator + ruta + File.separator + "html" + File.separator + name);
			org.jsoup.nodes.Document doc = Jsoup.parse(input, "UTF-8");
			Elements aTag = doc.select("a[href]");
			Elements imgTag = doc.select("img[src]");
			for(Element link : aTag)
			{//editamos las rutas de la etiqueta <a>
				String relative = link.attr("href");
				relative = relative.replaceAll("/", Matcher.quoteReplacement(File.separator));
				link.removeAttr("href");
				link.attr("href", File.separator + "eprail" + File.separator + ruta + File.separator + "html" + File.separator + relative);
			}
			for(Element link : imgTag)
			{//editamos las rutas de la etiqueta img
				String relative = link.attr("src");
				relative = relative.replaceAll("/", Matcher.quoteReplacement(File.separator));
				link.removeAttr("src");
				link.attr("src", File.separator + "eprail" + File.separator + ruta + File.separator + "html" + File.separator + relative);
			}

			Writer writer = new PrintWriter(input, "UTF-8");
			writer.write(doc.outerHtml());
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Funcion que recibe un directorio y borra todo su contenido
	 * @param directorio - directorio a borrar
	 */
	public static void borrarDirectorio (File directorio){
		File[] hijos = directorio.listFiles();
		for (int x=0;x<hijos.length;x++){
			if (hijos[x].isDirectory()) {
				borrarDirectorio(hijos[x]);
			}
			hijos[x].delete();
		}
	}
}
