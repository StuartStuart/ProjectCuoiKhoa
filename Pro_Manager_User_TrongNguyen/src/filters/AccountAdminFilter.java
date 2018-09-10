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
 * Servlet Filter implementation class AccountAdminFilter
 */
@WebFilter(description = "Chỉ cho phép admin thực hiện thêm, sửa, xóa user", urlPatterns = { "/DeleteUser.do" })
public class AccountAdminFilter implements Filter {
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		try {
			String loginId = (String) req.getSession().getAttribute("loginId");
			System.out.println(loginId);
			if (new TblUserLogicImpl().checkAdminAccount(loginId)) {
				// là adminAccount

				// pass the request along the filter chain
				chain.doFilter(request, response);
			} else {
				// ko là adminAccount

				// hiện thông báo bạn ko là admin
				res.sendRedirect(
						req.getContextPath() + ConstantUtil.SUCCESS + "?type=" + ConstantUtil.ADM006_ERROR_TYPE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("systemerrormessage", "Thông báo lỗi hệ thống!");
			request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
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
