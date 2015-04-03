package servlet.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import modeldata.tfg.eprailJPA.Project;
import modeldata.tfg.eprailJPA.Sharing;
import modeldata.tfg.eprailJPA.User;
import controller.tfg.eprail.ManagementProject;

/**
 * Servlet implementation class DownloadServlet
 */
@WebServlet("/download")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
		ManagementProject mp = new ManagementProject();
		Project project = null;
		if(request.getParameter("sh").equals("0"))
		{//el proyecto a descargar es propio
			//buscamos los metadatos del archivo en la bbdd
			project = mp.buscarJPAProyectoIdUID(userBean, Long.parseLong(request.getParameter("id")));
		}else if(request.getParameter("sh").equals("1"))
		{//el proyecto a descargar es compartido
			Sharing sharing = mp.buscarJPAPadre(userBean.getUid(), Long.parseLong(request.getParameter("id")));
			if(sharing != null && sharing.getAllowDownload()!=0){//tiene permisos
				project = sharing.getProject();
			}
		}
		if(project!=null)
		{//todo correcto
			descargar(request, response, project);
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

	protected void descargar(HttpServletRequest request, HttpServletResponse response, Project project) throws ServletException, IOException {
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
