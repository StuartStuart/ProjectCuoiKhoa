package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserInforEntity;
import logics.TblUserLogic;
import logics.impl.TblDetailUserJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import properties.MessageProperties;
import utils.ConstantUtil;
import validates.UserValidate;

/**
 * Servlet implementation class AddUserConfirm
 */
@WebServlet(description = "Xử lý khi click vào button Xác nhận của ADM003", urlPatterns = { ConstantUtil.ADD_USER_CONFIRM_CONTROLLER })
public class AddUserConfirmController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// lấy userInfor từ session
			HttpSession session = request.getSession();
			// lấy key
			String key = request.getParameter("key");
			// lấy obj userInfor
			UserInforEntity userInforEntity = (UserInforEntity) session.getAttribute("entityuserinfor" + key);

			// set userInfor lên request
			request.setAttribute("userinfor", userInforEntity);
			// set key lên request
			request.setAttribute("key", key);
			// fwd đến adm004
			request.getRequestDispatcher(ConstantUtil.ADM004_JSP).forward(request, response);
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
		try {
			HttpSession session = request.getSession();
			String key = request.getParameter("key");
			UserInforEntity userInforEntity = (UserInforEntity) session.getAttribute("entityuserinfor" + key);
			session.removeAttribute("entityuserinfor" + key);
			// validate lần 2
			ArrayList<String> listErrMsg = new UserValidate().validateUser(userInforEntity);
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			if (0 == listErrMsg.size()) {
				// ko tồn tại lỗi

				// kiểm tra tiếp các điều kiện update or add
				if (null == userInforEntity.getUserId()) {
					// là case add
					if (tblUserLogic.createUser(userInforEntity)) {
						// tạo thành công user
						session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_ADD_TYPE);
					} else {
						// không tạo thành công user
						session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_ERROR_TYPE);
					}
				} else {
					// là case update: kiểm tra user có tồn tại
					if (tblUserLogic.checkExistedUserId(userInforEntity.getUserId())) {
						// check tồn tại detailUserId - isExistedUserId
						boolean isExistedUserId = new TblDetailUserJapanLogicImpl()
								.checkExistUserId(userInforEntity.getUserId());

						// gọi logic update userInfor
						boolean isUpdateSuccess = tblUserLogic.updateUser(userInforEntity, isExistedUserId);

						// gọi updateUserInfor()
						if (isUpdateSuccess) {
							session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_UPDATE_TYPE);
						} else {
							// không update thành công user
							session.setAttribute(ConstantUtil.ADM006_TYPE, ConstantUtil.ADM006_ERROR_TYPE);
						}
					} else {
						session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, MessageProperties.getValue("MSG005"));
						response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
					}
				}
			} else {
				// tồn tại lỗi thì đến success controller với thông báo lỗi
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
