/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

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
}
