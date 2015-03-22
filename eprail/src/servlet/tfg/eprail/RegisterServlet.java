package servlet.tfg.eprail;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modeldata.tfg.eprailJPA.User;
import controller.tfg.eprail.ManagementUser;
import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		//nos protegemos ante caracteres especiales 
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		User user = new User();
		user.setFirstName(request.getParameter("nombre"));
		user.setFamilyName(request.getParameter("apellidos"));
		user.setEmail(request.getParameter("email"));
		user.setPassword(Funciones.cryptMD5("0351"+request.getParameter("pass")));
		ManagementUser mu = new ManagementUser();
		mu.registrarJPAUser(user);

		Funciones.sendEmail("Eprail: Confirmación de registro","<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
				+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Hola "
				+user.getFirstName()+"</h2><br>"
				+ "<p>Gracias por registrarte en nuestra aplicaci&oacute;n de simulaciones. Para activar t&uacute; cuenta visita el siguiente enlace: </p><br>"
				+ "http://localhost:8080/eprail/activate?op="+user.getUid()+
				"<br><br><p>Un saludo</p></div></body></html>",user.getEmail());

		request.getRequestDispatcher("/jsp/registro.jsp").forward(request, response);
	}
}
