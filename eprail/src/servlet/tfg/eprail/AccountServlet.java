package servlet.tfg.eprail;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import javabeans.tfg.eprail.User;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountServlet() {
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

			//Rellenamos el bean con los nuevos atributos
			User userBean = (User)request.getSession(true).getAttribute("userBean");
			userBean.setFirstName(request.getParameter("name"));
			userBean.setFamilyName(request.getParameter("apellidos"));
			if(!request.getParameter("pass").equals(""))
			{
				userBean.setPassword(request.getParameter("pass"));
			}

			Connection conexion = myDS.getConnection();
			
			PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("UPDATE users SET FirstName = ?, FamilyName = ?, password = ? WHERE email = ?");
			myPS.setString(1, userBean.getFirstName());
			myPS.setString(2, userBean.getFamilyName());
			myPS.setString(3, cryptMD5(userBean.getPassword()));
			myPS.setString(4, userBean.getEmail());
			myPS.executeUpdate();

			myPS.close();
			conexion.close();

			//redirigimos
			request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);

		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripción: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripción: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
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
