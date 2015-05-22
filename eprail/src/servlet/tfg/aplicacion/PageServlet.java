package servlet.tfg.aplicacion;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.tfg.aplicacion.ManagementProject;
import modeldata.tfg.aplicacionJPA.Project;
import modeldata.tfg.aplicacionJPA.Sharing;
import modeldata.tfg.aplicacionJPA.User;

/**
 * Servlet implementation class PageServlet
 */
@WebServlet("/page")
public class PageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PageServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		ManagementProject mp = new ManagementProject();


		HttpSession session = request.getSession(true);//cogemos los datos del usuario
		User userBean = (User) session.getAttribute("userBean");

		int offset = 0;
		if(request.getParameter("op").equals("0")){//pagina anterior
			if(request.getSession().getAttribute("offsetBack")!=null){
				offset = (int) request.getSession().getAttribute("offsetBack");
			}	
		}else{//pagina siguiente
			if(request.getSession().getAttribute("offsetNext")!=null){
				offset = (int) request.getSession().getAttribute("offsetNext");
			}
		}

		List<Project> projects = mp.buscarAllProjectsIterableJPA(offset, 4, userBean);//iteramos cogiendo los siguientes 4 proyectos
		List<Sharing> projectsSh = mp.buscarAllProjectsShIterableJPA(offset, 4, userBean);
		if(projects.size()>=projectsSh.size())
		{
			request.getSession().setAttribute("offsetBack", offset-4);//guardamos la posicion del primero
			offset += projects.size();//actualizamos
		}else{
			request.getSession().setAttribute("offsetBack", offset-4);//guardamos la posicion del primero
			offset += projectsSh.size();//actualizamos
		}

		request.getSession().setAttribute("offsetNext", offset);//guardamos la posicion del proximo primero
		if(projects.size()>0){//vemos si hay contenido
			request.getSession().setAttribute("projectList", projects);//guardamos la lista
		}
		if(projectsSh.size()>0){
			request.getSession().setAttribute("projectListShared", projectsSh);//guardamos la lista
		}
		request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);//redirigimos
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
