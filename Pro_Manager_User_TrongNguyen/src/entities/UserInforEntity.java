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
	private String endDate;
	private int total;

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

	public String getNameDevel() {
		return nameLevel;
	}

	public void setNameDevel(String nameLevel) {
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
