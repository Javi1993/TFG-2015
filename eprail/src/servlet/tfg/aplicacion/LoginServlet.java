package servlet.tfg.aplicacion;

import java.io.IOException;
import java.util.List;

import modeldata.tfg.aplicacionJPA.Project;
import modeldata.tfg.aplicacionJPA.Sharing;
import modeldata.tfg.aplicacionJPA.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.tfg.aplicacion.ManagementProject;
import funciones.tfg.aplicacion.Funciones;

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
		List<Sharing> listSh = mp.buscarJPAProyectosCompartidos(userBean);

		if(list!=null&&listSh!=null)
		{			
			//iteramos
			int offset = 0;
			List<Project> projects = mp.buscarAllProjectsIterableJPA(offset, 4, userBean);
			List<Sharing> projectsSh = mp.buscarAllProjectsShIterableJPA(offset, 4, userBean);
			if(projects.size()>=projectsSh.size())
			{
				request.getSession().setAttribute("max", list.size());
				request.getSession().setAttribute("offsetBack", offset);
				offset += projects.size();
			}else{
				request.getSession().setAttribute("max", listSh.size());
				request.getSession().setAttribute("offsetBack", offset);
				offset += projectsSh.size();
			}

			request.getSession().setAttribute("offsetNext", offset);
			request.getSession().setAttribute("projectList", projects);
			request.getSession().setAttribute("projectListShared", projectsSh);
		}
		request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(true);//cogemos los datos del usuario
		User userBean = (User) session.getAttribute("userBean");

		if(userBean.getProjects()!=null && userBean.getSharings1()!=null)
		{
			//Extraemos el html
			String applicationPath = request.getServletContext().getRealPath("");
			if(!userBean.getProjects().isEmpty()){//proyectos propios
				Funciones.extraerHTML(applicationPath, userBean.getUid());
			}

			if(!userBean.getSharings1().isEmpty()){//proyectos compartidos
				for(Sharing proyecto : userBean.getSharings1()){
					Funciones.extraerHTMLunico(applicationPath, userBean.getUid(), proyecto.getProject());
				}	
			}

			//iteramos
			int offset = 0;
			ManagementProject mp = new ManagementProject();
			List<Project> projects = mp.buscarAllProjectsIterableJPA(offset, 4, userBean);
			List<Sharing> projectsSh = mp.buscarAllProjectsShIterableJPA(offset, 4, userBean);
			if(projects.size()>=projectsSh.size())
			{
				request.getSession().setAttribute("max", userBean.getProjects().size());
				request.getSession().setAttribute("offsetBack", offset);
				offset += projects.size();
			}else{
				request.getSession().setAttribute("max", userBean.getSharings1().size());
				request.getSession().setAttribute("offsetBack", offset);
				offset += projectsSh.size();
			}

			request.getSession().setAttribute("offsetNext", offset);
			request.getSession().setAttribute("projectList", projects);
			request.getSession().setAttribute("projectListShared", projectsSh);
		}
		request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);
	}
}
