package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import properties.MessageErrorProperties;
import utils.ConstantUtil;

/**
 * Servlet implementation class SystemErrorController
 */
@WebServlet(description = "Chuyển hướng trang khi lỗi try - catch", urlPatterns = {
		ConstantUtil.SYSTEM_ERROR_CONTROLLER })
public class SystemErrorController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			// nhận về thông báo tren session
			String errMsg = (String) session.getAttribute(ConstantUtil.SYSTEM_ERROR_TYPE);
			if (ConstantUtil.SYSTEM_ERROR_TYPE.equals(errMsg)) {
				// là dạng lỗi system
				session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, MessageErrorProperties.getValue("Error015"));
			}
			
			request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Lỗi MessageErrorProperties");
		}
	}
}
