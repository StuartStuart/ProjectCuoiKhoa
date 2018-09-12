/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import entities.TblUserEntity;
import entities.UserInforEntity;

/**
 * đối tượng TblUserDao
 * 
 * @author TrongNguyen
 *
 */
public interface TblUserDao {
	/**
	 * kiểm tra trong db loginId có là admin ko
	 * 
	 * @param loginId
	 *            cần check
	 * @return true là đúng
	 * @throws Exception
	 */
	boolean checkAdminAccount(String loginId) throws Exception;

	/**
	 * nhận về 1 admin account có username tương ứng
	 * 
	 * @param userName
	 *            tên đăng nhập
	 * @return 1 admin account
	 * @throws Exception
	 */
	TblUserEntity getLoginUser(String userName) throws Exception;

	/**
	 * nhận về tổng số user trong db theo groupId và fullName
	 * 
	 * @param groupId
	 *            loại group_id có trong db
	 * @param fullName
	 *            chuỗi full_name có trong db
	 * @return tổng cộng các user có đủ 2 tham số trên
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
	 * lấy từ db các thông tin của user dựa vào userId
	 * 
	 * @param userId
	 *            id của người dùng - là duy nhất
	 * @return một đối tượng chứa thông tin của người dùng
	 */
	UserInforEntity getUserInfor(int userId) throws Exception;

	/**
	 * nhận về từ db đối tượng thỏa mãn id và loginName
	 * 
	 * @param userId
	 *            id của đối tượng
	 * @param loginName
	 *            tên đăng nhập của đối tượng
	 * @return một bản ghi có tên đăng nhập và id tương ứng hoặc null
	 * @throws Exception
	 */
	boolean checkExistedLoginName(final Integer userId, final String loginName) throws Exception;

	/**
	 * xác định đối tượng với email có tồn tại hay không
	 * 
	 * @param userId
	 *            id của đối tượng
	 * @param email
	 *            email của đối tượng
	 * @return true là tồn tại
	 * @throws Exception
	 */
	boolean checkExistedEmail(final Integer userId, final String email) throws Exception;

	/**
	 * insert các thông tin vào bảng tbl_user
	 * 
	 * @param userInfor
	 *            đối tượng chứa thông tin tbl_user va tbl_detail_japan
	 * @throws Exception
	 */
	void insertUser(UserInforEntity userInfor) throws Exception;

	/**
	 * nhận userId có loginName tương ứng từ db
	 * 
	 * @param loginName
	 *            tên đăng nhập
	 * @return id của login name
	 * @throws Exception
	 */
	Integer getUserIdByLoginName(final String loginName) throws Exception;

	/**
	 * xóa user có id tương ứng từ db
	 * 
	 * @param userId
	 *            id của user cần xóa
	 * @return true là xóa thành công
	 * @throws SQLException
	 */
	boolean deleteUser(Integer userId) throws SQLException;

	/**
	 * gọi db để udpate thông tin của user tương ứng
	 * 
	 * @param userInforEntity
	 *            đối tượng có thông tin càn update
	 * @return true là update thành công
	 * @throws Exception
	 */
	void updateUser(UserInforEntity userInforEntity) throws Exception;

	/**
	 * kiểm tra trong db có tồn tại userId tương ứng ko
	 * 
	 * @param userId
	 *            id cần kiểm tra
	 * @return true là tồn tại
	 * @throws Exception
	 */
	public boolean checkExistedUserId(Integer userId) throws Exception;

	/**
	 * update pass và salt vào db ứng vs userid
	 * 
	 * @param userId
	 *            user sẽ được update
	 * @param pass
	 *            pass cần update
	 * @param salt
	 *            salt sễ update
	 * @return true là update thành công
	 * @throws Exception 
	 */
	public boolean updatePasswordForId(Integer userId, String pass, String salt) throws Exception;
}
