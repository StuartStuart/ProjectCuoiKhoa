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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo các biến sẽ sử dụng
			String fullName;
			Integer groupId;
			String sortType;
			String sortSymbol;
			Integer currentPage;
			String firstSortSymbol = ConstantUtil.ADM002_ASC;
			String secondSortSymbol = ConstantUtil.ADM002_ASC;
			String thirdSortSymbol = ConstantUtil.ADM002_DESC;
			int userLimit = CommonUtil.getLimit();

			/*
			 * Các loại vào ADM002
			 */
			String type = request.getParameter("type");
			if (ConstantUtil.ADM002_SEARCH.equals(type)) { // nhận full name
				fullName = request.getParameter(ConfigProperties.getValue("ADM002_Textbox"));
				fullName = (null == fullName) ? "" : fullName;
				// lưu session
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_Textbox"), fullName);

				// nhận groupId
				String groupType = request.getParameter((ConfigProperties.getValue("ADM002_GroupId")));
				groupId = Integer.parseInt((null == groupType) ? "0" : groupType);
				// lưu session
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_GroupId"), groupId);

				// đổi lại sortType
				sortType = ConstantUtil.CAC_LOAI_SAP_XEP[0];
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortType"), sortType);

				// đổi lại sortSymbol
				sortSymbol = ConstantUtil.ADM002_ASC;
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortSymbol"), sortSymbol);

				// đổi lại currentPage
				currentPage = 1;
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_CurrentPage"), currentPage);
			} else {
				// get old status textbox[fullName]
				fullName = (String) request.getSession().getAttribute(ConfigProperties.getValue("ADM002_Textbox"));
				// get old status combobox[groupId]
				groupId = (Integer) request.getSession().getAttribute(ConfigProperties.getValue("ADM002_GroupId"));
				// get old status sortType
				sortType = (String) request.getSession().getAttribute(ConfigProperties.getValue("ADM002_SortType"));
				// get old status sortWay
				sortSymbol = (String) request.getSession().getAttribute(ConfigProperties.getValue("ADM002_SortSymbol"));
				// get old status pageNum
				currentPage = (Integer) request.getSession()
						.getAttribute(ConfigProperties.getValue("ADM002_CurrentPage"));

				switch (type) {
				case ConstantUtil.ADM002_PAGING:
					// tính giá trị currentPage
					String page = request.getParameter("page");
					// số paging trên web browser
					int pageLimit = Integer.parseInt(ConfigProperties.getValue("Paging_Limit"));
					switch (page) {
					case ConstantUtil.ADM002_PAGE_BACK:
						// xử lý chuỗi paging
						currentPage = (currentPage - 1) / pageLimit * pageLimit;
						request.getSession().setAttribute(ConfigProperties.getValue("ADM002_CurrentPage"), currentPage);
						break;
					case ConstantUtil.ADM002_PAGE_NEXT:
						// xử lý chuỗi paging
						currentPage = ((currentPage - 1) / pageLimit + 1) * pageLimit + 1;
						request.getSession().setAttribute(ConfigProperties.getValue("ADM002_CurrentPage"), currentPage);
						break;
					default:
						// xác định currentPage
						currentPage = Integer.parseInt(page);
						request.getSession().setAttribute(ConfigProperties.getValue("ADM002_CurrentPage"), currentPage);
						break;
					}
					break;
				case ConstantUtil.ADM002_SORT:
					// xác định kiểu sortSymbol
					if ((int) ConstantUtil.ADM002_ASC.toString().charAt(0) == Integer
							.parseInt(request.getParameter("sort"))) {
						sortSymbol = ConstantUtil.ADM002_DESC;
					} else {
						sortSymbol = ConstantUtil.ADM002_ASC;
					}
					// lưu session
					request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortSymbol"), sortSymbol);

					// xác định priorityType
					switch (request.getParameter("priority")) {
					case ConstantUtil.ADM002_FULL_NAME_SORT:
						// xác định loại sắp xếp được ưu tiên
						sortType = ConstantUtil.CAC_LOAI_SAP_XEP[0];
						// thay đổi dạng icon
						firstSortSymbol = sortSymbol;
						break;
					case ConstantUtil.ADM002_CODE_LEVEL_SORT:
						sortType = ConstantUtil.CAC_LOAI_SAP_XEP[1];
						secondSortSymbol = sortSymbol;
						break;
					case ConstantUtil.ADM002_END_DATE_SORT:
						sortType = ConstantUtil.CAC_LOAI_SAP_XEP[2];
						thirdSortSymbol = sortSymbol;
						break;
					default:
						break;
					}
					// lưu session
					request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortType"), sortType);
					break;
				default:
					break;
				}
			}

			/*
			 * xây dựng htmlGroupId
			 */
			ArrayList<String> groupHTMLs = new ArrayList<>();
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
			request.setAttribute("groupHTML", groupHTMLs); // gửi combobox[groupId]

			/*
			 * gửi symbols
			 */
			// chuyển các symbol về chính xác
			switch (sortType) {
			case ConstantUtil.ADM002_FULL_NAME_SORT:
				firstSortSymbol = sortSymbol;
				break;
			case ConstantUtil.ADM002_CODE_LEVEL_SORT:
				secondSortSymbol = sortSymbol;
				break;
			case ConstantUtil.ADM002_END_DATE_SORT:
				thirdSortSymbol = sortSymbol;
				break;
			default:
				break;
			}
			// gửi symbols
			request.setAttribute("symbolFullName", firstSortSymbol);
			request.setAttribute("symbolCodeLevel", secondSortSymbol);
			request.setAttribute("symbolEndDate", thirdSortSymbol);

			// used for send userInfors List and build htmlPaging
			TblUserLogicImpl tblUserLogic = new TblUserLogicImpl();
			request.setAttribute("userInfors", // send userInfors List
					tblUserLogic.getListUser(CommonUtil.getOffSet(currentPage, userLimit), userLimit, groupId, fullName,
							sortType, CommonUtil.convertSymbol(firstSortSymbol),
							CommonUtil.convertSymbol(secondSortSymbol), CommonUtil.convertSymbol(thirdSortSymbol)));
			/*
			 * build htmlPaging
			 */
			int totalUser = tblUserLogic.getTotalUser(groupId, fullName);
			ArrayList<String> pagingHTMLs = new ArrayList<>();
			ArrayList<Integer> listPageNum = CommonUtil.getListPaging(totalUser, userLimit, currentPage);
			if (0 != listPageNum.size()) {
				// thêm paging <<
				if (1 != listPageNum.get(0)) {
					pagingHTMLs.add("<a href='ListUser.do?type=" + ConstantUtil.ADM002_PAGING + "&page="
							+ ConstantUtil.ADM002_PAGE_BACK + "'><<</a> " + "&nbsp;");
				}
				// thêm paging N
				for (Integer pageNums : listPageNum) {
					pagingHTMLs.add("<a href='ListUser.do?type=" + ConstantUtil.ADM002_PAGING + "&page=" + pageNums
							+ "'>" + pageNums + "</a>" + " &nbsp;");
				}
				// thêm paging >>
				int lastPageNum = listPageNum.get(listPageNum.size() - 1);
				if (lastPageNum * userLimit < totalUser) {
					pagingHTMLs.add("<a href='ListUser.do?type=" + ConstantUtil.ADM002_PAGING + "&page="
							+ ConstantUtil.ADM002_PAGE_NEXT + "'>>></a> " + "&nbsp;");
				}
			}
			request.setAttribute("adm002paging", pagingHTMLs); // gửi htmlPaging

			// request đến ADM002
			request.getRequestDispatcher("jsp/ADM002.jsp").forward(request, response);
		} catch (Exception e) {
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
