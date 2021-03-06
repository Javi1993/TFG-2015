package servlet.tfg.aplicacion;

import java.io.IOException;

import modeldata.tfg.aplicacionJPA.User;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import webservices.tfg.aplicacion.Comunicacion;
import controller.tfg.aplicacion.ManagementUser;
import funciones.tfg.aplicacion.Funciones;

/**
 * Servlet implementation class ServletController
 */
@WebServlet(name="ServletController", urlPatterns={"/controller/*"})
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private Comunicacion comunicacion;

	/**
	 * Default constructor. 
	 */
	public ServletController() {
		// TODO Auto-generated constructor stub
	}

	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		comunicacion = new Comunicacion();
	}

	/** Processes requests for both HTTP  
	 * <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//nos protegemos ante caracteres especiales 
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

		// String que contiene la ruta de la pagina solicitada
		String nextPage = request.getPathInfo();
		System.out.println("------------------- "+nextPage);//debug

		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");

		//Comprobamos si estaba en la session
		if ((userBean == null || !userBean.getLoggedIn())) 
		{//comprobamos si quiere loguearse, recuperar contrase�a o registrarse
			if (userBean == null) 
			{//creamos el bean
				userBean = new User();
			}

			//le pasamos los parametros de la request
			userBean.setEmail(request.getParameter("email"));
			userBean.setPassword(Funciones.cryptMD5("0351"+request.getParameter("pass")));
			ManagementUser mu = new ManagementUser();
			userBean = mu.realizarJPALogin(userBean);

			if(userBean.getLoggedIn() == false || userBean == null)
			{//No hay userBean en session o los datos son incorrectos, redirigimos a inicio
				nextPage = "/errors/error-login.jsp";
			}else{
				userBean.setLoggedIn(true);
				session.setAttribute("userBean", userBean);
			}
		}

		//comprobamos el estado del servidor de simulaciones
		if(comunicacion.getServer()){
			request.getSession().setAttribute("server", "on");
		}else{
			request.getSession().setAttribute("server", "off");
		}

		//Redirigimos a la pagina que va a tramitar su peticion
		request.getRequestDispatcher(nextPage).forward(request, response);
	}    


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}
}
