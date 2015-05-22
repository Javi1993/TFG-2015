package filter.tfg.aplicacion;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import modeldata.tfg.aplicacionJPA.User;

/**
 * Servlet Filter implementation class NoLogFilter
 */
@WebFilter("/NoLogFilter")
public class NoLogFilter implements Filter {

    /**
     * Default constructor. 
     */
    public NoLogFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// Buscamos el userBean en la session
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);
		if(session != null && session.getAttribute("userBean")!=null)
		{
			User userBean = (User) session.getAttribute("userBean");
			if(userBean.getLoggedIn())
			{
				chain.doFilter(request, response);//esta logueado
			}else{
				req.getRequestDispatcher("/errors/error-login.jsp").forward(request, response);//no hay login
			}
		}else{
			req.getRequestDispatcher("/errors/error-login.jsp").forward(request, response);//no hay login
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
