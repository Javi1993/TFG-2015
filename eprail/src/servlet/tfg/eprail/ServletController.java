package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import javabeans.tfg.eprail.User;
import javax.annotation.Resource;
import javax.annotation.sql.DataSourceDefinition;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
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

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

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

		try {
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
					session.setAttribute("userBean", userBean);
				}

				//le pasamos los parametros de la request
				userBean.setEmail(request.getParameter("email"));
				userBean.setPassword(request.getParameter("pass"));

				Connection conexion = myDS.getConnection();

				Statement st = conexion.createStatement();

				ResultSet rs = st.executeQuery("SELECT * FROM users WHERE email = '"+userBean.getEmail()+
						"' AND password ='"+Funciones.cryptMD5("0351"+userBean.getPassword())+"' AND IsValidate = 1");
				rs.last();

				if(rs.getRow()==0 || !userBean.doLogin(rs))//hacemos el login (ver en esta funcion si esta activa la cuenta)
				{//No hay userBean en session o los datos son incorrectos, redirigimos a inicio
					nextPage = "/index.html";
				}

				rs.close();
				st.close();
				conexion.close();
			}

			//Redirigimos a la pagina que va a tramitar su peticion
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
