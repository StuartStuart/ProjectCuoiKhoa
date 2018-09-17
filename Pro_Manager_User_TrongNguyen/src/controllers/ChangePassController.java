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
import properties.MessageProperties;
import utils.CommonUtil;
import utils.ConstantUtil;
import validates.UserValidate;

/**
 * Servlet implementation class ChangePassController
 */
@WebServlet(description = "Xử lý click button[editPass] ở màn hình ADM005", urlPatterns = {
		ConstantUtil.CHANGE_PASS_CONTROLLER })
public class ChangePassController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// nhận về userId từ request
		Integer userId = CommonUtil.convertStrToInteger(request.getParameter("userid"));
		HttpSession session = request.getSession();
		try {
			if (new TblUserLogicImpl().checkExistedUserId(userId)) {
				// userId có tồn tại
				request.setAttribute("userId", userId);
				request.getRequestDispatcher(ConstantUtil.ADM007_JSP).forward(request, response);
			} else {
				// userId ko tồn tại thì gửi mess user ko tồn tại
				session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, MessageProperties.getValue("MSG005"));
				response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			request.getSession().setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Integer userId = CommonUtil.convertStrToInteger(request.getParameter("userId"));
		try {
			TblUserLogic userLogic = new TblUserLogicImpl();
			HttpSession session = request.getSession();
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
						session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_UPDATE_TYPE);
						response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS_CONTROLLER);
					} else {
						// không update thành công user
						throw new Exception();
					}
				}
			} else {
				// không tồn tại user
				session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, MessageProperties.getValue("MSG005"));
				response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			request.getSession().setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}
}
