/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.util.ArrayList;

import dao.MstJapanDao;
import dao.impl.MstJapanDaoImpl;
import entities.MstJapanEntity;
import logics.MstJapanLogic;

/**
 * đối tượng MstJapanLogic
 * 
 * @author TrongNguyen
 *
 */
public class MstJapanLogicImpl extends BaseLogicImpl implements MstJapanLogic {
	MstJapanDao mstJapan;

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstJapanLogic#getAllMstJapan()
	 */
	@Override
	public ArrayList<MstJapanEntity> getAllMstJapan() throws Exception {
		return mstJapan.getAllMstJapan();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.BaseLogic#init()
	 */
	@Override
	public void init() {
		mstJapan = new MstJapanDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstJapanLogic#checkExistedCodeLevel(int)
	 */
	@Override
	public boolean checkExistedCodeLevel(String codeLevel) throws Exception {
		return mstJapan.checkExistedCodeLevel(codeLevel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstJapanLogic#getMstJapanByCodeLevel(java.lang.String)
	 */
	@Override
	public MstJapanEntity getMstJapanByCodeLevel(String codeLevel) throws Exception {
		return mstJapan.getMstJapanByCodeLevel(codeLevel);
	}
}
