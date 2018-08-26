/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package logics;

import java.util.ArrayList;

import entities.MstJapanEntity;

/**
 * đối tượng MstJapanLogic
 * 
 * @author TrongNguyen
 *
 */
public interface MstJapanLogic {
	/**
	 * lấy danh sách các câp độ tiếng Nhật từ DB
	 * 
	 * @return danh sách các cấp độ tiếng Nhật
	 * @throws Exception
	 */
	ArrayList<MstJapanEntity> getAllMstJapan() throws Exception;

	/**
	 * kiểm tra sự tồn tại cũa codeLevel trong db
	 * 
	 * @param codeLevel mã trình độ tiếng Nhật
	 * @return true là tồn tại
	 * @throws Exception
	 */
	boolean checkExistedCodeLevel(String codeLevel) throws Exception;

	/**
	 * lấy về đối tượng MstJapanEntity từ dao
	 * 
	 * @param codeLevel mã trình độ tiếng Nhật
	 * @return đối tượng MstJapanEntity
	 * @throws Exception
	 */
	MstJapanEntity getMstJapanByCodeLevel(final String codeLevel) throws Exception;
}
