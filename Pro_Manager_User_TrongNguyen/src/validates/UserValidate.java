/**
 * Copyright(C) 2018	Luvina
 * UserValidate.java, Aug 25, 2018, A
 */
package validates;

import java.util.ArrayList;

import entities.UserInforEntity;

/**
 * @author TrongNguyen
 *
 */
public class UserValidate {

	/**
	 * @param userInfor
	 * @return
	 */
	public ArrayList<String> validateUser(UserInforEntity userInfor) {
		ArrayList<String> listErrMsg = new ArrayList<>();
		System.out.println("Đã chạy qua đây");
		listErrMsg.add("Hello Ann!");
		return listErrMsg;
	}

}
