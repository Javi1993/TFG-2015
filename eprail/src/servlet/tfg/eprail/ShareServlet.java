package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.util.ArrayList;
import java.util.List;

import modeldata.tfg.eprail.Project;
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
			Sharing sh = ManagementProject.buscarJPACompartidoId(Long.parseLong(request.getParameter("i")));
			long compartido = sh.getUser1().getUid();
			ManagementProject.borrarJPAObject(sh);
			Sharing aux = null;
			while(null!=(aux=ManagementProject.buscarJPAReferido(compartido, Long.parseLong(request.getParameter("id")))))
			{//borramos los usuarios con los que haya compartido el proyecto el usuario al que vamos a eliminar de la lista
				System.out.println("+++++++++++++ "+aux.getUser1().getUid());
				compartido = aux.getUser1().getUid();
				ManagementProject.borrarJPAObject(aux);
			}
			loadProjects(request, response);
		}
		else if(request.getParameter("op").equals("2"))
		{//mostramos la lista de usuarios con acceso a ese documento y de los cuales el usuario les dio acceso
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
								sh = ManagementProject.buscarJPACompartidoId(Long.parseLong(j[i].substring(1)));
								sh.setAllowDelete((byte)0);
								sh.setAllowDownload((byte)0);
								sh.setAllowRecalculate((byte)0);
								sh.setAllowShare((byte)0);
								Funciones.asignarPermiso(sh, j[i].charAt(0));
							}
						}else{
							sh = ManagementProject.buscarJPACompartidoId(Long.parseLong(j[i].substring(1)));
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

		List<Sharing> list = ManagementProject.buscarJPAUsuariosCompartidos(userBean, Long.parseLong(request.getParameter("id")));//lista con usuarios con los que hemos compartido
		List<User> listU = ManagementUser.buscarJPAUserCompartir(userBean);
		List<User> aux = new ArrayList<User>();
		if(listU!=null)
		{
			Project p = ManagementProject.buscarJPAProyectoId(Long.parseLong(request.getParameter("id")));
			if(list!=null)
			{//Esta compartiendo ya el proyecto a otras personas
				for(Sharing userSh : list)
				{//borramos de la lista de Sharings usuarios a los que ya ha sido compartido y a usuarios que compartieron ese proyecto
					for(User user : listU)
					{
						if((userSh.getUser1().getUid()!=user.getUid())&&(userSh.getUser2().getUid()!=user.getUid())&&(user.getUid()!=p.getUser().getUid()))
						{
							aux.add(user);
						}
					}
				}
			}else//no comparte todavia el proyecto con otras personas
			{//borramos de la lista al propio dueño del proyecto y a quien se lo compartio (en caso que no sea dueño)
				Sharing shar = ManagementProject.buscarJPAPadre(userBean.getUid(), Long.parseLong(request.getParameter("id")));//si no somos dueños del proyecto nos dira quien nos lo compartio
				if(shar!=null)
				{//el que compartio el proyecto no es el dueño
					for(User user : listU)
					{
						if(user.getUid()!=p.getUser().getUid()&&user.getUid()!=shar.getUser2().getUid())
						{
							aux.add(user);
						}
					}
				}else{//el que compartio el proyecto es el dueño
					for(User user : listU)
					{
						if(user.getUid()!=p.getUser().getUid())
						{
							aux.add(user);
						}
					}
				}
			}
		}
		request.setAttribute("userList", aux);
		request.setAttribute("userSharedList", list);
		request.getRequestDispatcher("/jsp/shared.jsp?n="+ManagementProject.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))).getProjectName()).forward(request, response);
	}
}
