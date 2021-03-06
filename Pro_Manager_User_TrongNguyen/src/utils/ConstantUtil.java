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
	 * file chứa các cặp key - value liên quan đến Message Error
	 */
	public static final String MESSAGE = "Message.properties";
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
	public static final String ADM002_SORT_TYPE_URL[] = { "tbl_user.full_name", "mst_japan.code_level",
			"tbl_detail_user_japan.end_date" };
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
	/**
	 * URL của DeleteController
	 */
	public static final String DELETE_CONTROLLER = "/DeleteUser.do";
	/*
	 * đường dẫn đến servlet add user confirm
	 */
	public static final String ADD_USER_CONFIRM_CONTROLLER = "/AddUserConfirm.do";
	/*
	 * đường dẫn đến jsp ADM003
	 */
	public static final String ADM003_JSP = "/jsp/ADM003.jsp";
	/*
	 * đường dẫn đến ADM004
	 */
	public static final String ADM004_JSP = "/jsp/ADM004.jsp";
	/*
	 * đường dẫn đến ADM006
	 */
	public static final String ADM006_JSP = "/jsp/ADM006.jsp";
	/*
	 * đường dẫn đến system error
	 */
	public static final String SYSTEM_ERROR_JSP = "/jsp/System_Error.jsp";
	/*
	 * đường dẫn đến successController
	 */
	public static final String SUCCESS_CONTROLLER = "/Success.do";
	/**
	 * URL thứ 1 của AddUserInputController
	 */
	public static final String ADD_USER_INPUT_CONTROLLER = "/AddUserInput.do";
	/**
	 * URL thứ 2 của AddUserInputController
	 */
	public static final String ADD_USER_VALIDATE_CONTROLLER = "/AddUserValidate.do";
	/**
	 * URL thứ 3 của AddUserInputController
	 */
	public static final String EDIT_USER_INPUT_CONTROLLER = "/EditUserInput.do";
	/*
	 * gọi đến màn hình ADM006 ở trạng thái thành công
	 */
	public static final String ADM006_ADD_TYPE = "success";
	/*
	 * gọi đến màn hình ADM006 ở trạng thái lỗi
	 */
	public static final String ADM006_ERROR_TYPE = "error";
	/*
	 * ký tự kana đầu tiên
	 */
	public static final char FIRST_KANA = (char) 0x30a0;
	/*
	 * ký tự kana cuối cùng
	 */
	public static final char LAST_KANA = (char) 0x30ff;
	/*
	 * biểu thức chính quy của email
	 */
	public static final String EMAIL_REGREX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$";
	/*
	 * biểu thức chính quy của login name
	 */
	public static final String LOGIN_NAME_REGREX = "^[a-zA-Z]+[a-zA-Z0-9_]*";
	/*
	 * ký tự 1 byte đầu tiên
	 */
	public static final char FIRST_ONE_BYTE_CHAR = (char) 1;
	/*
	 * ký tự 1 byte cuối cùng
	 */
	public static final char LAST_ONE_BYTE_CHAR = (char) 128;
	/*
	 * gọi đến ADM006 ở trạng thái delete
	 */
	public static final String ADM006_DELETE_TYPE = "delete";
	/*
	 * đường dẫn đến ADM005
	 */
	public static final String ADM005_JSP = "/jsp/ADM005.jsp";
	/*
	 * gọi đến màn hình ADM006 ở trạng thái update
	 */
	public static final String ADM006_UPDATE_TYPE = "update";
	/**
	 * update detail japan bởi cách delete user
	 */
	public static final String DELETE_DETAIL_JAPAN_USER = "DELETE_DETAIL_JAPAN_USER";
	/**
	 * update detail japan bởi cách ko làm gì cả
	 */
	public static final String DO_NOTHING_DETAIL_JAPAN_USER = "DO_NOTHING_DETAIL_JAPAN_USER";
	/**
	 * update detail japan bởi cách update
	 */
	public static final String UPDATE_DETAIL_JAPAN_USER = "UPDATE_DETAIL_JAPAN_USER";
	/**
	 * update detail japan bởi cách insert
	 */
	public static final String INSERT_DETAIL_JAPAN_USER = "INSERT_DETAIL_JAPAN_USER";
	/**
	 * đường dẫn đến ADM007.jsp
	 */
	public static final String ADM007_JSP = "/jsp/ADM007.jsp";
	/**
	 * đường dẫn đến SystemErrorController
	 */
	public static final String SYSTEM_ERROR_CONTROLLER = "/SystemError.do";
	/**
	 * URL của ChangePassController
	 */
	public static final String CHANGE_PASS_CONTROLLER = "/ChangePass.do";
	/**
	 * submit ở màn hình ADM003
	 */
	public static final String ADM003_SUBMIT_TYPE = "submit";
	/*
	 * cho phép qua ADM006
	 */
	public static final String ADM006_TYPE = "THROUGH_ADM006";
	/*
	 * URL của ListUserController
	 */
	public static final String LIST_USER_CONTROLLER = "/ListUser.do";
	/*
	 * URL của ADM001
	 */
	public static final String ADM001_JSP = "/jsp/ADM001.jsp";
	/*
	 * url của LoginController
	 */
	public static final String LOGIN_CONTROLLER = "/login.do";
	/*
	 * url của LogoutController
	 */
	public static final String LOGOUT_CONTROLLER = "/logout.do";
	/**
	 * url của ShowUserController
	 */
	public static final String SHOW_USER_CONTROLLER = "/ShowUser.do";
	/**
	 * đánh dấu là đã login
	 */
	public static final String DANH_DAU_LOGIN = "loginId";
	/*
	 * URL của ADM002
	 */
	public static final String ADM002_JSP = "/jsp/ADM002.jsp";
	/**
	 * code level mặc định khi vào adm003
	 */
	public static final String DEFAULT_CODE_LEVEL = "N0";
	/*
	 * quay lại ADM002
	 */
	public static final String ADM002_BACK = "back";
	/*
	 * sortType mặc định khi search
	 */
	public static final Integer DEFAULT_SORT_TYPE = 0;
	/*
	 * currentPage mặc định của search
	 */
	public static final Integer DEFAULT_CURRENT_PAGE = 1;
	/*
	 * cho phép đến system_error
	 */
	public static final String SYSTEM_ERROR_TYPE = "SYSTEM_ERROR_TYPE";
	/**
	 * ký hiệu của admin
	 */
	public static final int ADMIN_CATEGORY = 0;
}