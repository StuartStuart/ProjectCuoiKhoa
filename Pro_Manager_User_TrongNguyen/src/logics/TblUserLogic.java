/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package logics;

import java.util.ArrayList;

import entities.UserInforEntity;

/**
 * đối tượng TblUserLogic
 * 
 * @author TrongNguyen
 *
 */
public interface TblUserLogic {
	/**
	 * kiểm tra tài khoản có tồn tại trong database hay ko
	 * 
	 * @param userName
	 *            tên đăng nhập
	 * @param pass
	 *            mật khẩu
	 * @return true (tài khoản không tồn tại) hoặc false (tài khoản tồn tại)
	 * @throws Exception
	 */
	public boolean checkExist(String userName, String pass) throws Exception;

	/**
	 * nhận về tổng số User có group_id và full_name tương ứng
	 * 
	 * @param groupId
	 *            mã nhóm làm việc
	 * @param fullName
	 *            tên đầy đủ
	 * @return tổng số user tương ứng
	 * @throws Exception
	 */
	int getTotalUser(int groupId, String fullName) throws Exception;

	/**
	 * Lấy về danh sách các User từ db
	 * 
	 * @param offSet
	 *            vị trí của User đầu tiên
	 * @param limit
	 *            số bản ghi tối đa
	 * @param groupId
	 *            mã group_id trong db
	 * @param fullName
	 *            full_name trong db
	 * @param sortType
	 *            loại cần sắp xếp
	 * @param sortByFullName
	 *            sắp xếp theo tên đầy đủ
	 * @param sortByCodeLevel
	 *            sắp xếp theo mức code
	 * @param sortByEndDate
	 *            sắp xếp theo ngày hêt hạn
	 * @return danh sách TblUserEntity
	 * @throws Exception
	 */
	ArrayList<UserInforEntity> getListUser(int offSet, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception;

	/**
	 * lấy thông tin user từ dao
	 * 
	 * @param userId
	 *            id của người dùng - là duy nhất
	 * @return một đối tượng chứa thông tin của người dùng
	 * @throws Exception
	 */
	public UserInforEntity getUserInfor(int userId) throws Exception;

	/**
	 * nhận về đối tượng có loginName tương ứng
	 * 
	 * @param userId
	 *            user_id của đối tượng cần lấy
	 * @param loginName
	 *            tên đăng nhập của đối tượng cần lấy
	 * @return đối tượng có loginName tương ứng
	 * @throws Exception
	 */
	public boolean checkExistedLoginName(final Integer userId, final String loginName) throws Exception;

	/**
	 * 
	 * @param userId
	 * @param email
	 * @return
	 * @throws Exception
	 */
	public boolean checkExistedEmail(final Integer userId, final String email) throws Exception;

	/**
	 * gọi đến phương thức tạo của dao
	 * 
	 * @param userInfor
	 *            đối tượng cần tạo
	 * @return true là tạo thành công
	 * @throws Exception
	 */
	public boolean createUser(UserInforEntity userInfor) throws Exception;

	/**
	 * nhận userId có loginName tương ứng từ dao
	 * 
	 * @param loginName
	 *            tên đăng nhập
	 * @return id của login name
	 * @throws Exception
	 */
	Integer getUserIdByLoginName(final String loginName) throws Exception;

	/**
	 * gọi dao để xóa user có id tương ứng
	 * 
	 * @param userId
	 *            id của user cần xóa
	 * @return true là xóa thành công
	 * @throws Exception
	 */
	boolean deleteUser(Integer userId) throws Exception;

	/**
	 * gọi dao để update user có thông tin tương ứng
	 * 
	 * @param userInforEntity
	 *            đối tượng cần update
	 * @return true là update thành công
	 */
	boolean updateUser(UserInforEntity userInforEntity);

	/**
	 * kiểm tra sự tồn tại của userId bởi dao
	 * 
	 * @param userId
	 *            id cần kiểm tra
	 * @return true là tồn tại
	 * @throws Exception 
	 */
	public boolean checkExistedUserId(Integer userId) throws Exception;
}
