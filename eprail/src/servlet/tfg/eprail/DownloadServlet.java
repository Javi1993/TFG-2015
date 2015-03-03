package servlet.tfg.eprail;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javabeans.tfg.eprail.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

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
		try {
			// Buscamos el userBean en la session
			HttpSession session = request.getSession(true);
			User userBean = (User) session.getAttribute("userBean");

			//buscamos los metadatos del archivo en la bbdd
			Connection conexion;
			conexion = myDS.getConnection();
			Statement mySt = conexion.createStatement();
			ResultSet rs = mySt.executeQuery("SELECT IdProject, ProjectName, ONGFile FROM projects WHERE IdProject = "+request.getParameter("id")+" AND UID = "+userBean.getUid()); 

			while(rs.next())
			{//descargamos el archivo
				response.setContentType("application/ongf");//indicamos el tipo de archivo
				response.setHeader("Content-disposition","attachment; filename="+rs.getString("ProjectName"));//dialogo de descarga

				//buscamos el archivo a descargar en el servidor
				Blob blob = rs.getBlob("ONGFile");
				String path = new String(blob.getBytes(1l, (int) blob.length()));
				File my_file = new File(path+File.separator+rs.getLong("IdProject"));

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
			rs.close();
			mySt.close();//cerramos la conexion
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
