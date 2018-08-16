/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.util.ArrayList;

import dao.impl.MstGroupDaoImpl;
import entities.TblMstGroupEntity;
import logics.MstGroupLogic;

/**
 * đối tượng MstGroupLogic
 * 
 * @author TrongNguyen
 *
 */
public class MstGroupLogicImpl extends BaseLogicImpl implements MstGroupLogic {
	private MstGroupDaoImpl mstGroupDaoImpl;

	/*
	 * khởi tạo đối tượng mstGroupDao
	 * 
	 * @see logics.BaseLogic#init()
	 */
	@Override
	public void init() {
		mstGroupDaoImpl = new MstGroupDaoImpl();
	}

	/*
	 * nhận về tất cả MstGroup trong db
	 * 
	 * @see logics.MstGroupLogic#getAllMstGroup()
	 */
	@Override
	public ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception {
		return mstGroupDaoImpl.getAllMstGroup();
	}

}
