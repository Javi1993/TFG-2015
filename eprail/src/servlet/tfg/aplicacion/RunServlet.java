package servlet.tfg.aplicacion;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modeldata.tfg.aplicacionJPA.Project;
import controller.tfg.aplicacion.ManagementProject;
import webservices.tfg.aplicacion.Simulate;

/**
 * Servlet implementation class RunServlet
 */
@WebServlet("/run")
@SuppressWarnings("unused")
public class RunServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RunServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//actualizamos el estado del proyecto
		ManagementProject mp = new ManagementProject();
		Project project = mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id")));
		project.setStatuscategory(mp.buscarJPAStatus((byte)0));//actualizamos estado
		Date date= new Date();
		Timestamp ts = new Timestamp(date.getTime());
		project.setDateModified(ts);//actualizamos fecha de modificacion
		mp.updateJPAProject(project);
		Simulate sm = new Simulate(request.getParameter("id"), request.getServletContext().getRealPath(""));//lanzamos simulacion
		request.getRequestDispatcher("/controller/login").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}
