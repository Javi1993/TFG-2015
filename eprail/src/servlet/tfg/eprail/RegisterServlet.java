package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;

import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

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
			//nos protegemos ante caracteres especiales 
			response.setContentType("text/html;charset=UTF-8");
			request.setCharacterEncoding("UTF-8");

			Connection conexion = myDS.getConnection();

			PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("INSERT INTO users (FirstName, FamilyName, email, password) values (?,?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			myPS.setString(1, request.getParameter("nombre"));
			myPS.setString(2, request.getParameter("apellidos"));
			myPS.setString(3, request.getParameter("email"));
			myPS.setString(4, Funciones.cryptMD5("0351"+request.getParameter("pass")));
			myPS.executeUpdate();

			ResultSet rs = myPS.getGeneratedKeys();

			if (rs != null && rs.next()) {
				request.setAttribute("uid", rs.getLong(1));
			}

			myPS.close();

			conexion.close();

			//mandamos el email de activacion
			Funciones.sendEmail("Eprail: Confirmación de registro","<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
					+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Hola "
					+request.getParameter("nombre")+"</h2><br>"
					+ "<p>Gracias por registrarte en nuestra aplicación de simulaciones. Para activar tú cuenta visita el siguiente enlace: </p><br>"
					+ "<a href='http://localhost:8080/eprail/activate?op="+request.getAttribute("uid")+"' target='_blank'>Activar mi cuenta</a>"+
					"<br><br><p>Un saludo</p></div></body></html>",request.getParameter("email"));

			//redirigimos
			request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);

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
