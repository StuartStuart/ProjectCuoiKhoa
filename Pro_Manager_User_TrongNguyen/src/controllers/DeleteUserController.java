package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Servlet implementation class DeleteUserController
 */
@WebServlet(description = "Xử lý click button [delete] ở màn hình ADM005", urlPatterns = { "/DeleteUser.do" })
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
			Integer userId = CommonUtil.getIntegerFromTextbox(request.getParameter("userid"));
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			if (tblUserLogic.checkExistedUserId(userId)) {
				// nếu tồn tại userId
				
				// thì gọi method xóa của logic
				if (tblUserLogic.deleteUser(userId)) {
					// chuyển hướng ADM006 - success
					response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS + "?type="
							+ ConstantUtil.ADM006_DELETE_TYPE);
				} else {
					// chuyển hướng ADM006 - error
					response.sendRedirect(request.getContextPath() + ConstantUtil.ADM006_JSP + "?type="
							+ ConstantUtil.ADM006_ERROR_TYPE);
				}
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
