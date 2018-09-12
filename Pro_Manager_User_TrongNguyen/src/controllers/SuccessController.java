package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import properties.MessageProperties;
import utils.ConstantUtil;

/**
 * Servlet implementation class SuccessController
 */
@WebServlet(description = "Xử lý các logic thông báo thành công và lỗi hệ thống", urlPatterns = { "/Success.do" })
public class SuccessController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			if (null != session.getAttribute(ConstantUtil.THROUGH_ADM006)) {
				String type = request.getParameter("type");
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
				response.sendRedirect(request.getContextPath());
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}
}