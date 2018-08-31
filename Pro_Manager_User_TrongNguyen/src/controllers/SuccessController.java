package controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import properties.MessageErrorProperties;
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
			String type = request.getParameter("type");
			switch (type) {
			case ConstantUtil.ADM006_SUCCESS_TYPE:
				// thêm message
				request.setAttribute("adm006msg", MessageProperties.getValue("MSG001"));
				// chuyển tiếp ADM006
				request.getRequestDispatcher(ConstantUtil.ADM006_JSP).forward(request, response);
				break;
			case ConstantUtil.ADM006_ERROR_TYPE:
				// chuyển tiếp system error
				request.getRequestDispatcher(ConstantUtil.SYSTEM_ERROR_JSP).forward(request, response);
				break;
			case ConstantUtil.ADM006_DELETE_TYPE:
			default:
				// thêm message
				request.setAttribute("adm006msg", MessageProperties.getValue("MSG003"));
				request.getRequestDispatcher(ConstantUtil.ADM006_JSP).forward(request, response);
				break;

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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hello from doPost of SuccessController!");
	}

}
