package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import utils.CommonUtil;
import utils.ConstantUtil;
import validates.UserValidate;

/**
 * Servlet implementation class ChangePassController
 */
@WebServlet(description = "Xử lý click button[editPass] ở màn hình ADM005", urlPatterns = { "/ChangePass.do" })
public class ChangePassController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// nhận về userId từ request
		Integer userId = CommonUtil.getIntegerFromTextbox(request.getParameter("userid"));

		try {
			if (new TblUserLogicImpl().checkExistedUserId(userId)) {
				// userId có tồn tại
				request.setAttribute("userId", userId);
				request.getRequestDispatcher(ConstantUtil.ADM007_JSP).forward(request, response);
			} else {
				// userId ko tồn tại thì gửi mess user ko tồn tại
				request.setAttribute("systemerrormessage", "user ko tồn tại");
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("START doPost ChangePassController");

		Integer userId = CommonUtil.getIntegerFromTextbox(request.getParameter("userId"));
		try {
			TblUserLogic userLogic = new TblUserLogicImpl();
			if (userLogic.checkExistedUserId(userId)) {
				// userId có tồn tại thì getMess khi validate Pass
				String errMess = new UserValidate().validatePass(request.getParameter("pass"),
						request.getParameter("repass"));
				if (!errMess.isEmpty()) {
					// listMess ! rỗng thì gửi listMess đến 007
					request.setAttribute("adm007message", errMess);
					request.getRequestDispatcher(ConstantUtil.ADM007_JSP).forward(request, response);
				} else {
					// listMess rỗng thì chuẩn bị updatePass
					String salt = CommonUtil.getSalt();
					String pass = CommonUtil.encodeMatKhau(request.getParameter("pass"), salt);
					if (userLogic.updatePasswordForId(userId, pass, salt)) {
						response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS_CONTROLLER + "?type="
								+ ConstantUtil.ADM006_UPDATE_TYPE);
					} else {
						response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS_CONTROLLER + "?type="
								+ ConstantUtil.ADM006_ERROR_TYPE);
					}
				}
			} else {
				// userId ko tồn tại thì gửi mess user ko tồn tại
				response.sendRedirect(
						request.getContextPath() + ConstantUtil.SUCCESS_CONTROLLER + "?type=" + ConstantUtil.ADM006_ERROR_TYPE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}

		System.out.println("END doPost ChangePassController");
	}
}
