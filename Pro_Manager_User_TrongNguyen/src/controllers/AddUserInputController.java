package controllers;

import java.io.IOException;
import java.time.Year;

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
	 * set các thuộc tính cho userInforDefault có thông tin tùy thuộc cách truy
	 * cập màn hình ADM003
	 * 
	 * @param request
	 * @param response
	 * @return 1 đối tượng UserInforEntity
	 * @throws Exception 
	 */
	private UserInforEntity getUserInforDefault(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// nhận về loại đến ADM003 - type
		try {
			String type = request.getParameter("type");
			// từ type, xác định cách set attribute cho userInfof - s-c
			switch (type) {

			case ConstantUtil.ADM003_BACK_TYPE:
				// thêm các thông tin mặc định cho user từ session
				return (UserInforEntity) request.getSession().getAttribute("adm003entityuserinfor");
			case ConstantUtil.ADM003_EDIT_TYPE:
				// thêm các thông tin mặc định cho user bằng các thông tin từ db
				// phụ
				// thuộc user_id

				// nhận user_id
				int userId = (Integer) request.getAttribute("userid");
				return new TblUserLogicImpl().getUserInfor(userId);
			case ConstantUtil.ADM003_ADD_TYPE:
			default: // cùng trường hợp với add type
				UserInforEntity userInforDefault = new UserInforEntity();
				String nowTime = CommonUtil.getNowTime();
				// thêm các thông tin mặc định cho user, ví dụ startDate là ngày
				// hiện tại
				// set thuộc tính cho userInforDefault
				userInforDefault.setLoginName("");
				userInforDefault.setGroupId(0);
				userInforDefault.setFullName("");
				userInforDefault.setFullNameKana("");
				userInforDefault.setBirthDay(nowTime);
				userInforDefault.setEmail("");
				userInforDefault.setTel("");
				userInforDefault.setCodeLevel("");
				userInforDefault.setStartDate(nowTime);
				userInforDefault.setEndDate(nowTime);
				userInforDefault.setTotal(0);
				return userInforDefault;
			}
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
