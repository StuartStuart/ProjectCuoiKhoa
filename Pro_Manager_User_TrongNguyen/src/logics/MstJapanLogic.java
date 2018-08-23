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
}
