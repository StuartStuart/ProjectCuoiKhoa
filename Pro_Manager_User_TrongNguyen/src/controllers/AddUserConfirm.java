package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserInforEntity;
import logics.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;
import utils.ConstantUtil;

/**
 * Servlet implementation class AddUserConfirm
 */
@WebServlet(description = "Xử lý khi click vào button Xác nhận của ADM003", urlPatterns = { "/AddUserConfirm.do" })
public class AddUserConfirm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher(ConstantUtil.ADM004_JSP).forward(request, response);
		;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			System.out.println(null == request.getSession().getAttribute("entityuserinfor"));
			if (new TblUserLogicImpl()
					.createUser((UserInforEntity) request.getSession().getAttribute("entityuserinfor"))) {
				response.sendRedirect(
						request.getContextPath() + ConstantUtil.SUCCESS + "?type=" + ConstantUtil.ADM006_SUCCESS_TYPE);
			} else {
				response.sendRedirect(
						request.getContextPath() + ConstantUtil.SUCCESS + "?type=" + ConstantUtil.ADM006_ERROR_TYPE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
