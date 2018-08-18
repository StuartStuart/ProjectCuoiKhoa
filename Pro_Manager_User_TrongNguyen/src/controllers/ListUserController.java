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
import utils.CommonUtil;
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
		try {
			request.setAttribute("groups", new MstGroupLogicImpl().getAllMstGroup());

			int offset = 0, groupId = 0;
			String fullName = null, sortType = ConfigProperties.getValue("ADM002_FirstSorting");
			String typeByFullName = ConstantUtil.SAP_XEP_TANG, typeByCodeLevel = ConstantUtil.SAP_XEP_TANG,
					typeByEndDate = ConstantUtil.SAP_XEP_GIAM;
			switch (request.getParameter("type")) {
			case ConstantUtil.ADM002_BACK:
				break;
			case ConstantUtil.ADM002_PAGING:
			case ConstantUtil.ADM002_SORT:
				switch (request.getParameter("priority")) {
				case ConstantUtil.ADM002_FULL_NAME_SORT:
					sortType = ConfigProperties.getValue("ADM002_FirstSorting");
					if (request.getParameter("sort").equals(ConstantUtil.ADM002_ASC.toString())) {
						request.setAttribute("symbolCodeLevel", ConstantUtil.ADM002_DESC);
					} else {
						request.setAttribute("symbolCodeLevel", ConstantUtil.ADM002_ASC);
					}
					break;
				case ConstantUtil.ADM002_CODE_LEVEL_SORT:
					sortType = ConfigProperties.getValue("ADM002_SecondSorting");
					if (ConstantUtil.ADM002_ASC.equals(request.getAttribute("symbolCodeLevel"))) {
						request.setAttribute("symbolCodeLevel", ConstantUtil.ADM002_DESC);
					} else {
						request.setAttribute("symbolCodeLevel", ConstantUtil.ADM002_ASC);
					}
					break;
				case ConstantUtil.ADM002_END_DATE_SORT:
					sortType = ConfigProperties.getValue("ADM002_ThirdSorting");
					if (ConstantUtil.ADM002_ASC.equals(request.getAttribute("symbolEndDate"))) {
						request.setAttribute("symbolEndDate", ConstantUtil.ADM002_DESC);
					} else {
						request.setAttribute("symbolEndDate", ConstantUtil.ADM002_ASC);
					}
					break;

				default:
					break;
				}
			case ConstantUtil.ADM002_SEARCH:
				request.setAttribute("symbolFullName", ConstantUtil.ADM002_ASC);
				request.setAttribute("symbolCodeLevel", ConstantUtil.ADM002_ASC);
				request.setAttribute("symbolEndDate", ConstantUtil.ADM002_DESC);
			default:// case ConstantUtil.ADM002_SEARCH:
				// fullName
				fullName = request.getParameter(ConfigProperties.getValue("ADM002_Textbox"));
				// groupId
				String loaiNhom = request.getParameter(ConfigProperties.getValue("ADM002_GroupId"));
				groupId = (null == loaiNhom) ? 0 : Integer.parseInt(loaiNhom);
				break;
			}

			request.setAttribute("userInfors", new TblUserLogicImpl().getListUser(offset, CommonUtil.getLimit(),
					groupId, fullName, sortType, typeByFullName, typeByCodeLevel, typeByEndDate));
			// request đến ADM002
			request.getRequestDispatcher("jsp/ADM002.jsp").forward(request, response);
		} catch (

		Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect("jsp/System_Error.jsp"); // sẽ chuyển đến
															// system_error
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
