/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package logics;

import java.util.ArrayList;

import entities.TblMstGroupEntity;

/**
 * đối tượng MstGroupLogic
 * @author TrongNguyen
 *
 */
public interface MstGroupLogic {
	ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception;
}
