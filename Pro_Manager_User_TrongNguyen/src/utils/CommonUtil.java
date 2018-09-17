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
import java.util.Calendar;
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
	 * chuyển yyyy-MM-dd về dạng yyyy/MM/dd
	 * 
	 * @param date ngày cần chuyển
	 * @return chuỗi ngày đúng format
	 * @throws ParseException
	 */
	public static String showFormatDate(String date) throws ParseException {
		try {
			if (date.isEmpty()) {
				return "";
			} else {
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

				Date day = df.parse(date.replaceAll("-", "/"));
				return df.format(day);
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * kiểm tra total có là số nguyên dương ko
	 * 
	 * @param total điểm
	 * @return true là total là số nguyên dương
	 */
	public static boolean checkHalfSizeNumber(String total) {
		return (total + "").matches("[1-9][0-9]*");
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
			// xác định kiểu encode là SHA1
			mDigest = MessageDigest.getInstance("SHA1");
			/*
			 * endcode
			 */
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
	public static int getUserLimit() throws Exception {
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
		int totalPage;
		if (0 == totalUser) {
			totalPage = 0;
		} else {
			int userLimit = limit; // Integer.parseInt(ConfigProperties.getValue("User_Limit"));
			totalPage = totalUser / userLimit;
			if (totalPage * userLimit != totalUser) {
				totalPage++;
			}
		}

		return totalPage;
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
	 * @param plusOne năm hiện tại cộng thêm 1
	 * @return chuỗi dạng yyyy-mm-dd
	 */
	public static String getNowTime(boolean plusOne) {
		int year = Calendar.getInstance().get(Calendar.YEAR);
		year = (plusOne) ? year + 1 : year;
		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
		int date = Calendar.getInstance().get(Calendar.DATE);
		return year + "-" + month + "-" + date;
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
	public static String convertToDateString(String year, String month, String date) throws ParseException {
		return year + "-" + month + "-" + date;
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
	 * nhận total từ textbox
	 * 
	 * @param str chuỗi textbox
	 * @return null là chuỗi ko phải số
	 */
	public static Integer convertStrToInteger(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * kiểm tra các ký tự trong chuỗi có là ký tự 1 byte ko
	 * 
	 * @param pass chuỗi cần check
	 * @return true là chuỗi chỉ bao gồm các ký tự 1 byte
	 */
	public static boolean checkOneByteString(String pass) {
		for (int i = 0; i < pass.length(); i++) {
			int so = (int) pass.charAt(i);

			if ((int) ConstantUtil.FIRST_ONE_BYTE_CHAR > so || so > (int) ConstantUtil.LAST_ONE_BYTE_CHAR) {
				// ko là ký tự kana

				// thì
				return false;
			}
		}

		return true;
	}

	/**
	 * kiểm tra sự hợp lệ của date
	 * 
	 * @param day date cần check
	 * @return true là hợp lệ
	 */
	public static boolean checkDate(String dateToValidate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(dateToValidate);
			// validate thành công
			return true;
		} catch (ParseException e) {
			e.printStackTrace();
			// validate ko thành công
			return false;
		}
	}

	/**
	 * nhận về salt lấy từ milisecond
	 * 
	 * @return salt
	 */
	public static String getSalt() {
		return new Date().getTime() + "";
	}

	/**
	 * chuyển chuỗi date thành mảng gồm năm, tháng, ngày
	 * 
	 * @param date chuỗi cần chuyển
	 * @return mảng 3 phần tử là năm thành ngày
	 */
	public static String[] convertToArrDateString(String date) {
		return date.split("-");
	}
}
