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
import javax.servlet.http.HttpSession;

import logics.impl.TblUserLogicImpl;
import utils.ConstantUtil;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter(description = "Ko lọc 404, 403", urlPatterns = { "*.do" })
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
		HttpSession session = req.getSession();
		try {
			// đang ở trang login
			boolean inLogin = req.getServletPath().equals(ConstantUtil.LOGIN_CONTROLLER);
			String loginId = (String) session.getAttribute(ConstantUtil.DANH_DAU_LOGIN);
			// đã login thành công
			boolean isLogin = null != loginId && new TblUserLogicImpl().checkAdminAccount(loginId);

			if (isLogin) {
				if (inLogin) {
					res.sendRedirect(req.getContextPath() + ConstantUtil.LIST_USER_CONTROLLER);
				} else {
					chain.doFilter(request, response);
				}
			} else {
				if (inLogin) {
					chain.doFilter(req, res);
				} else {
					res.sendRedirect(req.getContextPath() + ConstantUtil.LOGIN_CONTROLLER);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			res.sendRedirect(req.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
