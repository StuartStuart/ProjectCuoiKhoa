/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import dao.TblDetailUserJapanDao;
import dao.impl.TblDetailUserJapanDaoImpl;
import logics.TblDetailUserJapanLogic;

/**
 * đối tượng TblDetailUserJapanLogic
 * 
 * @author TrongNguyen
 *
 */
public class TblDetailUserJapanLogicImpl implements TblDetailUserJapanLogic {
	TblDetailUserJapanDao detailDao = new TblDetailUserJapanDaoImpl();

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblDetailUserJapanLogic#checkExistUserId(java.lang.Integer)
	 */
	@Override
	public boolean checkExistUserId(Integer userId) throws Exception {
		return detailDao.checkExistUserId(userId);
	}

}
