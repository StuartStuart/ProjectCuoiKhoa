package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.MstGroupDaoImpl;
import entities.TblMstGroupEntity;
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
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			/*
			 * ADM002 - search
			 */
			// nhận fullName
			String fullName = (String) request.getSession().getAttribute((ConfigProperties.getValue("ADM002_Textbox")));

			// nhận groupId từ session
			Integer groupId = (Integer) request.getSession()
					.getAttribute((ConfigProperties.getValue("ADM002_GroupId")));

			// tạo ADM002 - search select - option groupId
			ArrayList<String> groups = (ArrayList) request.getSession().getAttribute("groups");
			// tạo search - sortType
			String sortType = ConfigProperties.getValue("ADM002_FirstSorting");

			// tạo search - sortTypeSymbols
			String firstSortSymbol = ConstantUtil.ADM002_ASC;
			String secondSortSymbol = ConstantUtil.ADM002_ASC;
			String thirdSortSymbol = ConstantUtil.ADM002_DESC;

			// các giá trị của sortTypes
			String typeByFullName = ConstantUtil.SAP_XEP_TANG;
			String typeByCodeLevel = ConstantUtil.SAP_XEP_TANG;
			String typeByEndDate = ConstantUtil.SAP_XEP_GIAM;

			// giá trị ban đầu của offset
			int offset = 0;

			// tạo search - htmlPaging

			/*
			 * Các loại vào ADM002
			 */
			switch (request.getParameter("type")) {
			case ConstantUtil.ADM002_BACK:
				// get old status textbox[fullName]

				// get old status combobox[groupId]

				// get old status link[sortFullName]

				// get old status link[sortCodeLevel]

				// get old status link[sortEndDate]

				// get old status html[Paging]

				break;
			case ConstantUtil.ADM002_SORT:
				// sắp xếp cũ là ASC
				boolean isASCOldSort = (int) ConstantUtil.ADM002_ASC.toString().charAt(0) == Integer
						.parseInt(request.getParameter("sort"));

				switch (request.getParameter("priority")) {
				case ConstantUtil.ADM002_FULL_NAME_SORT:
					// xác định loại sắp xếp được ưu tiên
					sortType = ConfigProperties.getValue("ADM002_FirstSorting");

					if (isASCOldSort) {
						// thay đổi hiển thị trên màn hình
						firstSortSymbol = ConstantUtil.ADM002_DESC;
						// xác định loại sắp xếp là tăng hay giảm
						typeByFullName = ConstantUtil.SAP_XEP_GIAM;
					} else {
						firstSortSymbol = ConstantUtil.ADM002_ASC;
						typeByFullName = ConstantUtil.SAP_XEP_TANG;
					}
					break;
				case ConstantUtil.ADM002_CODE_LEVEL_SORT:
					sortType = ConfigProperties.getValue("ADM002_SecondSorting");

					if (isASCOldSort) {
						secondSortSymbol = ConstantUtil.ADM002_DESC;
						typeByEndDate = ConstantUtil.SAP_XEP_GIAM;
					} else {
						secondSortSymbol = ConstantUtil.ADM002_ASC;
						typeByEndDate = ConstantUtil.SAP_XEP_TANG;
					}
					break;
				case ConstantUtil.ADM002_END_DATE_SORT:
					sortType = ConfigProperties.getValue("ADM002_ThirdSorting");

					if (isASCOldSort) {
						thirdSortSymbol = ConstantUtil.ADM002_DESC;
						typeByEndDate = ConstantUtil.SAP_XEP_GIAM;
					} else {
						thirdSortSymbol = ConstantUtil.ADM002_ASC;
						typeByEndDate = ConstantUtil.SAP_XEP_TANG;
					}
					break;
				default:
					break;
				}
			case ConstantUtil.ADM002_PAGING:
				// tính giá trị offset

				break;

			case ConstantUtil.ADM002_SEARCH:
				fullName = request.getParameter(ConfigProperties.getValue("ADM002_Textbox"));

				String groupType = request.getParameter((ConfigProperties.getValue("ADM002_GroupId")));
				groupId = Integer.parseInt((null == groupType) ? "0" : groupType);
				groups = new ArrayList<>();

				for (TblMstGroupEntity group : new MstGroupLogicImpl().getAllMstGroup()) {
					// tạo html
					StringBuilder groupHTML = new StringBuilder("");
					groupHTML.append("<option value='");
					groupHTML.append(group.getGroupId() + "'");
					// thêm điều kiện selected vào đây
					if(group.getGroupId()==groupId) {
						groupHTML.append(" selected");
					}
					// thêm đoạn html còn lại
					groupHTML.append(">");
					groupHTML.append(group.getGroupName());
					groupHTML.append("</option>");

					// thêm vào list
					groups.add(groupHTML.toString());
				}
				break;
			default:
				break;
			}
			/*
			 * gửi dữ liệu lên ADM002
			 */
			// gửi giá trị textbox[fullName]
			request.getSession().setAttribute(ConfigProperties.getValue("ADM002_Textbox"),
					(null == fullName) ? "" : fullName);
			request.getSession().setAttribute(ConfigProperties.getValue("ADM002_GroupId"), groupId);
			/*
			 * 
			 * 
			 * 
			 * lưu giá trị combobox[groupId] lên session viết vào đây
			 * 
			 * 
			 * 
			 */
			request.getSession().setAttribute("groups", groups);
			// gửi các icon
			request.getSession().setAttribute("symbolFullName", firstSortSymbol);
			request.getSession().setAttribute("symbolCodeLevel", secondSortSymbol);
			request.getSession().setAttribute("symbolEndDate", thirdSortSymbol);
			// gửi danh sách user
			request.getSession().setAttribute("userInfors",
					new TblUserLogicImpl().getListUser(offset, CommonUtil.getLimit(), (null == groupId) ? 0 : groupId,
							fullName, sortType, typeByFullName, typeByCodeLevel, typeByEndDate));
			// gửi chuỗi paging
			request.getSession().setAttribute("htmlPaging",
					"<a href=\"#\"><<</a> " + "&nbsp;" + "<a href=\"#\">1</a>" + " &nbsp;" + "<a href=\"#\">2</a>"
							+ "&nbsp;" + "<a href=\"#\">3</a>" + "&nbsp;" + "<a href=\"#\">>></a>");
			// request đến ADM002
			request.getRequestDispatcher("jsp/ADM002.jsp").forward(request, response);
		} catch (

		Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect("jsp/System_Error.jsp");
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

	public static void main(String[] args) {
		System.out.println();
	}
}
