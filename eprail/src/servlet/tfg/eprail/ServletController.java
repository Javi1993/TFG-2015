package servlet.tfg.eprail;

import java.io.IOException;

import javabeans.tfg.eprail.User;

import javax.annotation.sql.DataSourceDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ServletController
 */
@DataSourceDefinition(
		name = "java:app/jdbc/Eprail",
		minPoolSize = 0,
		initialPoolSize = 0,
		className = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
		user = "root",
		password = "Javi.93",
		databaseName = "eprail"
		)
@WebServlet(name="ServletController", urlPatterns={"/controller/*"})
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	//CAMBIAR
	public static final String EMAIL_PARAM = "email";
	public static final String PASSWORD_PARAM = "pass";
	public static final String USERBEAN_ATTR = "userbean";
	// public static final String CONTROLLER_PREFIX = "/controller";

	/**
	 * Default constructor. 
	 */
	public ServletController() {
		// TODO Auto-generated constructor stub
	}

	/** Processes requests for both HTTP  
	 * <code>GET</code> and <code>POST</code> methods.
	 * @param request servlet request
	 * @param response servlet response
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// String que contiene la ruta de la pagina solicitada
		String nextPage = request.getPathInfo();

		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute(USERBEAN_ATTR);
		System.out.println("--------------------- "+nextPage);
		//Comprobamos si estaba en la session o la accion es registrar un nuevo usuario
		if ((userBean == null || !userBean.getLoggedIn())) 
		{//comprobamos si quiere loguearse, recuperar contraseña o registrarse
			if(!nextPage.equals("/register")&&!nextPage.equals("/recover"))
			{//login
				if (userBean == null) 
				{//creamos el bean
					userBean = new User();
					session.setAttribute(USERBEAN_ATTR, userBean);
				}

				/*
				 * EN EL DOLOGIN() SE HACE TODO!!
				 */

				//le pasamos los parametros de la request
				userBean.setEmail(request.getParameter(EMAIL_PARAM));
				userBean.setPassword(request.getParameter(PASSWORD_PARAM));

				if(!userBean.doLogin())//hacemos el login
				{//No hay userBean en session o los datos son incorrectos, redirigimos a inicio
					session.invalidate();
					nextPage = "/index.html";
				}
			}
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
