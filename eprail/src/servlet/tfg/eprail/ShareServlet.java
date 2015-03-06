package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import com.mysql.jdbc.PreparedStatement;


/**
 * Servlet implementation class ShareServlet
 */
@WebServlet("/share")
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Resource(lookup="java:app/jdbc/eprail")
	private DataSource myDS;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShareServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		if(request.getParameter("op").equals("1"))
		{
			deleteUser(request, response);
		}
		else if(request.getParameter("op").equals("2"))
		{
			loadProjects(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Buscamos el userBean en la session
			HttpSession session = request.getSession(true);
			User userBean = (User) session.getAttribute("userBean");

			Connection conexion = myDS.getConnection();

			if(request.getParameter("op").equals("1"))
			{//insertamos el nuevo usuario con el que se va a compartir en la BBDD
				Statement mySt = conexion.createStatement();
				ResultSet rs = mySt.executeQuery("SELECT UID FROM users WHERE email = '"+request.getParameter("email")+"'");
				int uid=0;
				rs.last();
				if(rs.getRow()!=0)
				{
					rs.beforeFirst();
					while(rs.next())
					{
						uid = rs.getInt("UID");
					}
					rs.close();
					mySt.close();

					PreparedStatement myPS = (PreparedStatement) conexion.prepareStatement("INSERT INTO sharings (IdProject, UID, UIDsharer) values (?,?,?)");
					myPS.setInt(1, Integer.parseInt(request.getParameter("id")));
					myPS.setInt(2, uid);
					myPS.setInt(3, userBean.getUid());
					myPS.executeUpdate();
					myPS.close();

				}else{
					rs.close();
					mySt.close();
					conexion.close();
					System.out.println("Email no encontrado-redirigir a pagina error, usar pagina error defecto con cmabio mensaje");
					//REDIRIGIR A PAGINA Q DICE Q ESE EMAIL NO ESTA EN LA BBDD
				}
			}
			else if(request.getParameter("op").equals("2"))
			{
				//COMPROBAR NAMES QUE ACABAN EN 'C' y ver de QUE ID de usuarios son!!
			}
			conexion.close();
			loadProjects(request, response);
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void loadProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// Buscamos el userBean en la session
			HttpSession session = request.getSession(true);
			User userBean = (User) session.getAttribute("userBean");

			Connection conexion = myDS.getConnection();
			Statement st = conexion.createStatement();

			//Obtenemos la lista de usuarios ligados a ese proyecto
			ResultSet rs = st.executeQuery("SELECT * FROM sharings s, projects p, users u WHERE s.IdProject = '"+request.getParameter("id")+
					"' AND s.UID != "+userBean.getUid()+" AND s.IdProject = p.IdProject AND u.UID = s.UID");
			List<Sharings> list = null;
			rs.last();
			if(rs.getRow()!=0){
				list = new ArrayList<Sharings>();
				rs.beforeFirst();
				Sharings sh;
				Status s;
				Project p;
				User u;
				while(rs.next())
				{
					u = new User(rs.getInt("UID"), rs.getString("FirstName"), "", rs.getString("email"), (byte) rs.getByte("IsValidate"), rs.getTimestamp("DateRegistration"), "");
					s = new Status(rs.getByte("IdProjectStatus"), "", "");
					p = new Project(rs.getInt("IdProject"), rs.getString("ProjectName"), rs.getString("ProjectDescription"), rs.getBlob("ONGFile"), u, s, rs.getTimestamp("DateCreation"), rs.getTimestamp("DateModified"));

					sh = new Sharings(rs.getInt("IdSharing"), p, u, null, rs.getTimestamp("DateShared"), rs.getTimestamp("DateChanged"), rs.getByte("AllowRecalculate"), rs.getByte("AllowDelete"), rs.getByte("AllowDownload"), rs.getByte("AllowShare"));
					list.add(sh);
				}
			}
			rs.close();
			st.close();

			request.getSession().setAttribute("userSharedList", list);
			conexion.close();
			request.getRequestDispatcher("/jsp/shared.jsp").forward(request, response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	protected void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

	}
}
