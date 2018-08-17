package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserInforEntity;
import logics.impl.MstGroupLogicImpl;
import logics.impl.TblUserLogicImpl;
import properties.ConfigProperties;
import utils.ConstantUtil;

/**
 * Servlet implementation class ListUserController
 */
@SuppressWarnings("serial")
@WebServlet(description = "ListUserController", urlPatterns = "/ListUser.do")
public class ListUserController extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		try {
			switch (type) {
			case ConstantUtil.ADM002_NEW_STATUS:
				// nhận dữ liệu từ ADM002
				Object fullName = request.getParameter(ConfigProperties.getValue("ADM002_Textbox"));
				Object groupId = request.getParameter(ConfigProperties.getValue("ADM002_GroupId"));
				// nhận về danh sách User và đẩy lên ADM002
				ArrayList<UserInforEntity> listUserInfors = null;
				if (null == fullName && null == groupId) {
					listUserInfors = new TblUserLogicImpl().getListUser(0, 10, 0, null,
							ConfigProperties.getValue("ADM002_FirstSorting"), ConstantUtil.SAP_XEP_TANG,
							ConstantUtil.SAP_XEP_TANG, ConstantUtil.SAP_XEP_GIAM);
				} else {

				}
				request.setAttribute("groups", new MstGroupLogicImpl().getAllMstGroup());
				request.setAttribute("userInfors", listUserInfors);
				// request đến ADM002
				request.getRequestDispatcher("jsp/ADM002.jsp").forward(request, response);
				break;
			case ConstantUtil.ADM002_SAVED_STATUS:
				break;
			default:

				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect("jsp/System_Error.jsp"); // sẽ chuyển đến
															// system_error
		}

	}
}
