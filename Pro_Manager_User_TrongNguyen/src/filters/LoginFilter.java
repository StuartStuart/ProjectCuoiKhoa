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

/**
 * Servlet Filter implementation class LoginFilter
 */
// @WebFilter("/*")
@WebFilter(description = "LoginFilter", urlPatterns = { "*.do", "*.jsp" })
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
		System.out.println(req.getContextPath());

		// điều kiện URL trỏ đến ADM001
		boolean dkADM001 = ((req.getContextPath() + "/").equalsIgnoreCase(req.getRequestURI())
				|| (req.getContextPath() + "/jsp/ADM001.jsp").equalsIgnoreCase(req.getRequestURI()));

		if (null == req.getSession().getAttribute("isLogin") && dkADM001) {
			// chưa login và ở ADM001
			chain.doFilter(request, response);
		} else if (null == req.getSession().getAttribute("isLogin")) {
			// chưa login nhưng ko ở ADM001
			res.sendRedirect(req.getContextPath() + "/jsp/ADM001.jsp");
		} else if (null != req.getSession().getAttribute("isLogin") && dkADM001) {
			// đã login và ở ADM001
			res.sendRedirect(req.getContextPath() + "/ListUser.do");
		} else {
			// đã login vào ko ở ADM001
			chain.doFilter(request, response);
		}
	}

	// @Override
	// public void doFilter(ServletRequest req, ServletResponse res, FilterChain
	// chain)
	// throws ServletException, IOException {
	// HttpServletRequest request = (HttpServletRequest) req; // Lấy
	// // HttpServletRequest
	// HttpServletResponse response = (HttpServletResponse) res; // Lấy
	// // HttpServletResponse
	// HttpSession session = request.getSession(false); // Lấy session, nếu
	// // không có trả về
	// // null
	// String reqURI = request.getRequestURI(); // Lấy req URI để so sánh
	// String loginPageEx = "/Pro_Manager_User_TrongNguyen/Login"; // Địa chỉ
	// login page
	// System.out.println("reqURI " + reqURI); // LOG
	//
	// boolean loggedIn = session != null && session.getAttribute("isLogin") !=
	// null;
	// boolean loginRequest =
	// request.getRequestURI().equals("/Pro_Manager_User_TrongNguyen/");
	//
	//
	// if (loggedIn || loginRequest || reqURI.equals(loginPageEx)) {
	// if (loggedIn && loginRequest) {
	// request.getRequestDispatcher("/ListUser.do").forward(request, response);
	// }
	// System.out.println("URI: " + request.getRequestURI()); // LOG
	// chain.doFilter(request, response);
	// } else {
	// System.out.println("URL: " + request.getRequestURL()); // LOG
	// request.getRequestDispatcher("jsp/ADM001.jsp").forward(request,
	// response);
	// }
	// }

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
