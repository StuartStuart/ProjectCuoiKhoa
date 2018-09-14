package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import properties.MessageProperties;
import utils.ConstantUtil;

/**
 * Servlet implementation class SuccessController
 */
@WebServlet(description = "Xử lý các logic thông báo thành công và lỗi hệ thống", urlPatterns = {
		ConstantUtil.SUCCESS_CONTROLLER })
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String type = (String) request.getSession().getAttribute(ConstantUtil.ADM006_TYPE);
			if (null != type) {
				switch (type) {
				case ConstantUtil.ADM006_ADD_TYPE:
					// thêm message
					request.setAttribute("adm006msg", MessageProperties.getValue("MSG001"));
					// chuyển tiếp ADM006
					request.getRequestDispatcher(ConstantUtil.ADM006_JSP).forward(request, response);
					break;

				case ConstantUtil.ADM006_UPDATE_TYPE:
					// thêm message
					request.setAttribute("adm006msg", MessageProperties.getValue("MSG002"));
					// chuyển tiếp ADM006
					request.getRequestDispatcher(ConstantUtil.ADM006_JSP).forward(request, response);
					break;

				case ConstantUtil.ADM006_DELETE_TYPE:
					// thêm message
					request.setAttribute("adm006msg", MessageProperties.getValue("MSG003"));
					request.getRequestDispatcher(ConstantUtil.ADM006_JSP).forward(request, response);
					break;

				case ConstantUtil.ADM006_ERROR_TYPE:

				default:
					// chuyển tiếp system error
					request.setAttribute("systemerrormessage", MessageProperties.getValue("MSG005"));
					request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
					break;
				}
			} else {
				response.sendRedirect(request.getContextPath() + ConstantUtil.LIST_USER_CONTROLLER + "?type="
						+ ConstantUtil.ADM002_BACK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}
}
