package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.UserInforEntity;
import logics.TblUserLogic;
import logics.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;
import utils.ConstantUtil;

/**
 * Servlet implementation class AddUserConfirm
 */
@WebServlet(description = "Xử lý khi click vào button Xác nhận của ADM003", urlPatterns = { "/AddUserConfirm.do" })
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
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
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
			HttpSession session = request.getSession();
			String keyParam = request.getParameter("keyEntity");
			UserInforEntity userInforEntity = (UserInforEntity) session.getAttribute("entityuserinfor" + keyParam);
			if (null == userInforEntity.getUserId()) {
				// obj thực hiện create user
				TblUserLogic tblUserLogic = new TblUserLogicImpl();
				// email
				String email = userInforEntity.getEmail();
				// login name
				String loginName = userInforEntity.getLoginName();
				// là tạo user thành công
				boolean isCreatedUser = !tblUserLogic.checkExistedEmail(null, email)
						&& !tblUserLogic.checkExistedLoginName(null, loginName)
						&& tblUserLogic.createUser(userInforEntity);
				if (isCreatedUser) {
					// tạo thành công user
					response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS + "?type="
							+ ConstantUtil.ADM006_SUCCESS_TYPE);
				} else {
					// không tạo thành công user
					response.sendRedirect(request.getContextPath() + ConstantUtil.SUCCESS + "?type="
							+ ConstantUtil.ADM006_ERROR_TYPE);
				}
			} else if (new TblUserLogicImpl().updateUser(userInforEntity)) {
				response.sendRedirect(
						request.getContextPath() + ConstantUtil.SUCCESS + "?type=" + ConstantUtil.ADM006_UPADATE_TYPE);
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
