package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javabeans.tfg.eprail.*;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		load(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		load(request, response);
	}

	protected void load (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Buscamos el userBean en la session
			HttpSession session = request.getSession(true);
			User userBean = (User) session.getAttribute("userBean");

			Connection conexion = myDS.getConnection();

			Statement myST = conexion.createStatement();

			ResultSet rs = myST.executeQuery("SELECT * FROM projects p, statuscategories s WHERE p.IdProjectStatus = s.IdProjectStatus AND p.UID = "+userBean.getUid());

			List<Project> list = null;
			rs.last();
			if(rs.getRow()!=0){
				list = new ArrayList<Project>();
				rs.beforeFirst();
				Project p;
				Status s;
				//Blob blob;
				//String path;
				while(rs.next())
				{
					//blob = rs.getBlob("ONGFile");
					//path = new String(blob.getBytes(1l, (int) blob.length()));
					s = new Status(rs.getByte("IdProjectStatus"), rs.getString("StatusName"), rs.getString("StatusDescription"));
					p = new Project(rs.getInt("IdProject"), rs.getString("ProjectName"), rs.getString("ProjectDescription"), rs.getBlob("ONGFile"), userBean, s, rs.getTimestamp("DateCreation"), rs.getTimestamp("DateModified"));
					list.add(p);
				}
			}

			rs.close();
			myST.close();
			conexion.close();

			request.getSession().setAttribute("projectList", list);
			request.getRequestDispatcher("/jsp/inicio.jsp").forward(request, response);

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
}
