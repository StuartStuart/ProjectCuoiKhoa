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
@WebFilter(description = "LoginFilter", urlPatterns = { "*.do" })
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

		System.out.println(req.getRequestURI());
		// điều kiện URL trỏ đến ADM001
		boolean inLogin = (req.getContextPath() + "/login.do").equalsIgnoreCase(webURI)
				|| (req.getContextPath() + "/").equalsIgnoreCase(webURI);
		boolean isLogin = null != req.getSession().getAttribute("isLogin");
		if (inLogin && !isLogin) {
			// đường dẫn là login
			if (null == req.getSession(false)) {
				// chưa vào lần nào
				req.getRequestDispatcher("jsp/ADM001.jsp").forward(request, response);
			} else if (null == request.getParameter("adm001loginid")) {
				// đã vào và cố tình vào lại
				req.getRequestDispatcher("jsp/ADM001.jsp").forward(request, response);
			} else {
				// click button
				chain.doFilter(request, response);
			}
		} else if (isLogin && inLogin) {
			// đã login nhưng vào ADM001
			res.sendRedirect(req.getContextPath() + "/ListUser.do");
		} else if (!isLogin && !inLogin) {
			// ko ở ADM001 và chưa login
			res.sendRedirect(req.getContextPath() + "/login.do");
		} else {
			// ko ở ADM001 và đã login
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
