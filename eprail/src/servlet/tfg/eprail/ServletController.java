package servlet.tfg.eprail;

import java.io.IOException;
import modeldata.tfg.eprail.User;
import javax.annotation.sql.DataSourceDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import controller.tfg.eprail.ManagementUser;
import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class ServletController
 */
@DataSourceDefinition(
		name = "java:app/jdbc/eprail",
		className = "com.mysql.jdbc.jdbc2.optional.MysqlDataSource",
		url = "jdbc:mysql://localhost:3306/eprail",
		portNumber = 3306,
		serverName = "localhost",
		databaseName = "eprail",
		user = "root",
		password = "Javi.93",
		properties = {"pedantic=true"}
		)
@WebServlet(name="ServletController", urlPatterns={"/controller/*"})
public class ServletController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
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
			System.out.println(userBean.getPassword());
			userBean = ManagementUser.realizarJPALogin(userBean);
			
			if(userBean.getLoggedIn() == false || userBean == null)
			{//No hay userBean en session o los datos son incorrectos, redirigimos a inicio
				nextPage = "/index.html";
			}else{
				session.setAttribute("userBean", userBean);
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
