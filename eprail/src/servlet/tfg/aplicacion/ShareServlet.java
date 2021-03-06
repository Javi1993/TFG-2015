package servlet.tfg.aplicacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import modeldata.tfg.aplicacionJPA.Project;
import modeldata.tfg.aplicacionJPA.Sharing;
import modeldata.tfg.aplicacionJPA.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controller.tfg.aplicacion.ManagementProject;
import controller.tfg.aplicacion.ManagementUser;
import funciones.tfg.aplicacion.Funciones;


/**
 * Servlet implementation class ShareServlet
 */
@WebServlet("/share")
public class ShareServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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
			ManagementProject mp = new ManagementProject();
			Sharing sh = mp.buscarJPACompartidoId(Long.parseLong(request.getParameter("i")));
			long compartido = sh.getUser1().getUid();
			mp.borrarJPAObject(sh);
			Sharing aux = null;
			while(null!=(aux=mp.buscarJPAReferido(compartido, Long.parseLong(request.getParameter("id")))))
			{//borramos los usuarios con los que haya compartido el proyecto el usuario al que vamos a eliminar de la lista
				compartido = aux.getUser1().getUid();
				mp.borrarJPAObject(aux);
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

		ManagementUser mu = new ManagementUser();
		ManagementProject mp = new ManagementProject();

		if(request.getParameter("op").equals("1"))
		{//insertamos el nuevo usuario con el que se va a compartir en la BBDD
			User userAdd = mu.buscarJPAUserEmail(request.getParameter("email"));
			if(userAdd!=null)
			{
				Sharing sh = new Sharing();
				sh.setProject(mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))));
				sh.setUser1(userAdd);
				sh.setUser2(userBean);
				mp.addJPACompartido(sh);
			}else{
				String idiom = (String) request.getSession().getAttribute("lenguage");
				if(idiom.equals("SP"))
				{
					request.setAttribute("message", "El email introducido no existe en nuestro registro");
				}else{
					request.setAttribute("message", "This email does not exist in our records");
				}	
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
							sh = mp.buscarJPACompartidoId(Long.parseLong(j[i].substring(1)));
							sh.setAllowDelete((byte)0);
							sh.setAllowDownload((byte)0);
							sh.setAllowRecalculate((byte)0);
							sh.setAllowShare((byte)0);
							Funciones.asignarPermiso(sh, j[i].charAt(0));
						}
					}else{
						sh = mp.buscarJPACompartidoId(Long.parseLong(j[i].substring(1)));
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
	}

	protected void loadProjects(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Buscamos el userBean en la session
		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");

		ManagementProject mp = new ManagementProject();
		List<Sharing> list = mp.buscarJPAUsuariosCompartidos(userBean, Long.parseLong(request.getParameter("id")));//lista con usuarios con los que hemos compartido
		ManagementUser mu = new ManagementUser();
		List<User> listU = mu.buscarJPAUserCompartir(userBean);
		List<User> aux = new ArrayList<User>();
		if(listU!=null)
		{
			Project p = mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id")));
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
			{//borramos de la lista al propio due�o del proyecto y a quien se lo compartio (en caso que no sea due�o)
				Sharing shar = mp.buscarJPAPadre(userBean.getUid(), Long.parseLong(request.getParameter("id")));//si no somos due�os del proyecto nos dira quien nos lo compartio
				if(shar!=null)
				{//el que compartio el proyecto no es el due�o
					for(User user : listU)
					{
						if(user.getUid()!=p.getUser().getUid()&&user.getUid()!=shar.getUser2().getUid())
						{
							aux.add(user);
						}
					}
				}else{//el que compartio el proyecto es el due�o
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
		request.getRequestDispatcher("/jsp/shared.jsp?n="+mp.buscarJPAProyectoId(Long.parseLong(request.getParameter("id"))).getProjectName()).forward(request, response);
	}
}
