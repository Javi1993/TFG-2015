package servlet.tfg.eprail;

import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import modeldata.tfg.eprail.User;
import modeldata.tfg.eprail.Project;

import com.mysql.jdbc.PreparedStatement;

import controller.tfg.eprail.ManagementProject;
import controller.tfg.eprail.ManagementUser;
import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/upload")
@MultipartConfig(
		fileSizeThreshold=1024*1024*2, // 2MB
		maxFileSize=1024*1024*10,      // 10MB
		maxRequestSize=1024*1024*50)   // 50MB
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * Name of the directory where uploaded files will be saved,
	 */
	private static final String SAVE_DIR = "ONGFiles";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		// TODO Auto-generated method stub
	}



	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//nos protegemos ante caracteres especiales 
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");

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

		String fileName = null;
		//Get all the parts from request and write it to the file on server
		for (Part part : request.getParts()) {
			fileName = Funciones.getFileName(part);
			part.write(uploadFilePath + File.separator + insertFile(fileName, userBean, uploadFilePath));	
		}
	}

	/**
	 * Inserta los metadatos de un fichero subido en la BBDD
	 * @param fileName - nombre del fichero
	 * @param user - UID del usuario que lo subio
	 * @param path - Localización
	 * @return - ID del proyecto
	 */
	protected long insertFile(String fileName, User user, String path){
		/*Project project  = new Project();
		project.setProjectName(fileName);
		project.setONGFile(path.getBytes());
		project.setUser(user);
		
		ManagementProject.subirJPAProyecto(project);
		System.out.println("----------a "+project.getIdProject());
		//terminarss
		return project.getIdProject();*/
		long id = 0;
		
		try {

			Connection conexion = myDS.getConnection();

			PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("INSERT INTO projects (ProjectName, ONGFile, UID) values (?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
			myPS.setString(1, fileName);
			Blob blob = conexion.createBlob();
			blob.setBytes(1, path.getBytes());
			myPS.setBlob(2, blob);
			myPS.setLong(3, user.getUid());
			myPS.executeUpdate();

			ResultSet rs = myPS.getGeneratedKeys();

			if (rs != null && rs.next()) {
				id = rs.getLong(1);
			}

			myPS.close();

			conexion.close();
		} catch (SQLWarning sqlWarning) {
			while (sqlWarning != null) {
				System.out.println("Error: " + sqlWarning.getErrorCode());
				System.out.println("Descripción: " + sqlWarning.getMessage());
				System.out.println("SQLstate: " + sqlWarning.getSQLState());
				sqlWarning = sqlWarning.getNextWarning();
			}
		} catch (SQLException sqlException) {
			while (sqlException != null) {
				System.out.println("Error: " + sqlException.getErrorCode());
				System.out.println("Descripción: " + sqlException.getMessage());
				System.out.println("SQLstate: " + sqlException.getSQLState());
				sqlException = sqlException.getNextException();
			}
		}
		return id;
	}
}
