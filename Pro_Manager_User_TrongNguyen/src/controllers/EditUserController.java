package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserInforEntity;
import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Servlet implementation class EditUserController
 */
@WebServlet(description = "Điều khiển các sự kiện ADM002, ADM005", urlPatterns = { "/EditUser.do" })
public class EditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy về userId dạng Integer
			Integer userId = CommonUtil.getIntegerFromTextbox(request.getParameter("userid"));
			// lấy về UserInforEntity
			UserInforEntity userInforEntity = new TblUserLogicImpl().getUserInfor(userId);
			// set UserInforEntity lên request
			request.setAttribute("userinfor", userInforEntity);
			// fwd sang ADM005
			request.getRequestDispatcher(ConstantUtil.ADM005_JSP).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy userId của user cần xóa
			Integer userId = CommonUtil.getIntegerFromTextbox(request.getParameter("userid"));
			// gọi method xóa của logic
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			if (tblUserLogic.deleteUser(userId)) {
				// chuyển hướng ADM006 - success
				response.sendRedirect(
						request.getContextPath() + ConstantUtil.SUCCESS + "?type=" + ConstantUtil.ADM006_DELETE_TYPE);
			} else {
				// chuyển hướng ADM006 - error
				response.sendRedirect(
						request.getContextPath() + ConstantUtil.ADM006_JSP + "?type=" + ConstantUtil.ADM006_ERROR_TYPE);
			}

		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
