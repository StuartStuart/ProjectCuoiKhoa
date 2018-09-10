package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.impl.TblUserLogicImpl;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(description = "LoginFilter", urlPatterns = { "*" })
public class LoginFilter implements Filter {
	/**
	 * Kiểm tra session có tồn tại hay ko Nếu ko thì trở về ADM001
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String webURI = req.getRequestURI();

		// điều kiện URL trỏ đến ADM001
		boolean inLogin = (req.getContextPath() + "/login.do").equalsIgnoreCase(webURI)
				|| (req.getContextPath() + "/").equalsIgnoreCase(webURI);
		boolean isLogin = null != req.getSession().getAttribute("loginId");
		if (inLogin) {
			// ở trang login
			if (isLogin) {
				// đã login

				// thì chuyển hướng ListUser.do
				res.sendRedirect(req.getContextPath() + "/ListUser.do");
			} else {
				// chưa login

				// thì chuyển tiếp login
				req.getRequestDispatcher("/login.do").forward(req, res);
			}
		} else {
			// không ở trang login
			if (isLogin) {
				// đã login

				// thì kiểm tra admin
				String loginId = (String) req.getSession().getAttribute("loginId");
				try {
					if (new TblUserLogicImpl().checkAdminAccount(loginId)) {
						// là adminAccount

						// pass the request along the filter chain
						chain.doFilter(request, response);
					} else {
						// ko là adminAccount

						// gọi logout
						if ((req.getContextPath() + "/logout.do").equalsIgnoreCase(webURI)) {
							chain.doFilter(request, response);
						} else {
							res.sendRedirect(req.getContextPath() + "/logout.do");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					// về error_system
					System.out.println("LoginFilter - doFilter - gọi servlet ShowSystemError");
				}
			} else {
				// chưa login

				if ((req.getContextPath() + "/login.do").equalsIgnoreCase(req.getRequestURI())) {
					// nếu là [/logic]

					// thì cho qua
					chain.doFilter(req, res);
				} else {
					// không là [/logic]

					// thì chuyển hướng login
					res.sendRedirect(req.getContextPath() + "/login.do");
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
}
