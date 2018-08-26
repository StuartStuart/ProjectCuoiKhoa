/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import dao.TblDetailUserJapanDao;
import entities.UserInforEntity;

/**
 * đối tượng TblDetailUserJapanDao
 * 
 * @author TrongNguyen
 *
 */
public class TblDetailUserJapanDaoImpl extends BaseDaoImpl implements TblDetailUserJapanDao {

	/* (non-Javadoc)
	 * @see dao.TblDetailUserJapanDao#insertUser(entities.UserInforEntity)
	 */
	@Override
	public void insertUser(UserInforEntity userInfor) throws Exception {
		System.out.println("Đã chạy qua insertUser of tbl_detail_user_japan!");
		
	}

}
