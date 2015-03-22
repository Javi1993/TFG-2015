package servlet.tfg.eprail;

import java.io.IOException;
import modeldata.tfg.eprail.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.tfg.eprail.ManagementUser;
import funciones.tfg.eprail.Funciones;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		//Rellenamos el bean con los nuevos atributos
		User userBean = (User)request.getSession(true).getAttribute("userBean");
		userBean.setFirstName(request.getParameter("name"));
		userBean.setFamilyName(request.getParameter("apellidos"));
		if(!request.getParameter("pass").equals(""))
		{
			userBean.setPassword(Funciones.cryptMD5("0351"+request.getParameter("pass")));
		}
		ManagementUser mu = new ManagementUser();
		mu.updateJPAUser(userBean);

		//redirigimos
		request.getRequestDispatcher("/jsp/account.jsp").forward(request, response);
	}
}
