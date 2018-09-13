/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.util.ArrayList;

import entities.MstJapanEntity;

/**
 * đối tượng MstJapanDao
 * 
 * @author TrongNguyen
 *
 */
public interface MstJapanDao extends BaseDao {
	/**
	 * nhận về các trình độ tiếng Nhật
	 * 
	 * @return list trình độ tiếng Nhật
	 * @throws Exception
	 */
	ArrayList<MstJapanEntity> getAllMstJapan() throws Exception;

	/**
	 * kiểm tra sự tồn tại của codelevel trong db
	 * 
	 * @param codeLevel mã trình đô tiếng Nhật
	 * @return true là tồn tại
	 * @throws Exception 
	 */
	boolean checkExistedCodeLevel(String codeLevel) throws Exception;
	
	/**
	 * lấy về đối tượng MstJapanEntity từ db
	 * 
	 * @param codeLevel mã trình độ tiếng Nhật
	 * @return đối tượng MstJapanEntity
	 * @throws Exception
	 */
	MstJapanEntity getMstJapanByCodeLevel(final String codeLevel) throws Exception;
}
