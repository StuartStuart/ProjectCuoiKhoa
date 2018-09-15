package controllers;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.MstJapanEntity;
import entities.TblMstGroupEntity;
import entities.UserInforEntity;
import logics.TblUserLogic;
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import utils.CommonUtil;
import utils.ConstantUtil;
import validates.UserValidate;

/**
 * Servlet implementation class AddUserInputController
 */
@WebServlet(urlPatterns = { ConstantUtil.ADD_USER_INPUT_CONTROLLER, ConstantUtil.ADD_USER_VALIDATE_CONTROLLER,
		ConstantUtil.EDIT_USER_INPUT_CONTROLLER })
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if (ConstantUtil.ADD_USER_VALIDATE_CONTROLLER.equals(request.getServletPath())) {
				response.sendRedirect(request.getContextPath() + ConstantUtil.ADD_USER_INPUT_CONTROLLER + "?type="
						+ ConstantUtil.ADM003_ADD_TYPE);
			} else {
				// nhận về 1 userInfor tùy thuộc từng điều kiện
				UserInforEntity userInforDefault = getUserInforDefault(request, response);

				// set các thông tin trong combobox lên request
				setDataLogic(request);

				// gửi userInforDefault lên request
				request.setAttribute("adm003userinfor", userInforDefault);

				request.getRequestDispatcher(ConstantUtil.ADM003_JSP).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			request.getSession().setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}

	/**
	 * set các thuộc tính cho userInforDefault có thông tin tùy thuộc cách truy cập
	 * màn hình ADM003
	 * 
	 * @param request
	 * @param response
	 * @return 1 đối tượng UserInforEntity
	 * @throws Exception
	 */
	private UserInforEntity getUserInforDefault(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserInforEntity userInforDefault = new UserInforEntity();
		// khai báo các thuộc tính để set cho userinfor
		Integer userId = null;
		String loginId;
		int groupId;
		TblMstGroupEntity mstGroup;
		String fullName;
		String fullNameKana;
		String birthDay;
		int category;
		String email;
		String tel;
		String pass;
		String repass;
		String codeLevel;
		MstJapanEntity mstJapan;
		String startDate;
		String endDate;
		String total;
		// nhận về loại đến ADM003 - type
		try {
			String type = request.getParameter("type");
			// từ type, xác định cách set attribute cho userInfof - s-c

			if (null == type) {
				// là trường hợp ấn submit
				type = ConstantUtil.ADM003_SUBMIT_TYPE;
			}

			// ko là trường hợp submit
			TblUserLogic userLogic = new TblUserLogicImpl();
			switch (type) {
			case ConstantUtil.ADM003_SUBMIT_TYPE:
				// thì nhận về các thông tin đã nhập
				loginId = (String) request.getParameter("id");
				if (null == loginId) {
					// là trường hợp submit của update thì
					loginId = request.getParameter("loginName");
					userId = userLogic.getUserIdByLoginName(loginId);
				}
				groupId = CommonUtil.convertStrToInteger(request.getParameter("group_id"));
				mstGroup = new MstGroupLogicImpl().getMstGroupByGroupId(groupId);
				fullName = request.getParameter("full_name");
				fullNameKana = request.getParameter("full_name_kana");
				category = CommonUtil.convertStrToInteger(request.getParameter("category"));
				// birth_day trong tbl_user
				String[] arrBirthDay = request.getParameterValues("birth_day");
				birthDay = CommonUtil.convertToDateString(arrBirthDay[0], arrBirthDay[1], arrBirthDay[2]);
				email = request.getParameter("email");
				tel = request.getParameter("tel");
				pass = request.getParameter("pass");
				repass = request.getParameter("repass");
				codeLevel = request.getParameter("kyu_id");
				mstJapan = new MstJapanLogicImpl().getMstJapanByCodeLevel(codeLevel);
				// start_date trong tbl_user
				String[] arrStartDate = request.getParameterValues("start_date");
				startDate = CommonUtil.convertToDateString(arrStartDate[0], arrStartDate[1], arrStartDate[2]);
				// end_date trong tbl_user
				String[] arrEndDate = request.getParameterValues("end_date");
				endDate = CommonUtil.convertToDateString(arrEndDate[0], arrEndDate[1], arrEndDate[2]);
				total = request.getParameter("total");
				break;
			case ConstantUtil.ADM003_BACK_TYPE:
				// thêm các thông tin mặc định cho user từ session
				HttpSession session = request.getSession();
				String key = request.getParameter("key");
				UserInforEntity sessionUser = (UserInforEntity) session.getAttribute("entityuserinfor" + key);
				// xóa obj
				session.removeAttribute("entityuserinfor" + key);

				userId = sessionUser.getUserId();
				loginId = sessionUser.getLoginName();
				groupId = sessionUser.getGroupId();
				mstGroup = sessionUser.getMstGroup();
				fullName = sessionUser.getFullName();
				fullNameKana = sessionUser.getFullNameKana();
				category = sessionUser.getCategory();
				birthDay = sessionUser.getBirthDay();
				email = sessionUser.getEmail();
				tel = sessionUser.getTel();
				pass = "";
				repass = "";
				codeLevel = sessionUser.getCodeLevel();
				mstJapan = sessionUser.getMstJapan();
				startDate = sessionUser.getStartDate();
				endDate = sessionUser.getEndDate();
				total = sessionUser.getTotal();
				break;
			case ConstantUtil.ADM003_EDIT_TYPE:
				// thêm các thông tin mặc định cho user bằng các thông tin
				// từ db
				// phụ thuộc user_id

				// lấy về userId dạng Integer
				userId = CommonUtil.convertStrToInteger(request.getParameter("userid"));

				UserInforEntity editionUser;
				if (null != userId && userLogic.checkExistedUserId(userId)) {
					// lấy về UserInforEntity
					editionUser = userLogic.getUserInfor(userId);
				} else {
					editionUser = null;
				}

				loginId = editionUser.getLoginName();
				groupId = editionUser.getGroupId();
				mstGroup = editionUser.getMstGroup();
				fullName = editionUser.getFullName();
				fullNameKana = editionUser.getFullNameKana();
				category = editionUser.getCategory();
				birthDay = editionUser.getBirthDay();
				email = editionUser.getEmail();
				tel = editionUser.getTel();
				pass = "";
				repass = "";
				mstJapan = editionUser.getMstJapan();
				codeLevel = editionUser.getCodeLevel();
				if (null == codeLevel) {
					codeLevel = ConstantUtil.DEFAULT_CODE_LEVEL;
					startDate = CommonUtil.getNowTime(false);
					endDate = CommonUtil.getNowTime(true);
				} else {
					startDate = editionUser.getStartDate();
					endDate = editionUser.getEndDate();
				}
				total = editionUser.getTotal();
				break;
			case ConstantUtil.ADM003_ADD_TYPE:
			default: // cùng trường hợp với add type
				// thêm các thông tin mặc định cho user, ví dụ startDate là
				// ngày
				// hiện tại
				userId = null;
				loginId = "";
				groupId = ConstantUtil.ADM003_DEFAULT_GROUP;
				mstGroup = null;
				fullName = "";
				fullNameKana = "";
				category = 1;
				birthDay = CommonUtil.getNowTime(false);
				email = "";
				tel = "";
				pass = "";
				repass = "";
				codeLevel = ConstantUtil.DEFAULT_CODE_LEVEL;
				mstJapan = null;
				startDate = CommonUtil.getNowTime(false);
				endDate = CommonUtil.getNowTime(true);
				total = null;
				break;
			}
			// set thuộc tính cho userInforDefault
			userInforDefault.setUserId(userId);
			userInforDefault.setLoginName(loginId);
			userInforDefault.setGroupId(groupId);
			userInforDefault.setMstGroup(mstGroup);
			userInforDefault.setFullName(fullName);
			userInforDefault.setFullNameKana(fullNameKana);
			userInforDefault.setCategory(category);
			userInforDefault.setBirthDay(birthDay);
			userInforDefault.setEmail(email);
			userInforDefault.setTel(tel);
			userInforDefault.setPass(pass);
			userInforDefault.setRepass(repass);
			userInforDefault.setCodeLevel(codeLevel);
			userInforDefault.setMstJapan(mstJapan);
			userInforDefault.setStartDate(startDate);
			userInforDefault.setEndDate(endDate);
			userInforDefault.setTotal(total);

			return userInforDefault;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * set các giá trị mặc định cho group, trình độ tiếng Nhật, năm tháng ngày
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDataLogic(HttpServletRequest request) throws Exception {
		try {
			// gửi giá trị listMstGroup
			request.setAttribute("groups", new MstGroupLogicImpl().getAllMstGroup());
			// gửi giá trị listMstJapan
			request.setAttribute("japanlevels", new MstJapanLogicImpl().getAllMstJapan());
			// thêm thời gian vào các combobox
			// gửi giá trị listBirthDayYears
			int endBirthDayYear = Integer.parseInt(Year.now().toString());
			request.setAttribute("birthyears",
					CommonUtil.getListYears(ConstantUtil.ADM003_START_YEAR, endBirthDayYear));
			// gửi giá trị listStartDateYears
			int endStartDateYear = endBirthDayYear + ConstantUtil.ADM003_YEAR_ADDITION_VALUE;
			request.setAttribute("startyears",
					CommonUtil.getListYears(ConstantUtil.ADM003_START_YEAR, endStartDateYear));
			// gửi giá trị listEndDateYears
			int endEndDateYear = endBirthDayYear + ConstantUtil.ADM003_YEAR_ADDITION_VALUE;
			request.setAttribute("endyears", CommonUtil.getListYears(ConstantUtil.ADM003_START_YEAR, endEndDateYear));
			// gửi giá trị listMonths
			request.setAttribute("months", CommonUtil.getListMonths());
			// gửi giá trị listDays
			request.setAttribute("dates", CommonUtil.getListDates());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// nhận về các thuộc tính trên request
			UserInforEntity userInfor = getUserInforDefault(request, response);

			ArrayList<String> listErrMsg = new UserValidate().validateUser(userInfor);
			if (0 != listErrMsg.size()) {
				// kiểm tra thấy có lỗi

				// cài đặt 7 combobox
				setDataLogic(request);
				// thì set mảng lỗi lên request
				request.setAttribute("errmsg", listErrMsg);
				// set userinfor lên request
				request.setAttribute("adm003userinfor", userInfor);
				// set styleJapanZone lên req
				String style = request.getParameter("styleJapanZone");
				request.setAttribute("adm003style", style);
				request.getRequestDispatcher(ConstantUtil.ADM003_JSP).forward(request, response);

			} else {
				/*
				 * ko có lỗi thì set userinfor lên session và chuyển hướng đến AddUserConfirm.do
				 */

				// tạo key
				String key = CommonUtil.getSalt();
				request.getSession().setAttribute("entityuserinfor" + key, userInfor);
				response.sendRedirect(request.getContextPath() + ConstantUtil.ADD_USER_CONFIRM_CONTROLLER + "?key=" + key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			request.getSession().setAttribute(ConstantUtil.SYSTEM_ERROR_TYPE, ConstantUtil.SYSTEM_ERROR_TYPE);
			response.sendRedirect(request.getContextPath() + ConstantUtil.SYSTEM_ERROR_CONTROLLER);
		}
	}

}
