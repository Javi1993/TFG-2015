package servlet.tfg.eprail;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.tfg.eprail.ManagementUser;
import modeldata.tfg.eprail.User;

/**
 * Servlet implementation class ActivateServlet
 */
@WebServlet("/activate")
public class ActivateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ActivateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nextPage = "/jsp/activate.jsp";
		ManagementUser mu = new ManagementUser();
		User userAux = mu.buscarJPAUser(Long.parseLong(request.getParameter("op")));

		if(userAux!=null)
		{
			if(userAux.getIsValidate()!=1)
			{//la cuenta no está activa
				Timestamp currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
				userAux.setDateRegistration(currentTimestamp);
				userAux.setIsValidate((byte) 1);
				mu.updateJPAUser(userAux);
				request.setAttribute("active", true);
			}
			else{//la cuenta ya está activada
				request.setAttribute("fecha", userAux.getDateRegistration());
				request.setAttribute("active", false);
			}
		}else
		{//no se encuentra ningun usuario con esa UID
			nextPage = "/errors/404.html";
		}

		//redirigimos
		request.getRequestDispatcher(nextPage).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
