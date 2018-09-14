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
	private Integer userId;
	private String loginName;
	private int groupId;
	private String groupName;
	private TblMstGroupEntity mstGroup;
	private String fullName;
	private String fullNameKana;
	private int category;
	private String birthDay;
	private String email;
	private String tel;
	private String pass;
	private String repass;
	private String codeLevel;
	private String nameLevel;
	private MstJapanEntity mstJapan;
	private String startDate;
	private String endDate;
	private String total;

	/**
	 * @return the category
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(int category) {
		this.category = category;
	}

	/**
	 * @return the mstGroup
	 */
	public TblMstGroupEntity getMstGroup() {
		return mstGroup;
	}

	/**
	 * @param mstGroup the mstGroup to set
	 */
	public void setMstGroup(TblMstGroupEntity mstGroup) {
		this.mstGroup = mstGroup;
	}

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
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}

	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}

	/**
	 * @return the repass
	 */
	public String getRepass() {
		return repass;
	}

	/**
	 * @param repass the repass to set
	 */
	public void setRepass(String repass) {
		this.repass = repass;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the groupId
	 */
	public int getGroupId() {
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the fullNameKana
	 */
	public String getFullNameKana() {
		return fullNameKana;
	}

	/**
	 * @param fullNameKana the fullNameKana to set
	 */
	public void setFullNameKana(String fullNameKana) {
		this.fullNameKana = fullNameKana;
	}

	/**
	 * @return the birthDay
	 */
	public String getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay the birthDay to set
	 */
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * @return the nameLevel
	 */
	public String getNameLevel() {
		return nameLevel;
	}

	/**
	 * @param nameLevel the nameLevel to set
	 */
	public void setNameLevel(String nameLevel) {
		this.nameLevel = nameLevel;
	}

	/**
	 * @return the codeLevel
	 */
	public String getCodeLevel() {
		return codeLevel;
	}

	/**
	 * @param codeLevel the codeLevel to set
	 */
	public void setCodeLevel(String codeLevel) {
		this.codeLevel = codeLevel;
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

	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the total
	 */
	public String getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(String total) {
		this.total = total;
	}

}
