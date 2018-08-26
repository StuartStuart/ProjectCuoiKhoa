package controllers;

import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.UserInforEntity;
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;
import utils.CommonUtil;
import utils.ConstantUtil;
import validates.UserValidate;

/**
 * Servlet implementation class AddUserInputController
 */
@WebServlet("/AddUserInput.do")
public class AddUserInputController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// set các thông tin trong combobox lên request
			setDataLogic(request);
			// nhận về 1 userInfor tùy thuộc từng điều kiện
			UserInforEntity userInforDefault = getUserInforDefault(request, response);

			// gửi userInforDefault lên request
			request.setAttribute("adm003userinfor", userInforDefault);

			request.getRequestDispatcher("/jsp/ADM003.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher("/jsp/System_Error.jsp").forward(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
		String loginName;
		int groupId;
		String fullName;
		String fullNameKana;
		Date birthDay;
		String email;
		String tel;
		String pass;
		String repass;
		String codeLevel;
		Date startDate;
		Date endDate;
		int total;
		// nhận về loại đến ADM003 - type
		try {
			String type = request.getParameter("type");
			// từ type, xác định cách set attribute cho userInfof - s-c

			if (null == type) {
				// là trường hợp ấn submit

				// thì nhận về các thông tin đã nhập
				loginName = (String) request.getParameter("id");
				groupId = CommonUtil.convertStrToInt((String) request.getParameter("group_id"));
				fullName = (String) request.getParameter("full_name");
				fullNameKana = (String) request.getParameter("full_name_kana");
				// birth_day trong tbl_user
				String[] arrBirthDay = request.getParameterValues("birth_day");
				birthDay = CommonUtil.convertToDate(arrBirthDay[0], arrBirthDay[1], arrBirthDay[2]);
				email = request.getParameter("email");
				tel = request.getParameter("tel");
				pass = request.getParameter("pass");
				repass = request.getParameter("repass");
				codeLevel = request.getParameter("kyu_id");
				// start_date trong tbl_user
				String[] arrStartDate = request.getParameterValues("start_date");
				startDate = CommonUtil.convertToDate(arrStartDate[0], arrStartDate[1], arrStartDate[2]);
				// end_date trong tbl_user
				String[] arrEndDate = request.getParameterValues("end_date");
				endDate = CommonUtil.convertToDate(arrEndDate[0], arrEndDate[1], arrEndDate[2]);
				total = CommonUtil.convertStrToInt(request.getParameter("total"));
			} else {
				// ko là trường hợp submit
				switch (type) {

				case ConstantUtil.ADM003_BACK_TYPE:
					// thêm các thông tin mặc định cho user từ session
					UserInforEntity sessionUser = (UserInforEntity) request.getSession()
							.getAttribute("adm003entityuserinfor");

					loginName = sessionUser.getLoginName();
					groupId = sessionUser.getGroupId();
					fullName = sessionUser.getFullName();
					fullNameKana = sessionUser.getFullNameKana();
					birthDay = sessionUser.getBirthDay();
					email = sessionUser.getEmail();
					tel = sessionUser.getTel();
					pass = "";
					repass = "";
					codeLevel = sessionUser.getCodeLevel();
					startDate = sessionUser.getStartDate();
					endDate = sessionUser.getEndDate();
					total = sessionUser.getTotal();
					break;
				case ConstantUtil.ADM003_EDIT_TYPE:
					// thêm các thông tin mặc định cho user bằng các thông tin từ db
					// phụ thuộc user_id

					// nhận user_id
					int userId = CommonUtil.convertStrToInt(request.getParameter("userid"));
					UserInforEntity editionUser = new TblUserLogicImpl().getUserInfor(userId);

					loginName = editionUser.getLoginName();
					groupId = editionUser.getGroupId();
					fullName = editionUser.getFullName();
					fullNameKana = editionUser.getFullNameKana();
					birthDay = editionUser.getBirthDay();
					email = editionUser.getEmail();
					tel = editionUser.getTel();
					pass = "";
					repass = "";
					codeLevel = editionUser.getCodeLevel();
					startDate = editionUser.getStartDate();
					endDate = editionUser.getEndDate();
					total = editionUser.getTotal();
					break;
				case ConstantUtil.ADM003_ADD_TYPE:
				default: // cùng trường hợp với add type

					Date nowTime = CommonUtil.getNowTime();
					// thêm các thông tin mặc định cho user, ví dụ startDate là ngày
					// hiện tại
					loginName = "";
					groupId = ConstantUtil.ADM003_DEFAULT_GROUP;
					fullName = "";
					fullNameKana = "";
					birthDay = nowTime;
					email = "";
					tel = "";
					pass = "";
					repass = "";
					codeLevel = "";
					startDate = nowTime;
					endDate = nowTime;
					total = ConstantUtil.ADM003_DEFAULT_TOTAL;
					break;
				}
			}
			// set thuộc tính cho userInforDefault
			userInforDefault.setLoginName(loginName);
			userInforDefault.setGroupId(groupId);
			userInforDefault.setFullName(fullName);
			userInforDefault.setFullNameKana(fullNameKana);
			userInforDefault.setBirthDay(birthDay);
			userInforDefault.setEmail(email);
			userInforDefault.setTel(tel);
			userInforDefault.setPass(pass);
			userInforDefault.setRepass(repass);
			userInforDefault.setCodeLevel(codeLevel);
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
			// cài đặt 7 combobox
			setDataLogic(request);

			// nhận về các thuộc tính trên request
			UserInforEntity userInfor = getUserInforDefault(request, response);

			ArrayList<String> listErrMsg = new UserValidate().validateUser(userInfor);
			if (0 != listErrMsg.size()) {
				// kiểm tra thấy có lỗi

				// thì set mảng lỗi lên request
				request.setAttribute("errmsg", listErrMsg);
				// set userinfor lên request
				request.setAttribute("adm003userinfor", userInfor);
				request.getRequestDispatcher(ConstantUtil.ADM003_JSP).forward(request, response);

			} else {
				// ko có lỗi

				// thì set userinfor lên session và chuyển hướng đến AddUserConfirm.do
				request.getSession().setAttribute("adm003entityuserinfor", userInfor);
				response.sendRedirect(request.getContextPath() + ConstantUtil.ADD_USER_CONFIRM);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// chuyển đến màn hình Error
			try {
				request.setAttribute("systemerrormessage", MessageErrorProperties.getValue("Error015"));
				request.getRequestDispatcher("/jsp/System_Error.jsp").forward(request, response);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

}
