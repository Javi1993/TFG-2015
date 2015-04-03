package servlet.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import modeldata.tfg.eprailJPA.Deletedproject;
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
			delete(request, response, project, userBean);
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

	protected void delete(HttpServletRequest request, HttpServletResponse response, Project project, User userBean) throws ServletException, IOException {
		//borramos de la carpeta del usuario el fichero y lo movemos a la carpeta de borrados
		String path = new String(project.getONGFile());
		File file = new File(path+File.separator+project.getIdProject());
		// creates the save directory if it does not exists
		File fileSaveDir = new File(path.replaceAll(String.valueOf(userBean.getUid()), File.separator+"0"));
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		//archivo que almaecenara el contenido
		File fileD = new File(fileSaveDir.getPath()+File.separator+project.getIdProject());
		//copiamos el contenido
		FileInputStream in = new FileInputStream(file);
		FileOutputStream out = new FileOutputStream(fileD);
		int c;
		while( (c = in.read() ) != -1)
			out.write(c);
		in.close();
		out.close();
		file.delete();
		//insertamos en la tabla de borrados y borramos de la tabla del usuario ese proyecto
		ManagementProject mp = new ManagementProject();
		Deletedproject delproj = new Deletedproject();
		delproj.setIdProject(project.getIdProject());
		delproj.setProjectName(project.getProjectName());
		delproj.setProjectDescription(project.getProjectDescription());
		delproj.setONGFile(project.getONGFile());
		delproj.setUser(project.getUser());
		delproj.setStatuscategory(project.getStatuscategory());
		delproj.setDateCreation(project.getDateCreation());
		Date date= new Date();
		Timestamp ts = new Timestamp(date.getTime());
		delproj.setDateDeleted(ts);
		mp.moverJPAProyecto(delproj);
		mp.borrarJPAObject(project);//borramos el archivo de la tabla de proyectos del usuario
	}
}
