/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.util.ArrayList;

import entities.TblMstGroupEntity;

/**
 * đối tượng MstGroupDao
 * @author TrongNguyen
 *
 */
public interface MstGroupDao{
	ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception;
}
