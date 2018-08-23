/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.util.ArrayList;

import dao.impl.TblUserDaoImpl;
import entities.TblUserEntity;
import entities.UserInforEntity;
import logics.TblUserLogic;
import utils.CommonUtil;

/**
 * Ä‘á»‘i tÆ°á»£ng TblUserLogic
 * 
 * @author TrongNguyen
 *
 */
public class TblUserLogicImpl extends BaseLogicImpl implements TblUserLogic {
	private TblUserDaoImpl tblUserDaoImpl;

	/*
	 * Khởi tạo tblUserDaoImpl
	 * 
	 * @see logics.BaseLogic#init()
	 */
	@Override
	public void init() {
		tblUserDaoImpl = new TblUserDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkExist(String userName, String pass) throws Exception {
		try {
			// mỗi tài khoản có username duy nhất
			TblUserEntity adminAccount = tblUserDaoImpl.getLoginUser(userName);
			if (null == adminAccount) { // tài khoản không tồn tại
				return true;
			} else { // tài khoản tồn tại
				String encodedPass = CommonUtil.encodeMatKhau(pass, adminAccount.getSalt());
				// System.out.println(encodedPass);
				boolean isTrungMatKhau = adminAccount.getPassword().equals(encodedPass);
				if (!isTrungMatKhau) { // 2 pass không trung nhau
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * @see logics.TblUserLogic#getTotalUser(int, java.lang.String)
	 */
	@Override
	public int getTotalUser(int groupId, String fullName) throws Exception {
		try {
			return tblUserDaoImpl.getTotalUser(groupId, fullName);
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * 
	 * @see logics.TblUserLogic#getListUser(int, int, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UserInforEntity> getListUser(int offSet, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception {
		return this.tblUserDaoImpl.getListUser(offSet, limit, groupId, fullName, sortType, sortByFullName,
				sortByCodeLevel, sortByEndDate);
	}
}
