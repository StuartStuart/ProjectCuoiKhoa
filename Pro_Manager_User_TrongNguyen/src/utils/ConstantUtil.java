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
}