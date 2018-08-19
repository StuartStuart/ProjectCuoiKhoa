/**
 * Copyright(C) 2018 	Luvina
 * CommonUtil.java, Aug 16, 2018, LA-PM
 */
package utils;

import java.util.ArrayList;

import properties.ConfigProperties;

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
	public static int getOffSet(Integer pageNumber, int limit) {
		return ((null == pageNumber) ? 1 : pageNumber - 1) * limit;
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

	/**
	 * lấy danh sách số thứ tự của các trang để được hiển thị trên web browser
	 * 
	 * @param totalUser   tổng số user tìm được
	 * @param limit       số lượng tối đa user trên 1 web browser
	 * @param currentPage trang hiện thời
	 * @return danh sách có nhiều nhất limit trang
	 * @throws Exception
	 */
	public static ArrayList<Integer> getListPaging(int totalUser, int limit, int currentPage) throws Exception {
		ArrayList<Integer> listPaging = new ArrayList<>();

		try {
			// tính số trang có thể hiển thị
			int totalPage = getTotalPage(totalUser, limit);
			// số paging trên web browser
			int pageLimit = Integer.parseInt(ConfigProperties.getValue("Paging_Limit"));
			// thứ tự bắt đầu thêm vào list dựa vào currentPage
			int offsetNumber = currentPage / pageLimit * pageLimit;
			// thêm vào list
			while (listPaging.size() < pageLimit && offsetNumber < totalPage) { // còn nhỏ hơn số trang tối đa
				// browser
				listPaging.add(++offsetNumber);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			throw e;
		}

		return listPaging;
	}

	/**
	 * xác định số lượng user info hiển thị trên web browser
	 * 
	 * @return số lượng lớn nhất
	 * @throws Exception
	 */
	public static int getLimit() throws Exception {
		// xác định số trang có thể hiển thị
		return Integer.parseInt(ConfigProperties.getValue("User_Limit"));
	}

	/**
	 * tính tổng số trang sẽ được hiển thị
	 * 
	 * @param totalUser tổng số user
	 * @param limit     giới hạn user trên 1 web browser
	 * @return
	 */
	public static int getTotalPage(int totalUser, int limit) {
		// xác định số trang có thể hiển thị
		int userLimit = limit; // Integer.parseInt(ConfigProperties.getValue("User_Limit"));

		int totalPage = totalUser / userLimit;
		if (totalPage * userLimit != totalUser) {
			totalPage++;
		}

		return totalPage;
	}

	/**
	 * chuyển kiểu sắp xếp từ icon sang dạng db
	 * 
	 * @param firstSortSymbol biểu tượng cần chuyển
	 * @return ASC hoặc DESC
	 */
	public static String convertSymbol(String sortSymbol) {
		return (ConstantUtil.ADM002_ASC.equals(sortSymbol)) ? ConstantUtil.ADM002_TANG
				: ConstantUtil.ADM002_GIAM;
	}
}
