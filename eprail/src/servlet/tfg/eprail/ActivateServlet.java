package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import controller.tfg.eprail.ManagementUser;
import modeldata.tfg.eprail.User;

/**
 * Servlet implementation class ActivateServlet
 */
@WebServlet("/activate")
public class ActivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActivateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nextPage = "/jsp/activate.jsp";

		try {
			User userAux = ManagementUser.buscarJPAUser(Long.parseLong(request.getParameter("op")));

			Connection conexion = myDS.getConnection();
			Statement mySt = conexion.createStatement();

			if(userAux!=null)
			{
				if(userAux.getIsValidate()!=1)
				{//la cuenta no está activa
					Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
					mySt.executeUpdate("UPDATE users SET IsValidate = 1, DateRegistration = '"+currentTimestamp+"' WHERE UID = "+userAux.getUid());
					request.setAttribute("active", true);
				}
				else{//la cuenta ya está activada
					request.setAttribute("fecha", userAux.getDateRegistration());
					request.setAttribute("active", false);
				}
			}else
			{//no se encuentra ningun usuario con esa UID
				nextPage = "/errors/404.html";
			}

			mySt.close();
			conexion.close();

			//redirigimos
			request.getRequestDispatcher(nextPage).forward(request, response);

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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
