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
import properties.MessageProperties;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Servlet implementation class EditUserController
 */
@WebServlet(description = "Điều khiển các sự kiện ADM002, ADM005", urlPatterns = { "/ShowUser.do" })
public class ShowUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			TblUserLogic userLogic = new TblUserLogicImpl();
			// lấy về userId dạng Integer
			Integer userId = CommonUtil.getIntegerFromTextbox(request.getParameter("userid"));

			if (null != userId && userLogic.checkExistedUserId(userId)) {
				// nếu userId là số và tồn tại

				// thì lấy về UserInforEntity
				UserInforEntity userInforEntity = userLogic.getUserInfor(userId);
				// set UserInforEntity lên request
				request.setAttribute("userinfor", userInforEntity);
				// fwd sang ADM005
				request.getRequestDispatcher(ConstantUtil.ADM005_JSP).forward(request, response);
			} else {
				request.setAttribute("systemerrormessage", MessageProperties.getValue("MSG005"));
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
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
