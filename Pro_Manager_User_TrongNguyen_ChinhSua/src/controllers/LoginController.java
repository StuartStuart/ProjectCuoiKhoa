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

import properties.MessageErrorProperties;
import validates.LoginValidate;

/**
 * Servlet implementation class LoginController
 */
@SuppressWarnings("serial")
@WebServlet(description = "LoginController", urlPatterns = { "/login.do" })
public class LoginController extends HttpServlet {

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("jsp/ADM001.jsp").forward(req, resp);
	}

	/**
	 * di toi ADM002 hoac o lai ADM001 kem thong bao
	 * 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// nhận về chuỗi đã nhập trong textbox [login]
			String loginId = new String(request.getParameter("adm001loginid"));
			String password = new String(request.getParameter("adm001password"));

			// nhận thông báo tương ứng với textbox [login] và [password] đã nhập
			ArrayList<String> listMessage = new LoginValidate().validate(loginId, password);
			if (0 == listMessage.size()) { // login thành công
				request.getSession().setAttribute("loginId", loginId); // đánh dấu đăng nhập vào
				response.sendRedirect("ListUser.do");
			} else { // login không thành công
				request.setAttribute("adm001message", listMessage);
				request.setAttribute("adm001loginid", loginId);
				request.getRequestDispatcher("jsp/ADM001.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher("/jsp/System_Error.jsp").forward(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}
}
