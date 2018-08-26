/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.util.ArrayList;

import dao.MstGroupDao;
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
	private MstGroupDao mstGroup;

	/*
	 * khởi tạo đối tượng mstGroupDao
	 * 
	 * @see logics.BaseLogic#init()
	 */
	@Override
	public void init() {
		mstGroup = new MstGroupDaoImpl();
	}

	/*
	 * nhận về tất cả MstGroup trong db
	 * 
	 * @see logics.MstGroupLogic#getAllMstGroup()
	 */
	@Override
	public ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception {
		return mstGroup.getAllMstGroup();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.MstGroupLogic#checkExistedGroupId(int)
	 */
	@Override
	public boolean checkExistedGroupId(int groupId) throws Exception {
		try {
			return mstGroup.checkExistedGroupId(groupId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/* (non-Javadoc)
	 * @see logics.MstGroupLogic#getMstGroupByGroupId(int)
	 */
	@Override
	public TblMstGroupEntity getMstGroupByGroupId(int groupId) throws Exception{
		try {
			return mstGroup.getMstGroupByGroupId(groupId);
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
