package servlet.tfg.aplicacion;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import webservices.tfg.aplicacion.Simulate;
import modeldata.tfg.aplicacionJPA.Project;
import modeldata.tfg.aplicacionJPA.User;
import controller.tfg.aplicacion.ManagementProject;
import funciones.tfg.aplicacion.Funciones;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
@MultipartConfig(
		fileSizeThreshold=1024*1024*2, // 2MB
		maxFileSize=1024*1024*10,      // 10MB
		maxRequestSize=1024*1024*100)   // 100MB
@SuppressWarnings("unused")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the directory where uploaded files will be saved,
	 */
	private static final String SAVE_DIR = "ONGFiles";
	private static final String TEMP_DIR = "temp";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @throws IOException 
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");
		ManagementProject mp = new ManagementProject();
		try {
			PrintWriter out = response.getWriter();
			if(request.getParameter("z").equals("0"))
			{
				List<Project> listRepetidos = mp.buscarJPAProyectosRepetidos(userBean);
				if(!listRepetidos.isEmpty()){
					for(Project project : listRepetidos)
					{
						out.println("<script type=\"text/javascript\">");
						out.println("if(confirm('Ya existe una copia del proyecto "+project.getProjectName()+ " en el servidor �Desea remplazarla? Si no acepta se mantendr\u00e1n las dos copias del proyecto en el servidor.')){");
						out.println("window.location.assign('/aplicacion/controller/upload?z=1&id="+project.getIdProject()+"');}else{");
						out.println("window.location.assign('/aplicacion/controller/upload?z=2&id="+project.getIdProject()+"');}");
						out.println("</script>");
					}
				}else{
					out.println("<script type=\"text/javascript\">");
					out.println("document.location.href='/aplicacion/controller/login';");
					out.println("</script>");
				}
			}else if(request.getParameter("z").equals("1")){//borramos el proyecto si el usuario desea sobreescribirlo
				Funciones.borrarProject(mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))), userBean);
				out.println("<script type=\"text/javascript\">");
				out.println("document.location.href='/aplicacion/controller/login';");
				out.println("</script>");
			}else if(request.getParameter("z").equals("2")){
				List<Project> project = mp.buscarJPARepetido(mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))), userBean);
				if(project.size()==1){
					project.get(0).setProjectName(project.get(0).getProjectName()+" (1)");
				}else{
					String num = ((project.get(1).getProjectName().split(Pattern.quote("(")))[1].split(Pattern.quote(")")))[0];
					int numx = Integer.parseInt(num)+1;
					project.get(0).setProjectName(project.get(0).getProjectName()+" ("+String.valueOf(numx)+")");
				}
				mp.updateJPAProject(project.get(0));//actualizamos
				out.println("<script type=\"text/javascript\">");
				out.println("document.location.href='/aplicacion/controller/login';");
				out.println("</script>");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");

		// gets absolute path of the web application
		String applicationPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		String uploadFilePath = applicationPath + File.separator + SAVE_DIR + File.separator + userBean.getUid();

		// creates the save directory if it does not exists
		File fileSaveDir = new File(uploadFilePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdirs();
		}
		System.out.println("Upload File Directory="+fileSaveDir.getAbsolutePath());

		//Get all the parts from request and write it to the file on server
		for (Part part : request.getParts()) {
			//validamos el Manifest.xml
			if(Funciones.getManifest(part, userBean.getUid(), applicationPath))
			{
				String urlManifest = applicationPath + File.separator + TEMP_DIR + File.separator + userBean.getUid();
				Project p = new Project();
				Funciones.getFileName(urlManifest, p);
				if(p.getProjectName()!=null&&p.getProjectDescription()!=null)
				{//subimos el archivo al servidor y registramos sus metadatos en la BBDD
					long id = insertFile(p, userBean, uploadFilePath);
					part.write(uploadFilePath + File.separator + id);//guardamos en el servidor el archivo
					Simulate sm = new Simulate(String.valueOf(id),applicationPath);//lanzamos la simulacion
					ManagementProject mp = new ManagementProject();
					Funciones.extraerHTMLunico(applicationPath, userBean.getUid(), mp.buscarJPAProyectoId(id));//extraemos el html del proyecto
				}else{
					response.sendError(HttpServletResponse.SC_BAD_REQUEST, "important_parameter needed");
				}
			}else{//no hay manifest o este es invalido
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "important_parameter needed");
			}
		}
	}

	/**
	 * Inserta los metadatos de un fichero subido en la BBDD
	 * @param project - proyecto
	 * @param user - UID del usuario que lo subio
	 * @param path - Localizaci�n
	 * @return - ID del proyecto
	 */
	protected long insertFile(Project project, User user, String path){
		ManagementProject mp = new ManagementProject();
		project.setONGFile(path.getBytes());
		project.setUser(user);
		project.setStatuscategory(mp.buscarJPAStatus((byte)0));
		mp.subirJPAProyecto(project);
		return project.getIdProject();
	}
}
