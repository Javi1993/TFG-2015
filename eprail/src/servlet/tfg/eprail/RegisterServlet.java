package servlet.tfg.eprail;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Properties;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
//import javax.activation.*;
//import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.sql.DataSource;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String URL_PARAM = "jdbc:mysql://localhost:3306/eprail";
	public static final String USER_PARAM = "root";
	public static final String PASSWORD_PARAM = "Javi.93";

	//@Resource(lookup = "java:app/jdbc/eprail")
	//private DataSource miDS;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		try {
			Class.forName("com.mysql.jdbc.Driver");

			//Connection conexion = miDS.getConnection();

			Connection conexion = DriverManager.getConnection(URL_PARAM,USER_PARAM,PASSWORD_PARAM);

			Statement myStatement = conexion.createStatement();

			/*myStatement
			.executeUpdate("INSERT INTO users (FirstName, FamilyName, email, password) values ("
					+ "'" + request.getParameter("nombre") + "'" + ",'" + request.getParameter("apellidos") + "'"+",'"+request.getParameter("email")+
					"'"+",'"+cryptMD5(request.getParameter("pass"))+"')");*/

			myStatement.close();
			conexion.close();

			//mandamos el email de activacion
			sendEmail(request, response);

			//redirigimos
			request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripci�n: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripci�n: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void sendEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		/*
		 * HACER URL DE ACTIVACION!! USAR QUERYSTRING!!
		 */

/*
		// Recipient's email ID needs to be mentioned.
		String to = request.getParameter("email");

		// Sender's email ID needs to be mentioned
		String from = "web@gmail.com";

		// Assuming you are sending email from localhost
		String host = "localhost:8080";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);

		try{
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		}catch (MessagingException mex) {
			mex.printStackTrace();
		}*/
		
		Session session = null;
		try {
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		session = (Session) envCtx.lookup("mail/NomDeLaRessource");
		Message message = new MimeMessage(session);

		message.setFrom(new InternetAddress("em@h.es"));
		InternetAddress to[] = new InternetAddress[1];
		to[0] = new InternetAddress(request.getParameter("email"));
		message.setRecipients(Message.RecipientType.TO, to);
		message.setSubject("HOLA");
		message.setContent("heoo", "text/html;charset=UTF-8");
		Transport.send(message);
		} catch (AddressException ex) {
		System.out.println( ex.getMessage());
		} catch (MessagingException ex) {
		System.out.println( ex.getMessage());

		} catch (Exception ex) {
		System.out.println(" lookup error ");
		System.out.println( ex.getMessage());
		}
		/*try{
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			Session session = (Session) envCtx.lookup("mail/Session");

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("noreply@prueba.com"));
			InternetAddress to[] = new InternetAddress[1];
			to[0] = new InternetAddress(request.getParameter("email"));
			message.setRecipients(Message.RecipientType.TO, to);
			message.setSubject("Prueba");
			message.setContent("texto", "text/plain");
			Transport.send(message);
		}catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

	/** 
	 * Encripta un String con el algoritmo MD5. 
	 * @return String - cadena a encriptar
	 * @throws Exception 
	 */ 
	protected String cryptMD5(String textoPlano)
	{
		try
		{
			final char[] HEXADECIMALES = { '0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f' };

			textoPlano = "0208"+textoPlano;//metemos unos nuemros antes de la contrase�a en claro

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

}
