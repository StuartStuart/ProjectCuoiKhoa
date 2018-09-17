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
import properties.MessageProperties;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Servlet implementation class EditUserController
 */
@WebServlet(description = "Điều khiển các sự kiện ADM002, ADM005", urlPatterns = { ConstantUtil.SHOW_USER_CONTROLLER })
public class ShowUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			TblUserLogic userLogic = new TblUserLogicImpl();
			// lấy về userId dạng Integer
			Integer userId = CommonUtil.convertStrToInteger(request.getParameter("userid"));

			if (null != userId && userLogic.checkExistedUserId(userId)) {
				// nếu userId là số và tồn tại

				// thì lấy về UserInforEntity
				UserInforEntity userInforEntity = userLogic.getUserInfor(userId);
				// set UserInforEntity lên request
				request.setAttribute("userinfor", userInforEntity);
				// fwd sang ADM005
				request.getRequestDispatcher(ConstantUtil.ADM005_JSP).forward(request, response);
			} else {
				session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, MessageProperties.getValue("MSG005"));
				response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// tránh try - catch khi đọc file properties tại đây
			session.setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}
}
