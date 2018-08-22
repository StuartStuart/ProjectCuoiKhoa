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
@WebServlet(description = "ListUserController", urlPatterns = "/ListUser.do")
public class ListUserController extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			// Khởi tạo các biến sẽ sử dụng
			String fullName;
			Integer groupId;
			String sortType;
			String sortWay;
			Integer currentPage;
			int userLimit = CommonUtil.getLimit();
			int amountSortType = ConstantUtil.DEFAULT_WAYS.length;
			String[] displaySX = { "ASC", "ASC", "DESC" };
			/*
			 * Các loại vào ADM002
			 */
			String type = request.getParameter("type");
			if (null == type || ConstantUtil.ADM002_SEARCH.equals(type)) {
				fullName = request.getParameter("adm002fullname");
				fullName = (null == fullName) ? "" : fullName;
				// lưu session
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_Textbox"), fullName);

				// nhận groupId
				String groupType = request.getParameter("adm002groupid");
				groupId = Integer.parseInt((null == groupType) ? "0" : groupType);
				// lưu session
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_GroupId"), groupId);

				// đổi lại sortType
				sortType = BaseDaoImpl.WHITE_LIST[0];
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortType"), sortType);

				// đổi lại sortSymbol
				sortWay = ConstantUtil.DEFAULT_WAYS[0];
				request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortSymbol"), sortWay);

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
				sortWay = (String) request.getSession().getAttribute(ConfigProperties.getValue("ADM002_SortSymbol"));
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
					if ("ASC".equals(sortWay)) {
						sortWay = "DESC";
					} else {
						sortWay = "ASC";
					}
					// lưu session
					request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortSymbol"), sortWay);

					// xác định priorityType
					sortType = request.getParameter("priority");
					// lưu session
					request.getSession().setAttribute(ConfigProperties.getValue("ADM002_SortType"), sortType);
					break;
				default:
					break;
				}
			}

			/*
			 * xây dựng groupId
			 */
			// tạo danh sách group mặc định
			ArrayList<TblMstGroupEntity> optionGroup = new ArrayList<>();
			TblMstGroupEntity totalGroup = new TblMstGroupEntity();
			totalGroup.setGroupId(0);
			totalGroup.setGroupName("全て");
			// thêm các group còn lại
			optionGroup.add(totalGroup);
			optionGroup.addAll(new MstGroupLogicImpl().getAllMstGroup());
			// gửi session
			request.setAttribute("adm002groupid", optionGroup);

			/*
			 * chuyển các symbol về chính xác
			 */
			for (int i = 0; i < amountSortType; i++) {
				if (BaseDaoImpl.WHITE_LIST[i].equals(sortType)) {
					displaySX[i] = sortWay;
					break;
				}
			}
			// gửi symbols
			request.setAttribute("wayFullName", displaySX[0]);
			request.setAttribute("wayCodeLevel", displaySX[1]);
			request.setAttribute("wayEndDate", displaySX[2]);

			// used for send userInfors List and build paging
			TblUserLogic tblUserLogic = new TblUserLogicImpl();
			request.setAttribute("userInfors", // send userInfors List
					tblUserLogic.getListUser(CommonUtil.getOffSet(currentPage, userLimit), userLimit, groupId, fullName,
							sortType, displaySX[0], displaySX[1], displaySX[2]));
			/*
			 * build paging
			 */
			int totalUser = tblUserLogic.getTotalUser(groupId, fullName);
			request.setAttribute("adm002paging", CommonUtil.getListPaging(totalUser, userLimit, currentPage));
			request.setAttribute("totalPage", CommonUtil.getTotalPage(totalUser, userLimit));
			request.setAttribute("limitPage", ConfigProperties.getValue("Paging_Limit"));

			// request đến ADM002
			request.getRequestDispatcher("jsp/ADM002.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			response.sendRedirect("jsp/System_Error.jsp");
		}
	}
}
