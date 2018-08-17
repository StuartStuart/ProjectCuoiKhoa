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
	public static final String SAP_XEP_TANG = "ASC";
	/*
	 * sắp xếp giảm
	 */
	public static final String SAP_XEP_GIAM = "DESC";
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
	 * giữ trạng thái ADM002
	 */
	public static final String ADM002_SAVED_STATUS = "ADM002_SAVED_STATUS";
	/*
	 * không giữ trạng thái ADM002
	 */
	public static final String ADM002_NEW_STATUS = "ADM002_NEW_STATUS";
}
