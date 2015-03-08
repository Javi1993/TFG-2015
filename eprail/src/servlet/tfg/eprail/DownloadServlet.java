package servlet.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import modeldata.tfg.eprail.User;
import modeldata.tfg.eprail.Project;
import controller.tfg.eprail.ManagementProject;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
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

		//buscamos los metadatos del archivo en la bbdd
		Project project = ManagementProject.buscarJPAProyectoIdUID(userBean, Long.parseLong(request.getParameter("id")));
		if(project!=null)
		{
			response.setContentType("application/ongf");//indicamos el tipo de archivo
			response.setHeader("Content-disposition","attachment; filename="+project.getProjectName());//dialogo de descarga
			String path = new String(project.getONGFile());
			File my_file = new File(path+File.separator+project.getIdProject());

			//Realizamos la descarga
			OutputStream out = response.getOutputStream();
			FileInputStream in = new FileInputStream(my_file);
			byte[] buffer = new byte[4096];
			int length;
			while ((length = in.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}
			in.close();
			out.flush();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
