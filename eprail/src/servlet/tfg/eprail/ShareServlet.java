package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

import modeldata.tfg.eprail.Sharing;
import modeldata.tfg.eprail.User;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import com.mysql.jdbc.PreparedStatement;

import controller.tfg.eprail.ManagementProject;
import controller.tfg.eprail.ManagementUser;
import funciones.tfg.eprail.Funciones;


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
		{//borramos a un usuario de la comparticion
			ManagementProject.borrarJPAObject(ManagementProject.buscarJPACompartidoId(Long.parseLong(request.getParameter("i"))));
			loadProjects(request, response);
		}
		else if(request.getParameter("op").equals("2"))
		{//mostramos la lista de usuarios con acceso a ese documento
			loadProjects(request, response);
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");
		try {
			if(request.getParameter("op").equals("1"))
			{//insertamos el nuevo usuario con el que se va a compartir en la BBDD
				User userAdd = ManagementUser.buscarJPAUserEmail(request.getParameter("email"));
				if(userAdd!=null)
				{
					//Sharing sh = new Sharing();
					//sh.setProject(ManagementProject.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))));
					//sh.setUser1(userAdd);
					//sh.setUser2(userBean);
					//ManagementProject.addJPACompartido(sh);
					Connection conexion = myDS.getConnection();
					PreparedStatement myPs = (PreparedStatement) conexion.prepareStatement("INSERT INTO sharings (IdProject, UID, UIDsharer) values (?,?,?)");
					myPs.setLong(1, Long.parseLong(request.getParameter("id")));
					myPs.setLong(2, userAdd.getUid());
					myPs.setLong(3, userBean.getUid());
					myPs.executeUpdate();
					myPs.close();
					conexion.close();
				}else{
					System.out.println("Email no encontrado-redirigir a pagina error, usar pagina error defecto con cmabio mensaje");
				}
				loadProjects(request, response);
			}else if(request.getParameter("op").equals("2"))
			{//cambio de permisos
				String[] j = request.getParameterValues("perm");
				Sharing sh = null;
				if(j!=null)
				{
					for(int i = 0; i<j.length; i++)
					{
						if(i!=0)
						{
							if(j[i].charAt(1)==j[i-1].charAt(1))
							{//el id de ahora es del mismo usuario que antes
								Funciones.asignarPermiso(sh, j[i].charAt(0));
							}else{
								sh = ManagementProject.buscarJPACompartidoId(Long.parseLong(String.valueOf(j[i].charAt(1))));
								sh.setAllowDelete((byte)0);
								sh.setAllowDownload((byte)0);
								sh.setAllowRecalculate((byte)0);
								sh.setAllowShare((byte)0);
								Funciones.asignarPermiso(sh, j[i].charAt(0));
							}
						}else{
							sh = ManagementProject.buscarJPACompartidoId(Long.parseLong(String.valueOf(j[i].charAt(1))));
							sh.setAllowDelete((byte)0);
							sh.setAllowDownload((byte)0);
							sh.setAllowRecalculate((byte)0);
							sh.setAllowShare((byte)0);
							Funciones.asignarPermiso(sh, j[i].charAt(0));
						}
					}
				}
				loadProjects(request, response);
			}
		}catch (SQLWarning sqlWarning) {
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

	protected void loadProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");

		List<Sharing> list = ManagementProject.buscarJPAUsuariosCompartidos(userBean, Long.parseLong(request.getParameter("id")));
		List<User> listU = ManagementUser.buscarJPAUserCompartir(userBean);
		List<User> aux = new ArrayList<User>();
		if(list!=null && listU!=null)
		{
			int cnt;
			for(Sharing userSh : list)
			{//borramos de la lista de Sharings usuarios a los que ya ha sido compartido y a usuarios que compartieron ese proyecto
				cnt = 0;
				for(User user : listU)
				{
					if((userSh.getUser1().getUid()==user.getUid())||(userSh.getUser2().getUid()==user.getUid()))
					{
						cnt++;
						if(cnt==2)
						{//nos ahorramos recorrer todos los usuarios por cada comparticion (maximo 2 (el que hizo la comparticion y el compartido))
							break;
						}
					}else{
						aux.add(user);
					}
				}
			}

			request.getSession().setAttribute("userList", aux);
		}else{
			request.getSession().setAttribute("userList", listU);
		}

		request.getSession().setAttribute("userSharedList", list);

		request.getRequestDispatcher("/jsp/shared.jsp?n="+ManagementProject.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))).getProjectName()).forward(request, response);
	}
}
