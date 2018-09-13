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
import utils.ConstantUtil;

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
		// System.out.println("login");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String webURI = req.getRequestURI();
		if ((req.getContextPath() + "/").equalsIgnoreCase(webURI)) {
			request.getRequestDispatcher(ConstantUtil.ADM001_JSP).forward(req, res);
		} else if (!webURI.matches(".do$") && !webURI.matches(".jsp$")) {
			chain.doFilter(req, res);
		} else {
			// điều kiện URL trỏ đến ADM001
			boolean inLogin = (req.getContextPath() + ConstantUtil.LOGIN_CONTROLLER).equalsIgnoreCase(webURI);
			boolean isLogin = null != req.getSession().getAttribute(ConstantUtil.DANH_DAU_LOGIN);
			if (inLogin) {
				// ở trang login
				if (isLogin) {
					// đã login thì chuyển hướng ListUser.do
					res.sendRedirect(req.getContextPath() + ConstantUtil.LIST_USER_CONTROLLER);
				} else {
					// chưa login thì chuyển tiếp login
					req.getRequestDispatcher(ConstantUtil.LOGIN_CONTROLLER).forward(req, res);
				}
			} else {
				// không ở trang login
				if (isLogin) {
					// đã login thì kiểm tra admin
					String loginId = (String) req.getSession().getAttribute(ConstantUtil.DANH_DAU_LOGIN);
					try {
						if (new TblUserLogicImpl().checkAdminAccount(loginId)) {
							// là adminAccount pass the request along the filter chain
							chain.doFilter(request, response);
						} else {
							// ko là adminAccount gọi logout
							if ((req.getContextPath() + ConstantUtil.LOGOUT_CONTROLLER).equalsIgnoreCase(webURI)) {
								chain.doFilter(request, response);
							} else {
								res.sendRedirect(req.getContextPath() + ConstantUtil.LOGOUT_CONTROLLER);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						// về error_system
						res.sendRedirect(req.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
					}
				} else {
					// chưa login

					if ((req.getContextPath() + ConstantUtil.LOGIN_CONTROLLER).equalsIgnoreCase(req.getRequestURI())) {
						// nếu là [/logic] thì cho qua
						chain.doFilter(req, res);
					} else {
						// không là [/logic] thì chuyển hướng login
						res.sendRedirect(req.getContextPath() + ConstantUtil.LOGIN_CONTROLLER);
					}
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
