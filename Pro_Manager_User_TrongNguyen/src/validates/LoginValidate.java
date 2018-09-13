/**
 * Copyright(C) 2018 	Luvina
 * LoginValidate.java, Aug 14, 2018, LA-PM
 */
package validates;

import java.util.ArrayList;

import logics.impl.TblUserLogicImpl;
import properties.MessageErrorProperties;

/**
 * 
 * Xử lý logic cho ADM001
 * 
 * @author LA-PM
 *
 */
public class LoginValidate {
	private TblUserLogicImpl tblUserLogicImpl;

	/**
	 * @param tblUserLogicImpl
	 */
	public LoginValidate() {
		this.tblUserLogicImpl = new TblUserLogicImpl();
	}

	/**
	 * kiểm tra username rỗng, sai tài khoản
	 * 
	 * @param loginId tên đăng nhập
	 * @param pass     mật khẩu
	 * @return thông báo lỗi dẫn đến không đăng nhập được
	 * @throws Exception
	 */
	public ArrayList<String> validate(String loginId, String pass) throws Exception {
		ArrayList<String> listMessage = new ArrayList<>();

		try {
			if (loginId.isEmpty()) { // username ko được nhập
				listMessage.add(MessageErrorProperties.getValue("Error001_UserName"));
			}
			if (pass.isEmpty()) { // username ko được nhập
				listMessage.add(MessageErrorProperties.getValue("Error001_Password"));
			}
			if (0 == listMessage.size() && !tblUserLogicImpl.checkExistUser(loginId, pass)) { // tài khoản không tồn tại
				listMessage.add(MessageErrorProperties.getValue("Error016"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return listMessage;
	}
}
