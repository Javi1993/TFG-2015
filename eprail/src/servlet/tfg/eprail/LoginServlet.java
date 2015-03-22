package servlet.tfg.eprail;

import java.io.IOException;
import java.util.List;
import modeldata.tfg.eprailJPA.Project;
import modeldata.tfg.eprailJPA.Sharing;
import modeldata.tfg.eprailJPA.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import controller.tfg.eprail.ManagementProject;
import funciones.tfg.eprail.Comunicacion;
import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);//cogemos los datos del usuario
		User userBean = (User) session.getAttribute("userBean");

		ManagementProject mp = new ManagementProject();
		List<Project> list = mp.buscarJPAProyectosPropios(userBean);
		request.getSession().setAttribute("projectList", list);//buscamos en la BBDD los archivos propios
		List<Sharing> listSh = mp.buscarJPAProyectosCompartidos(userBean);
		request.getSession().setAttribute("projectListShared", listSh);//buscamos en la BBDD los archivos compartidos

		request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);//cogemos los datos del usuario
		User userBean = (User) session.getAttribute("userBean");

		ManagementProject mp = new ManagementProject();
		List<Project> list = mp.buscarJPAProyectosPropios(userBean);
		request.getSession().setAttribute("projectList", list);//buscamos en la BBDD los archivos propios
		List<Sharing> listSh = mp.buscarJPAProyectosCompartidos(userBean);
		request.getSession().setAttribute("projectListShared", listSh);//buscamos en la BBDD los archivos compartidos

		//Extraemos los archivos html [FALTA EXTRAER LOS ARCHIVOS COMPARTIDOS
		if(list!=null)
		{
			String applicationPath = request.getServletContext().getRealPath("");
			Funciones.extraerHTML(applicationPath, userBean.getUid());
		}

		System.out.println("TEST WEB-SERVICES "+Comunicacion.testRest());
		request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);
	}
}
