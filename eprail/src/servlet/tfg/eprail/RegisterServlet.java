package servlet.tfg.eprail;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.Properties;
import javax.annotation.Resource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String URL_PARAM = "jdbc:mysql://localhost:3306/eprail";
	public static final String USER_PARAM = "root";
	public static final String PASSWORD_PARAM = "Javi.93";

	@Resource(lookup = "java:comp/jdbc/eprail")
	private DataSource miDS;
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

		
		/*
		 * PROBAR A USAR EJBS AHORA CON TOMEE
		 */
		
		
		try {
			Connection conexion = miDS.getConnection();

			Statement myStatement = conexion.createStatement();

			myStatement
			.executeUpdate("INSERT INTO users (FirstName, FamilyName, email, password) values ("
					+ "'" + request.getParameter("nombre") + "'" + ",'" + request.getParameter("apellidos") + "'"+",'"+request.getParameter("email")+
					"'"+",'"+cryptMD5(request.getParameter("pass"))+"')");

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
		} 
	}

	protected void sendEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
			message.setFrom(new InternetAddress("100290698@alumnos.uc3m.es"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(request.getParameter("email")));
			message.setSubject("Eprail: Confirmación de registro");
			message.setText("Hola "+request.getParameter("nombre")+","
					+ "\n\n Gracias por registrarte en nuestra aplicación de simulaciones. Para activar tú cuenta visita el siguiente enlace: "
					+ "\n\n http://localhost:8080/eprail/activate?user="+request.getParameter("email")+" [ver otra forma de acitvar]"+
					"\n\n Un saludo");



			/*
			 * HACER URL DE ACTIVACION!! USAR QUERYSTRING o ver otras formas!!! GENERAR NUMERO ALEATORIO Q ESTE RELACIONADO CON AL CUENTA (usar isvalidate)
			 */


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
