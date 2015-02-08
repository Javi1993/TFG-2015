package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class RecoverServlet
 */
@WebServlet("/recover")
public class RecoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecoverServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nextPage = "/errors/404.html";
		
		//nos protegemos ante caracteres especiales 
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		try {
			Connection conexion = myDS.getConnection();

			Statement myST = conexion.createStatement();

			ResultSet rs = myST.executeQuery("SELECT * FROM users WHERE UID = "+request.getParameter("op"));

			rs.last();
			if(rs.getRow()!=0)
			{
				rs.beforeFirst();
				while(rs.next())
				{
					String email = Funciones.cryptMD5("0021"+rs.getString("email"));
					if(email.equals(request.getParameter("em")))
					{
						request.setAttribute("valido",true);
						nextPage = "/jsp/recover.jsp";
					}
				}
			}
			rs.close();
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

		String nextPage = "/errors/404.html";
		int modo = Integer.parseInt(request.getParameter("cd"));

		try {
			Connection conexion = myDS.getConnection();

			Statement myST = conexion.createStatement();

			if(modo==0)
			{
				nextPage = "/jsp/recuperar.jsp";
				ResultSet rs = myST.executeQuery("SELECT * FROM users WHERE email = '"+request.getParameter("email")+"'");

				rs.last();
				if(rs.getRow()!=0)
				{
					rs.beforeFirst();
					while(rs.next())
					{
						Funciones.sendEmail("Eprail: Restablecer contraseña","<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
								+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Hola "
								+rs.getString("firstName")+"</h2><br>"
								+ "<p>Hemos recibido una solicitud para restablecer tu contrase&ntilde;a. Si no has solicitado esta servicio ignora el mensaje por favor.<br>"
								+ "Para restablecer tu contrase&ntilde;a visita el siguiente link: </p><br>"
								+ "<a href='http://localhost:8080/eprail/recover?op="+rs.getInt("UID")+"&em="+Funciones.cryptMD5("0021"+rs.getString("email"))+"' target='_blank'>Restablecer contrase&ntilde;a</a> MANDAR A FORMULARIO DONDE PiDA LA NEUVA PASS"+
								"<br><br><p>Un saludo</p></div></body></html>",request.getParameter("email"));
					}
					request.setAttribute("user", true);	
				}else
				{//no se encuentra ningun usuario con esa UID
					request.setAttribute("user", false);
				}
				rs.close();

			}else if(modo==1){
				myST.executeUpdate("UPDATE users SET password = '"+Funciones.cryptMD5("0351"+request.getParameter("pass"))+"' WHERE UID = "+request.getParameter("uid"));
				nextPage = "/index.html";
			}

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

}
