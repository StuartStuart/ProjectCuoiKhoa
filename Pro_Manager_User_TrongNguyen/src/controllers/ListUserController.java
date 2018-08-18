package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			int userLimit = CommonUtil.getLimit();
			/*
			 * ADM002 - search
			 */
			// nhận fullName
			String fullName = (String) request.getSession().getAttribute((ConfigProperties.getValue("ADM002_Textbox")));

			// nhận groupId từ session
			Integer groupId = (Integer) request.getSession()
					.getAttribute((ConfigProperties.getValue("ADM002_GroupId")));

			// tạo ADM002 - search select - option groupId
			ArrayList<String> groupHTMLs = (ArrayList) request.getSession().getAttribute("groups");
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
			int curentPage = 1;

			// tạo search - htmlPaging
			ArrayList<String> pagingHTMLs = (ArrayList) request.getSession()
					.getAttribute((ConfigProperties.getValue("ADM002_Paging")));
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
				System.out.println("Đã vào paging");

				String pageNums = request.getParameter("page");
				switch (pageNums) {
				case ConstantUtil.ADM002_PAGE_BACK:
					System.out.println("Đã vào page back");
					break;
				case ConstantUtil.ADM002_PAGE_NEXT:
					System.out.println("Đã vào page next");
					break;
				default:
					System.out.println("Đã vào page " + pageNums);
					break;
				}
				break;

			case ConstantUtil.ADM002_SEARCH:
				// nhận full name
				fullName = request.getParameter(ConfigProperties.getValue("ADM002_Textbox"));

				// nhận groupId
				String groupType = request.getParameter((ConfigProperties.getValue("ADM002_GroupId")));
				groupId = Integer.parseInt((null == groupType) ? "0" : groupType);

				/*
				 * nhận htmlgroup
				 */
				groupHTMLs = new ArrayList<>();
				for (TblMstGroupEntity group : new MstGroupLogicImpl().getAllMstGroup()) {
					// tạo html
					StringBuilder groupHTML = new StringBuilder("");
					groupHTML.append("<option value='");
					groupHTML.append(group.getGroupId() + "'");
					// thêm điều kiện selected vào đây
					if (group.getGroupId() == groupId) {
						groupHTML.append(" selected");
					}
					// thêm đoạn html còn lại
					groupHTML.append(">");
					groupHTML.append(group.getGroupName());
					groupHTML.append("</option>");

					// thêm vào list
					groupHTMLs.add(groupHTML.toString());
				}

				/*
				 * xây dụng htmlPaging
				 */
				int totalUser = new TblUserLogicImpl().getTotalUser(groupId, fullName);
				pagingHTMLs = new ArrayList<>();
				ArrayList<Integer> listPageNum = CommonUtil.getListPaging(totalUser,
						Integer.parseInt(ConfigProperties.getValue("User_Limit")), curentPage);
				// thêm paging <<
				if (1 != listPageNum.get(0)) {
					pagingHTMLs.add("<a href='ListUser.do?type=" + ConstantUtil.ADM002_PAGING + "&page="
							+ ConstantUtil.ADM002_PAGE_BACK + "'><<</a> " + "&nbsp;");
				}
				// thêm paging N
				for (Integer pageNum : listPageNum) {
					pagingHTMLs.add("<a href='ListUser.do?type=" + ConstantUtil.ADM002_PAGING + "&page=" + pageNum
							+ "'>" + pageNum + "</a>" + " &nbsp;");
				}
				// thêm paging >>
				int lastPageNum = listPageNum.get(listPageNum.size() - 1);
				if (lastPageNum * userLimit < totalUser) {
					pagingHTMLs.add("<a href='ListUser.do?type=" + ConstantUtil.ADM002_PAGING + "&page="
							+ ConstantUtil.ADM002_PAGE_NEXT + "'>>></a> " + "&nbsp;");
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
			// lưu giá trị combobox[groupId] lên session viết vào đây
			request.getSession().setAttribute("groupHTML", groupHTMLs);
			// gửi các icon
			request.getSession().setAttribute("symbolFullName", firstSortSymbol);
			request.getSession().setAttribute("symbolCodeLevel", secondSortSymbol);
			request.getSession().setAttribute("symbolEndDate", thirdSortSymbol);
			// gửi danh sách user
			request.getSession().setAttribute("userInfors",
					new TblUserLogicImpl().getListUser(CommonUtil.getOffSet(curentPage, userLimit), userLimit,
							(null == groupId) ? 0 : groupId, fullName, sortType, typeByFullName, typeByCodeLevel,
							typeByEndDate));
			// gửi chuỗi paging
			request.getSession().setAttribute("adm002paging", pagingHTMLs);
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
}
