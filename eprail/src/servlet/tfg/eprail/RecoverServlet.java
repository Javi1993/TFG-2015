package servlet.tfg.eprail;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.tfg.eprail.ManagementUser;
import modeldata.tfg.eprailJPA.User;
import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class RecoverServlet
 */
@WebServlet("/recover")
public class RecoverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		ManagementUser mu = new ManagementUser();
		User user = mu.buscarJPAUser(Long.parseLong(request.getParameter("op")));

		if(user!=null)
		{
			if(Funciones.cryptMD5("0021"+user.getEmail()).equals(request.getParameter("em")))
			{//comprobamos que el eamil del link es el del usuario almacenado
				request.setAttribute("valido",true);
				nextPage = "/jsp/recover.jsp";
			}
		}

		//redirigimos
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String nextPage = "/errors/404.html";
		int modo = Integer.parseInt(request.getParameter("cd"));
		ManagementUser mu = new ManagementUser();

		if(modo==0)
		{//mandamos email para recuperar cuenta
			nextPage = "/jsp/recuperar.jsp";

			User user = mu.buscarJPAUserEmail(request.getParameter("email"));
			if(user!=null)
			{//existe el usuario
				Funciones.sendEmail("Eprail: Restablecer contraseña","<!DOCTYPE html><html><head><meta charset='utf-8'></head><body>"
						+"<div style='border: 2px solid #800000; border-radius: 20px; box-shadow: 2px 2px 2px #888888; padding:20px;'><h2>Hola "
						+user.getFirstName()+"</h2><br>"
						+ "<p>Hemos recibido una solicitud para restablecer tu contrase&ntilde;a. Si no has solicitado esta servicio ignora el mensaje por favor.<br>"
						+ "Para restablecer tu contrase&ntilde;a visita el siguiente link: </p><br>"
						+ "http://localhost:8080/eprail/recover?op="+user.getUid()+"&em="+Funciones.cryptMD5("0021"+user.getEmail())+
						"<br><br><p>Un saludo</p></div></body></html>",user.getEmail());
				request.setAttribute("user", true);	
			}else
			{//no se encuentra ningun usuario con esa UID
				request.setAttribute("user", false);
			}
		}else if(modo==1)
		{//reseteo de contraseña
			User user = mu.buscarJPAUser(Long.parseLong(request.getParameter("uid")));
			user.setPassword(Funciones.cryptMD5("0351"+request.getParameter("pass")));
			mu.updateJPAUser(user);
			nextPage = "/index.html";
		}

		//redirigimos
		request.getRequestDispatcher(nextPage).forward(request, response);
	}
}
