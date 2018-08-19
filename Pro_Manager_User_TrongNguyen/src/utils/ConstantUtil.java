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
	 * loại sắp xếp mặc định theo mySQL
	 */
	public static final String SAP_XEP_MAC_DINH = "ASC";
	/*
	 * sắp xếp tăng
	 */
	public static final String ADM002_TANG = "ASC";
	/*
	 * sắp xếp giảm
	 */
	public static final String ADM002_GIAM = "DESC";
	/*
	 * các thông tin cần lấy của user
	 */
	public static final String[] USER_INFORMATIONS = { "tbl_user.user_id", "tbl_user.full_name", "tbl_user.birthday",
			"mst_group.group_name", "tbl_user.email", "tbl_user.tel", "mst_japan.name_level",
			"tbl_detail_user_japan.end_date", "tbl_detail_user_japan.total" };
	/*
	 * các loại sắp xếp
	 */
	public static final String[] CAC_LOAI_SAP_XEP = { "tbl_user.full_name", "mst_japan.code_level",
			"tbl_detail_user_japan.end_date" };
	/*
	 * tìm kiếm trong ADM002
	 */
	public static final String ADM002_SEARCH = "search";
	/*
	 * sắp xếp tại ADM002
	 */
	public static final String ADM002_SORT = "sort";
	/*
	 * ưu tiên full name
	 */
	public static final String ADM002_FULL_NAME_SORT = "fullname";
	/*
	 * ưu tiên full name
	 */
	public static final String ADM002_CODE_LEVEL_SORT = "codelevel";
	/*
	 * ưu tiên end date
	 */
	public static final String ADM002_END_DATE_SORT = "enddate";
	/*
	 * paging tại ADM002
	 */
	public static final String ADM002_PAGING = "paging";
	/*
	 * chỉ định cho <<
	 */
	public static final String ADM002_PAGE_BACK="pageback";
	/*
	 * chỉ định cho >>
	 */
	public static final String ADM002_PAGE_NEXT="pagenext";
	/*
	 * biểu tượng sắp xếp tăng
	 */
	public static final String ADM002_ASC = "▲▽";
	/*
	 * biểu tượng sắp xếp giảm
	 */
	public static final String ADM002_DESC = "△▼";
}