/**
 * Copyright(C) 2018 	Luvina
 * CommonUtil.java, Aug 16, 2018, LA-PM
 */
package utils;

/**
 * Các hàm và thuộc tính chung của project
 * 
 * @author LA-PM
 *
 */
public class CommonUtil {
	/**
	 * lấy thứ tự của User đầu tiên sẽ hiển thị trên page
	 * 
	 * @param pageNumber trang sẽ hiển thị UserInfor
	 * @param limit      số user tôi đa sẽ được hiển thị page
	 * @return thứ tự đầu tiên
	 */
	public static int getOffSet(int pageNumber, int limit) {
		return (pageNumber - 1) * limit;
	}

	/**
	 * tránh lỗi wild card trong sql
	 * 
	 * @param text chuỗi cần chuyển wild card
	 * @return chuỗi đã được chuyển
	 */
	public static String convertWildCard(String text) {
		text = text.replace("%", "\\%");
		text = text.replace("_", "\\_");

		return text;
	}
}
