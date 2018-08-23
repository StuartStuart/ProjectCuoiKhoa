/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.util.ArrayList;

import entities.MstJapanEntity;

/**
 * đối tượng MstJapanDao
 * @author TrongNguyen
 *
 */
public interface MstJapanDao{
	/**
	 * nhận về các trình độ tiếng Nhật
	 * @return list trình độ tiếng Nhật
	 * @throws Exception
	 */
	ArrayList<MstJapanEntity> getAllMstJapan() throws Exception;
}
