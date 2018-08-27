/**
 * Copyright(C) 2018 	Luvina
 * CommonUtil.java, Aug 16, 2018, LA-PM
 */
package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import properties.ConfigProperties;

/**
 * Các hàm và thuộc tính chung của project
 * 
 * @author LA-PM
 *
 */
public class CommonUtil {
	/**
	 * kiểm tra total có là số nguyên dương ko
	 * 
	 * @param total điểm
	 * @return true là total là số nguyên dương
	 */
	public static boolean checkHalfSizeNumber(int total) {
		return (total + "").matches("[1-9][0-9]*");
	}

	/*
	 * chuyển từ chuỗi sang số
	 * 
	 */
	public static int convertStrToInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			// str không là số
			return 0;
		}
	}

	/**
	 * mã hóa chuỗi pass + salt sang SHA1
	 * 
	 * @param pass mật khẩu
	 * @param salt chuỗi gây nhiễu
	 * @return chuỗi đã mã hóa
	 * @throws NoSuchAlgorithmException
	 */
	public static String encodeMatKhau(String pass, String salt) throws NoSuchAlgorithmException {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest((pass + salt).getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * lấy thứ tự của User đầu tiên sẽ hiển thị trên page
	 * 
	 * @param pageNumber trang sẽ hiển thị UserInfor
	 * @param limit      số user tôi đa sẽ được hiển thị page
	 * @return thứ tự đầu tiên
	 */
	public static int getOffSet(Integer pageNumber, int limit) {
		return (((null == pageNumber) ? 1 : pageNumber) - 1) * limit;
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
			int offsetNumber = (currentPage - 1) / pageLimit * pageLimit;
			// thêm vào list
			while (listPaging.size() < pageLimit && offsetNumber < totalPage) {
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
	 * @throws Exception
	 */
	public static String convertSymbol(String sortSymbol) {
		try {
			return (ConfigProperties.getValue("ADM002_ASCSymbol").equals(sortSymbol)) ? ConstantUtil.ADM002_SX_TANG
					: ConstantUtil.ADM002_SX_GIAM;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * tạo danh sách các năm từ startYear đến năm hiện tại
	 * 
	 * @param startYear giới hạn dưới của năm
	 * @param endYear   giới hạn trên của năm
	 * @return danh sách các năm từ startYear đến endYear
	 */
	public static ArrayList<Integer> getListYears(int startYear, int endYear) throws Exception {
		ArrayList<Integer> listYears = new ArrayList<>();

		while (startYear <= endYear) {
			// startYear chưa lớn hơn endYear

			// thì thêm startYear vào danh sách và tăng startYear lên 1 đơn vị
			listYears.add(startYear++);
		}

		return listYears;
	}

	/**
	 * tạo danh sách các tháng trong năm
	 * 
	 * @return danh sách các tháng
	 */
	public static ArrayList<Integer> getListMonths() {
		ArrayList<Integer> listMonths = new ArrayList<>();

		for (int month = 1; month < 13; month++) {
			// month là các tháng trong năm

			// thì thêm vào danh sách
			listMonths.add(month);
		}

		return listMonths;
	}

	/**
	 * tạo danh sách các ngày trong tháng
	 * 
	 * @return danh sách các ngày
	 */
	public static ArrayList<Integer> getListDates() {
		ArrayList<Integer> listDates = new ArrayList<>();

		for (int date = 1; date < 32; date++) {
			// date là các ngày trong tháng

			// thì thêm vào danh sách
			listDates.add(date);
		}

		return listDates;
	}

	/**
	 * nhận thời gian hiện tại
	 * 
	 * @return chuỗi dạng yyyy-mm-dd
	 */
	public static Date getNowTime() {
		return new Date(System.currentTimeMillis());
	}

	/**
	 * chuyển 3 biến thành 1 chuỗi dạng yyyy-MM-dds
	 * 
	 * @param year  năm
	 * @param month tháng
	 * @param date  ngày
	 * @return kiểu Date trong SQL
	 * @throws Exception
	 */
	public static Date convertToDate(String year, String month, String date) throws ParseException {
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);

			return df.parse(year + "-" + month + "-" + date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * kiểm tra login name có đúng định dạng là chỉ bao gồm a-z, A-Z,0-9, _, ko bắt
	 * đầu bằng số
	 * 
	 * @param loginName tene đăng nhập
	 * @return true là đúng định dạng
	 */
	public static boolean checkAccountFormat(String loginName) {
		if ((loginName.charAt(0) + "").matches("[0-9]")) {
			// ký tự đầu là số
			return false;
		}
		if (!loginName.matches("\b")) {
			// các ký tự ko chỉ bao gồm a-z, A-Z, 0-9, _
			return false;
		}

		return true;
	}

	/**
	 * check các ký tự trong chuỗi có là ký tự kana ko
	 * 
	 * @param fullNameKana chuỗi cần check
	 * @return true là chuỗi là kana
	 */
	public static boolean isKanaString(String fullNameKana) {
		for (int i = 0; i < fullNameKana.length(); i++) {
			int so = (int) fullNameKana.charAt(i);

			if ((int) ConstantUtil.FIRST_KANA > so || so > (int) ConstantUtil.LAST_KANA) {
				// ko là ký tự kana

				// thì
				return false;
			}
		}

		return true;
	}

	/**
	 * kiểm tra các ký tự trong chuỗi có là ký tự 1 byte ko
	 * 
	 * @param pass chuỗi cần check
	 * @return true là chuỗi chỉ bao gồm các ký tự 1 byte
	 */
//	public static boolean checkOneByteString(String pass) {
//		for (int i = 0; i < pass.length(); i++) {
//			int so = (int) pass.charAt(i);
//
//			if ((int) ConstantUtil.FIRST_ONE_BYTE_CHAR > so || so > (int) ConstantUtil.LAST_ONE_BYTE_CHAR) {
//				// ko là ký tự kana
//
//				// thì
//				return false;
//			}
//		}
//
//		return true;
//	}
}
