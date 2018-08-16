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
}
