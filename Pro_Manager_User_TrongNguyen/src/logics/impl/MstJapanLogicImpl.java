/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.util.ArrayList;

import dao.impl.MstJapanDaoImpl;
import entities.MstJapanEntity;
import logics.MstJapanLogic;

/**
 * đối tượng MstJapanLogic
 * 
 * @author TrongNguyen
 *
 */
public class MstJapanLogicImpl implements MstJapanLogic {

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstJapanLogic#getAllMstJapan()
	 */
	@Override
	public ArrayList<MstJapanEntity> getAllMstJapan() throws Exception {
		return new MstJapanDaoImpl().getAllMstJapan();
	}

}
