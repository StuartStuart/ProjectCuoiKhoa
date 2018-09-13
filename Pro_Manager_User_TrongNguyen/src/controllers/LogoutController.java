package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ConstantUtil;

/**
 * 
 * Điều khiển logout tài khoản Servlet implementation class LogoutController
 */
@WebServlet(description = "LogoutController", urlPatterns = { ConstantUtil.LOGOUT_CONTROLLER })
public class LogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute(ConstantUtil.DANH_DAU_LOGIN);
		session.invalidate(); // xóa session
		response.sendRedirect(request.getContextPath() + ConstantUtil.LOGIN_CONTROLLER);
	}
}