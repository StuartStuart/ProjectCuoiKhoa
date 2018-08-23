package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import properties.MessageErrorProperties;

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
			setDataLogic(request, response);
//			setDefaultValue(request,response);
			request.getRequestDispatcher("/jsp/ADM003.jsp");
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
		// setDefaultValue(request, response);

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
			// gửi giá trị cho
			request.setAttribute("adm003groups", new MstGroupLogicImpl().getAllMstGroup());
			request.setAttribute("adm003japanlevels", new MstJapanLogicImpl().getAllMstJapan());
			
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
