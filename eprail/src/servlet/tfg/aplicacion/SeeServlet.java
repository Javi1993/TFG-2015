package servlet.tfg.aplicacion;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeldata.tfg.aplicacionJPA.Project;
import modeldata.tfg.aplicacionJPA.User;
import controller.tfg.aplicacion.ManagementProject;
import funciones.tfg.aplicacion.Funciones;

/**
 * Servlet implementation class SeeServlet
 */
@WebServlet("/see")
public class SeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String TEMP_DIR = "temp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SeeServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");

		ManagementProject mp = new ManagementProject();
		Project project = mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id")));

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		String projectPath = TEMP_DIR + File.separator + userBean.getUid()
				+File.separator+project.getIdProject()+File.separator+"html";
		File f = new File(applicationPath + File.separator + projectPath);

		String content="";
		for(String j : f.list())
		{
			if(Funciones.validarResultadoHtml(j))
			{
				content = j;
				break;
			}
		}

		String path = "";
		if(content!="")
		{//modificamos las rutas relativas para que se muestren bien los enlaces e hipervinculos del html
			path = ".." + File.separator + projectPath + File.separator + content;//path que tendra el icono ver
		}else{
			//poner path que lleve a error
		}
		
		request.setAttribute("path", path);
		request.setAttribute("project", project);
		request.getRequestDispatcher("/jsp/see.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
