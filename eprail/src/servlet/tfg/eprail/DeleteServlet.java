package servlet.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import modeldata.tfg.eprail.User;
import modeldata.tfg.eprail.Project;
import modeldata.tfg.eprail.Deletedproject;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

import com.mysql.jdbc.PreparedStatement;

import controller.tfg.eprail.ManagementProject;


/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

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
		// TODO Auto-generated method stub


		//CONTROLAR PARA CUANDO SEA COMPARTIDO EL ARCHIVO!!!!

		try {
			// Buscamos el userBean en la session
			HttpSession session = request.getSession(true);
			User userBean = (User) session.getAttribute("userBean");
			//List<Project> list = (List<Project>) session.getAttribute("projectList");

			Project project = ManagementProject.buscarJPAProyectoIdUID(userBean, Long.parseLong(request.getParameter("id")));

			if(project!=null)
			{//borramos de la carpeta del usuario el fichero y lo movemos a la carpeta de borrados
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


				/*Deletedproject delproj = new Deletedproject();
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

				ManagementProject.moverJPAProyecto(delproj);*/

				//borrar esto cuando se arrelge el fallo del insert en JPA
				Connection conexion = myDS.getConnection();
				Statement mySt = conexion.createStatement();

				PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("INSERT INTO deletedprojects (IdProject, ProjectName, ProjectDescription, ONGFile, UID, IdProjectStatus, DateCreation, DateDeleted) values (?,?,?,?,?,?,?,?)");
				myPS.setLong(1, project.getIdProject());
				myPS.setString(2, project.getProjectName());
				myPS.setString(3, project.getProjectDescription());
				Blob blob = new SerialBlob(project.getONGFile());
				myPS.setBlob(4, blob);
				myPS.setLong(5, project.getUser().getUid());
				myPS.setByte(6, project.getStatuscategory().getIdProjectStatus());
				myPS.setTimestamp(7, project.getDateCreation());
				Date date= new Date();
				Timestamp ts = new Timestamp(date.getTime());
				myPS.setTimestamp(8, ts);
				myPS.executeUpdate();
				myPS.close();
				conexion.close();

				ManagementProject.borrarJPAObject(project);//borramos el archivo de la tabla de proyectos del usuario
			}
			request.getRequestDispatcher("/controller/login").forward(request, response);
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
