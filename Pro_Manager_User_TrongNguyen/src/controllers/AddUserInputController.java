package controllers;

import java.io.IOException;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
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
			setDataLogic(request, response);
			// xác định type để xử lý getDefaultLogic
			

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
	 * set các giá trị mặc định cho group, trình độ tiếng Nhật, năm tháng ngày
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private void setDataLogic(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			// gửi giá trị listMstGroup
			request.setAttribute("groups", new MstGroupLogicImpl().getAllMstGroup());
			// gửi giá trị listMstJapan
			request.setAttribute("japanlevels", new MstJapanLogicImpl().getAllMstJapan());
			// thêm thời gian vào các combobox
			// gửi giá trị listBirthYears
			request.setAttribute("birthyears", CommonUtil.getListYears(ConstantUtil.ADM003_START_YEAR));
			// gửi giá trị listStartYears
			request.setAttribute("startyears", CommonUtil.getListYears(ConstantUtil.ADM003_START_YEAR));
			// gửi giá trị listEndYears
			request.setAttribute("endyears", CommonUtil.getListYears(ConstantUtil.ADM003_START_YEAR));
			// gửi giá trị listMonths
			request.setAttribute("months", CommonUtil.getListMonths());
			// gửi giá trị listDays
			request.setAttribute("dates", CommonUtil.getListMonths());
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
