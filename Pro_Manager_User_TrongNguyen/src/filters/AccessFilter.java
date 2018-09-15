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

import utils.ConstantUtil;

/**
 * Servlet Filter implementation class AccessFilter
 */
@WebFilter(description = "Ko cho phép doGet() trực tiếp đến SystemError.do, Success.do", urlPatterns = {
		"*.do" })
public class AccessFilter implements Filter {
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("START doFilter - AccessFilter");
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		// nhận vế servletPath
		String servletPath = req.getServletPath();

		// kiểm tra servletPath và xử lý kết quả
		switch (servletPath) {
		case ConstantUtil.SUCCESS_CONTROLLER:
			if (null != session.getAttribute(ConstantUtil.ADM006_TYPE)) {
				// có thông báo ở ADM006
				chain.doFilter(request, response);
			} else {
				// trở về ADM002
				res.sendRedirect(req.getContextPath() + ConstantUtil.LIST_USER_CONTROLLER);
			}
			break;
		case ConstantUtil.SYSTEM_ERROR_CONTROLLER:
			if (null != session.getAttribute(ConstantUtil.SYSTEM_ERROR_TYPE)) {
				// có thông báo ở ADM006
				chain.doFilter(request, response);
			} else {
				// trở về ADM002
				res.sendRedirect(req.getContextPath() + ConstantUtil.LIST_USER_CONTROLLER);
			}
			break;
		default:
			if (null != session.getAttribute(ConstantUtil.ADM006_TYPE)) {
				session.removeAttribute(ConstantUtil.ADM006_TYPE);
			}
			if (null != session.getAttribute(ConstantUtil.SYSTEM_ERROR_TYPE)) {
				session.removeAttribute(ConstantUtil.SYSTEM_ERROR_TYPE);
			}
			chain.doFilter(request, response);
			break;
		}
		System.out.println("END doFilter - AccessFilter");
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}
}
