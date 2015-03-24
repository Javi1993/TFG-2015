package servlet.tfg.eprail;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import funciones.tfg.eprail.Funciones;
import modeldata.tfg.eprailJPA.User;

/**
 * Servlet implementation class LogoutServlet
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogoutServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Buscamos el userBean en la session

		HttpSession session = request.getSession(true);
		User userBean = (User) session.getAttribute("userBean");

		try{
			// gets absolute path of the web application
			String applicationPath = request.getServletContext().getRealPath("");

			//borramos el directorio temporal del usuario
			File padre = new File(applicationPath + File.separator + "temp" + File.separator + userBean.getUid());
			if(padre!=null)
			{
				Funciones.borrarDirectorio(padre);

				if (padre.delete()){
					System.out.println("El directorio temporal de user-"+userBean.getUid()+" ha sido borrado correctamente");
				}else{
					System.out.println("El directorio temporal de user-"+userBean.getUid()+" no se ha podido borrar");
				}
			}
		}catch(NullPointerException e)
		{//caso de que el usuario no tenga ningun proyecto
			System.out.println("El directorio temporal de user-"+userBean.getUid()+" es: "+e.getMessage());
		}
		request.getSession(true).invalidate();
		request.getRequestDispatcher("/index.html").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
