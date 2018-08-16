/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanEntity.java, Aug 14, 2018, LA-PM
 */
package entities;

/**
 * @author LA-PM
 *
 */
public class TblDetailUserJapanEntity {
	private int detail_user_japan_id;
	private int user_id;
	private String code_level;
	private String start_date;
	private String end_date;
	private int total;

	public int getDetail_user_japan_id() {
		return detail_user_japan_id;
	}

	public void setDetail_user_japan_id(int detail_user_japan_id) {
		this.detail_user_japan_id = detail_user_japan_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public String getCode_level() {
		return code_level;
	}

	public void setCode_level(String code_level) {
		this.code_level = code_level;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
