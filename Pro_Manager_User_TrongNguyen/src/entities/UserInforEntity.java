/**
 * Copyright(C) 2018 	Luvina
 * UserInforEntity.java, Aug 16, 2018, LA-PM
 */
package entities;

/**
 * Các thông tin cần thiết của 1 user trong db
 * 
 * @author LA-PM
 *
 */
public class UserInforEntity {
	private int userId;
	private String fullName;
	private String birthDay;
	private String groupName;
	private String email;
	private String tel;
	private String nameLevel;
	private MstJapanEntity mstJapan;
	private String startDate;
	private String endDate;
	private int total;

	/**
	 * @return the mstJapan
	 */
	public MstJapanEntity getMstJapan() {
		return mstJapan;
	}

	/**
	 * @param mstJapan the mstJapan to set
	 */
	public void setMstJapan(MstJapanEntity mstJapan) {
		this.mstJapan = mstJapan;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getNameLevel() {
		return nameLevel;
	}

	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
