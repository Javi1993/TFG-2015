package servlet.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import modeldata.tfg.eprail.User;
import modeldata.tfg.eprail.Project;

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
@SuppressWarnings("unchecked")
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
			
			Project project = ManagementProject.buscarProyectoIdUID(userBean, Long.parseLong(request.getParameter("id")));

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
				
				//MOVER A OTRA FUNCION AHORA
				
				Connection conexion = myDS.getConnection();
				Statement mySt = conexion.createStatement();
				
				Date date= new Date();
				Timestamp ts = new Timestamp(date.getTime());

				PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("INSERT INTO deletedprojects (IdProject, ProjectName, ProjectDescription, ONGFile, UID, IdProjectStatus, DateCreation, DateDeleted) values (?,?,?,?,?,?,?,?)");
				myPS.setLong(1, project.getIdProject());
				myPS.setString(2, project.getProjectName());
				myPS.setString(3, project.getProjectDescription());
				Blob blob = new SerialBlob(project.getONGFile());
				myPS.setBlob(4, blob);
				myPS.setLong(5, project.getUser().getUid());
				myPS.setByte(6, project.getStatuscategory().getIdProjectStatus());
				myPS.setTimestamp(7, project.getDateCreation());
				myPS.setTimestamp(8, ts);
				myPS.executeUpdate();
				myPS.close();
				
				mySt = conexion.createStatement();
				mySt.executeUpdate("DELETE FROM projects WHERE IdProject = "+project.getIdProject()+" AND UID = "+project.getUser().getUid());
				mySt.close();
				conexion.close();
			}
			
			
			/*Connection conexion = myDS.getConnection();

			Statement mySt = conexion.createStatement();
			ResultSet rs = mySt.executeQuery("SELECT ONGFile, IdProject FROM projects WHERE IdProject = "+request.getParameter("id")+" AND UID = "+userBean.getUid()); 

			while(rs.next())
			{//borramos del servidor el archivo
				Blob blob = rs.getBlob("ONGFile");
				String path = new String(blob.getBytes(1l, (int) blob.length()));

				File file = new File(path+File.separator+rs.getLong("IdProject"));

				// creates the save directory if it does not exists
				File fileSaveDir = new File(path.replaceAll(String.valueOf(userBean.getUid()), File.separator+"0"));
				if (!fileSaveDir.exists()) {
					fileSaveDir.mkdirs();
				}

				//archivo que almaecenara el contenido
				File fileD = new File(fileSaveDir.getPath()+File.separator+rs.getLong("IdProject"));

				//copiamos el contenido
				FileInputStream in = new FileInputStream(file);
				FileOutputStream out = new FileOutputStream(fileD);

				int c;
				while( (c = in.read() ) != -1)
					out.write(c);

				in.close();
				out.close();

				file.delete();
			}
			Project aux = null;
			for(Project p : list){
				if(p.getIdProject()==Integer.parseInt(request.getParameter("id"))){
					aux = p;
				}
			}

			rs.close();
			mySt.close();

			//borramos de la base de datos el archivo e insertamos sus datos en la tabla de borrados
			if(aux!=null){
				Date date= new Date();
				Timestamp ts = new Timestamp(date.getTime());

				PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("INSERT INTO deletedprojects (IdProject, ProjectName, ProjectDescription, ONGFile, UID, IdProjectStatus, DateCreation, DateDeleted) values (?,?,?,?,?,?,?,?)");
				myPS.setInt(1, aux.getIdProject());
				myPS.setString(2, aux.getProjectName());
				myPS.setString(3, aux.getProjectDescription());
				myPS.setBlob(4, aux.getONGFile());
				myPS.setInt(5, aux.getUid().getUid());
				myPS.setByte(6, aux.getIdProjectStatus().getIdProjectStatus());
				myPS.setTimestamp(7, aux.getDateCreation());
				myPS.setTimestamp(8, ts);
				myPS.executeUpdate();
				myPS.close();
			}

			mySt = conexion.createStatement();
			mySt.executeUpdate("DELETE FROM projects WHERE IdProject = "+request.getParameter("id")+" AND UID = "+userBean.getUid());
			mySt.close();
			conexion.close();*/

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
