package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import modeldata.tfg.eprail.User;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
//import controller.tfg.eprail.ManagementUser;
import funciones.tfg.eprail.Funciones;

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
				userBean.setPassword(Funciones.cryptMD5("0351"+request.getParameter("pass")));
			}

			Connection conexion = myDS.getConnection();

			PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("UPDATE users SET FirstName = ?, FamilyName = ?, password = ? WHERE email = ?");
			myPS.setString(1, userBean.getFirstName());
			myPS.setString(2, userBean.getFamilyName());
			myPS.setString(3, userBean.getPassword());
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
		
		//VERSION CON JPA, no funciona, no permite EntityTransaction el TomEE

		//Rellenamos el bean con los nuevos atributos
		/*User userBean = (User)request.getSession(true).getAttribute("userBean");
		userBean.setFirstName(request.getParameter("name"));
		userBean.setFamilyName(request.getParameter("apellidos"));
		if(!request.getParameter("pass").equals(""))
		{
			userBean.setPassword(Funciones.cryptMD5("0351"+request.getParameter("pass")));
		}

		ManagementUser.updateJPAUser(userBean);

		//redirigimos
		request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);*/
	}
}
