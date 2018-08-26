/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package logics;

import java.util.ArrayList;

import entities.TblMstGroupEntity;

/**
 * đối tượng MstGroupLogic
 * 
 * @author TrongNguyen
 *
 */
public interface MstGroupLogic {
	/**
	 * nhận về danh sách các group có trong db
	 * 
	 * @return danh sách group
	 * @throws Exception
	 */
	ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception;

	/**
	 * kiểm tra groupId có tồn tại trong db
	 * 
	 * @param groupId mã nhóm
	 * @return true là có tồn tại
	 * @throws Exception
	 */
	boolean checkExistedGroupId(final int groupId) throws Exception;

	/**
	 * nhân về 1 record mst group từ dao
	 * 
	 * @param groupId
	 * @return 1 đối tượng mst group
	 * @throws Exception 
	 */
	TblMstGroupEntity getMstGroupByGroupId(final int groupId) throws Exception;
}
