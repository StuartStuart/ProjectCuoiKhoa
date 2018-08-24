/**
 * Copyright(C) 2018 	Luvina
 * ConstantUtil.java, Aug 15, 2018, LA-PM
 */
package utils;

/**
 * @author LA-PM
 *
 */
public class ConstantUtil {
	/*
	 * file chứa các cặp key - value liên quan đến database
	 */
	public static final String DATABASE = "Database.properties";
	/*
	 * file chứa các cặp key - value liên quan đến Message Error
	 */
	public static final String MESSAGE_ERROR = "MessageError.properties";
	/*
	 * file chứa các cặp key - value liên quan đến Message Error
	 */
	public static final String CONFIG = "Config.properties";
	/*
	 * user có category = 1
	 */
	public static final int USER_CATEGORY = 1;
	/*
	 * sắp xếp tăng
	 */
	public static final String ADM002_SX_TANG = "ASC";
	/*
	 * sắp xếp giảm
	 */
	public static final String ADM002_SX_GIAM = "DESC";
	/*
	 * tìm kiếm trong ADM002
	 */
	public static final String ADM002_SEARCH = "search";
	/*
	 * sắp xếp tại ADM002
	 */
	public static final String ADM002_SORT = "sort";
	/*
	 * các kiểu sắp xếp
	 */
	public static final String ADM002_SORT_TYPE_URL[] = { "fullname", "codelevel", "enddate" };
	/*
	 * danh sách sắp xếp default
	 */
	public static final String DEFAULT_WAYS[] = { "ASC", "ASC", "DESC" };
	/*
	 * kiểu sắp xếp mặc đinh theo thứ tự ưu tiên
	 */
	public static final String ADM002_DEFAULT_SORT_WAY_ORDERS[] = { ADM002_SX_TANG, ADM002_SX_TANG, ADM002_SX_GIAM };
	/*
	 * paging tại ADM002
	 */
	public static final String ADM002_PAGING = "paging";
	/*
	 * chỉ định cho <<
	 */
	public static final String ADM002_PAGE_BACK = "pageback";
	/*
	 * chỉ định cho >>
	 */
	public static final String ADM002_PAGE_NEXT = "pagenext";
	/*
	 * giới hạn dưới của năm
	 */
	public static final int ADM003_START_YEAR = 1980;
	/*
	 * truy cập đến ADM003 từ ADM002
	 */
	public static final String ADM003_ADD_TYPE = "add";
	/*
	 * truy cập đến ADM003 từ ADM004
	 */
	public static final String ADM003_BACK_TYPE = "back";
	/*
	 * truy cập đến ADM003 từ ADM005
	 */
	public static final String ADM003_EDIT_TYPE = "edit";
	/*
	 * giói hạn trên cộng thêm của năm, ví dụ là cho startDate và endDate
	 */
	public static final int ADM003_YEAR_ADDITION_VALUE = 1;
	/*
	 * giá trị mặc định của group
	 */
	public static final int ADM003_DEFAULT_GROUP = 0;
	/*
	 * giá trị total mặc định
	 */
	public static final int ADM003_DEFAULT_TOTAL = 0;
	/*
	 * đường dẫn đến servlet add user confirm
	 */
	public static final String ADD_USER_CONFIRM = "/AddUserConfirm.do";
	/*
	 * đường dẫn đến jsp ADM003
	 */
	public static final String ADM003_JSP = "/jsp/ADM003.jsp";
	
}