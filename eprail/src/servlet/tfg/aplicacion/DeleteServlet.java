package servlet.tfg.aplicacion;

import java.io.IOException;

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
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");
		ManagementProject mp = new ManagementProject();
		Project project = null;
		if(request.getParameter("sh").equals("0"))
		{//el proyecto a borrar es propio
			//buscamos los metadatos del archivo en la bbdd
			project = mp.buscarJPAProyectoIdUID(userBean, Long.parseLong(request.getParameter("id")));
		}else if(request.getParameter("sh").equals("1"))
		{//el proyecto a borrar es compartido
			Sharing sharing = mp.buscarJPAPadre(userBean.getUid(), Long.parseLong(request.getParameter("id")));
			if(sharing.getAllowShare()!=0){//tiene permisos
				project = sharing.getProject();
			}
		}
		if(project!=null)
		{//todo correcto
			Funciones.borrarProject(project, userBean);
			request.getRequestDispatcher("/controller/login").forward(request, response);
		}else{//el usuario no puede realizar esta tarea
			request.getRequestDispatcher("/errors/error-allowed.jsp").forward(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
