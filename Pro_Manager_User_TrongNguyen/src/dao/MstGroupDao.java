/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.util.ArrayList;

import entities.TblMstGroupEntity;

/**
 * đối tượng MstGroupDao
 * 
 * @author TrongNguyen
 *
 */
public interface MstGroupDao extends BaseDao{
	/**
	 * nhận về danh sách các nhóm trong db
	 * 
	 * @return danh sách nhóm
	 * @throws Exception
	 */
	ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception;

	/**
	 * kiểm tra sự tồn tại của groupId trong db
	 * 
	 * @param groupId id của nhóm trong db
	 * @return true là tồn tại
	 * @throws Exception
	 */
	boolean checkExistedGroupId(final int groupId) throws Exception;
	
	/**
	 * nhân về 1 record mst group từ db
	 * 
	 * @param groupId
	 * @return 1 đối tượng mst group
	 * @throws Exception 
	 */
	TblMstGroupEntity getMstGroupByGroupId(final int groupId) throws Exception;
}
