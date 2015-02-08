package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
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
			Connection conexion = myDS.getConnection();

			Statement myST = conexion.createStatement();
			Statement mySTaux = conexion.createStatement();

			ResultSet rs = myST.executeQuery("SELECT * FROM users WHERE UID = "+request.getParameter("op"));

			rs.last();
			if(rs.getRow()!=0)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					if(!rs.getBoolean("IsValidate"))
					{//la cuenta no está activa
						Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
						mySTaux.executeUpdate("UPDATE users SET IsValidate = 1, DateRegistration = '"+currentTimestamp+"' WHERE UID = "+request.getParameter("op"));
						request.setAttribute("active", true);
					}
					else{//la cuenta ya está activada
						request.setAttribute("fecha", rs.getTimestamp("DateRegistration"));
						request.setAttribute("active", false);
					}
				}
			}else
			{//no se encuentra ningun usuario con esa UID
				nextPage = "/errors/404.html";
			}

			rs.close();
			mySTaux.close();
			myST.close();
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
