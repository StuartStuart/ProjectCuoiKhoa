package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Servlet implementation class DeleteUserController
 */
@WebServlet(description = "Xử lý click button [delete] ở màn hình ADM005", urlPatterns = {
		ConstantUtil.DELETE_CONTROLLER })
public class DeleteUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy userId của user cần xóa
			Integer userId = CommonUtil.convertStrToInteger(request.getParameter("userid"));
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			HttpSession session = request.getSession();
			if (tblUserLogic.checkExistedUserId(userId)) {
				// thì gọi method xóa của logic
				if (tblUserLogic.deleteUser(userId)) {
					// chuyển hướng ADM006 - delete
					session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_DELETE_TYPE);
				} else {
					// không delete thành công user
					session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_ERROR_TYPE);
				}
			}else {
				// không delete thành công user
				session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_ERROR_TYPE);
			}
			response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS_CONTROLLER);
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			request.getSession().setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}

}
