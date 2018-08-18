/**
 * Copyright(C) 2018 	Luvina
 * ManagerUserTrongNguyenProperties.java, Aug 14, 2018, TrongNguyen
 */
package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.ConstantUtil;
import validates.LoginValidate;

/**
 * Servlet implementation class LoginController
 */
@SuppressWarnings("serial")
@WebServlet(description = "LoginController", urlPatterns = { "/Login" })
public class LoginController extends HttpServlet {
	/**
	 * di toi ADM002 hoac o lai ADM001 kem thong bao
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// nhận về chuỗi đã nhập trong textbox [login]
			String loginId = new String(request.getParameter("loginId"));
			String password = new String(request.getParameter("password"));

			// nhận thông báo tương ứng với textbox [login] và [password] đã nhập
			ArrayList<String> listMessage = new LoginValidate().getListMessage(loginId, password);
			if (0 == listMessage.size()) { // login thành công
				HttpSession session = request.getSession(true); // tạo session
				session.setAttribute("isLogin", true); // đánh dấu đăng nhập vào session
				response.sendRedirect("ListUser.do?type=" + ConstantUtil.ADM002_SEARCH);
//				request.getRequestDispatcher("ListUser.do").forward(request, response); // sẽ chuyển đến ADM001
			} else { // login không thành công
				request.setAttribute("message", listMessage); // gửi message đến ADM001
				request.setAttribute("loginId", loginId); // gửi loginId đến ADM001
				request.setAttribute("password", password); // gửi pass đến ADM001
				request.getRequestDispatcher("jsp/ADM001.jsp").forward(request, response); // chuyển đến trang tương ứng
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect("jsp/System_Error.jsp"); // sẽ chuyển đến system_error
		}

	}
}
