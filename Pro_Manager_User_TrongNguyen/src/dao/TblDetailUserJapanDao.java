/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import entities.UserInforEntity;

/**
 * đối tượng TblDetailUserJapanDao
 * 
 * @author TrongNguyen
 *
 */
public interface TblDetailUserJapanDao extends BaseDao {
	/**
	 * insert thông tin vào tbl_detail_user_japan
	 * 
	 * @param userInfor đối tượng chứa thông tin của tbl_user và
	 *                  tbl_detail_user_japan
	 * @throws Exception
	 */
	void insertUser(UserInforEntity userInfor) throws Exception;

	/**
	 * xóa thông tin từ tbl)detail_user_japan
	 * 
	 * @param userId id của obj cần xóa
	 * @throws Exception 
	 */
	void deleteUserById(Integer userId) throws Exception;
}
