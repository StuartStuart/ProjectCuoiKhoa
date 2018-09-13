package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.BaseDaoImpl;
import entities.TblMstGroupEntity;
import logics.TblUserLogic;
import logics.impl.MstGroupLogicImpl;
import logics.impl.TblUserLogicImpl;
import properties.ConfigProperties;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Servlet implementation class ListUserController
 */
@SuppressWarnings("serial")
@WebServlet(description = "ListUserController", urlPatterns = ConstantUtil.LIST_USER_CONTROLLER)
public class ListUserController extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// Khởi tạo các biến sẽ sử dụng
			String fullName = (String) request.getSession().getAttribute("adm002tbfullname");
			Integer groupId = (Integer) request.getSession().getAttribute("adm002cbbgroupid");
			String sortType = (String) request.getSession().getAttribute("adm002sortType");
			String sortWay = (String) request.getSession().getAttribute("adm002sortsymbol");
			Integer currentPage = (Integer) request.getSession().getAttribute("adm002currentpage");
			int userLimit = CommonUtil.getLimit();
			int amountSortType = ConstantUtil.DEFAULT_WAYS.length;
			String[] displaySX = { ConstantUtil.ADM002_SX_TANG, ConstantUtil.ADM002_SX_TANG, ConstantUtil.ADM002_SX_GIAM };
			/*
			 * Các loại vào ADM002
			 */
			String type = request.getParameter("type");
			if (null == type) {
				// trường hợp ấn submit
				type = ConstantUtil.ADM002_SEARCH;
			}

			switch (type) {
			case ConstantUtil.ADM002_SEARCH:
				fullName = request.getParameter("adm002fullname");
				fullName = (null == fullName) ? "" : fullName;
				// lưu session
				request.getSession().setAttribute("adm002tbfullname", fullName);

				// nhận groupId
				String groupType = request.getParameter("adm002groupid");
				groupId = CommonUtil.convertStrToInteger(groupType);
				// lưu session
				request.getSession().setAttribute("adm002cbbgroupid", groupId);

				// đổi lại sortType
				sortType = BaseDaoImpl.WHITE_LIST[0];
				request.getSession().setAttribute("adm002sortType", sortType);

				// đổi lại sortSymbol
				sortWay = ConstantUtil.DEFAULT_WAYS[0];
				request.getSession().setAttribute("adm002sortsymbol", sortWay);

				// đổi lại currentPage
				currentPage = 1;
				request.getSession().setAttribute("adm002currentpage", currentPage);
				break;
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
					currentPage = CommonUtil.convertStrToInteger(page);
					currentPage = (currentPage < 1) ? 1 : currentPage;
					request.getSession().setAttribute(ConfigProperties.getValue("ADM002_CurrentPage"), currentPage);
					break;
				}
				break;
			case ConstantUtil.ADM002_SORT:
				// xác định priorityType
				sortType = request.getParameter("priority");
				// lưu session
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortType"), sortType);
				// get old status sortWay
				sortWay = (String) request.getParameter("sort");
				if (ConstantUtil.ADM002_SX_TANG.equals(sortWay)) {
					sortWay = ConstantUtil.ADM002_SX_GIAM;
				} else if (ConstantUtil.ADM002_SX_GIAM.equals(sortWay)) {
					sortWay = ConstantUtil.ADM002_SX_TANG;
				} else {
					if (BaseDaoImpl.WHITE_LIST[2].equals(sortType)) {
						sortWay = ConstantUtil.ADM002_SX_GIAM;
					} else {
						sortWay = ConstantUtil.ADM002_SX_TANG;
					}
				}
				// lưu session
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortSymbol"), sortWay);
				break;
			default:
				break;
			}

			/*
			 * xây dựng groupId
			 */
			// tạo danh sách group mặc định
			ArrayList<TblMstGroupEntity> optionGroup = new MstGroupLogicImpl().getAllMstGroup();
			// gửi session
			request.setAttribute("adm002groupid", optionGroup);

			/*
			 * chuyển các symbol về chính xác
			 */
			int index = 0;
			while (index < amountSortType) {
				if (BaseDaoImpl.WHITE_LIST[index].equals(sortType)) {
					displaySX[index] = sortWay;
					break;
				}
				index++;
			}
			// gửi symbols
			index = 0;
			request.setAttribute("wayFullName", displaySX[index++]);
			request.setAttribute("wayCodeLevel", displaySX[index++]);
			request.setAttribute("wayEndDate", displaySX[index++]);

			// used for send userInfors
			index = 0;
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			request.setAttribute("userInfors", // send userInfors List
					tblUserLogic.getListUser(CommonUtil.getOffSet(currentPage, userLimit), userLimit, groupId, fullName,
							sortType, displaySX[index++], displaySX[index++], displaySX[index++]));
			/*
			 * build paging
			 */
			int totalUser = tblUserLogic.getTotalUser(groupId, fullName);
			request.setAttribute("adm002paging", CommonUtil.getListPaging(totalUser, userLimit, currentPage));
			request.setAttribute("totalPage", CommonUtil.getTotalPage(totalUser, userLimit));
			request.setAttribute("limitPage", ConfigProperties.getValue("Paging_Limit"));
			// request đến ADM002
			request.getRequestDispatcher(ConstantUtil.ADM002_JSP).forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}
}
