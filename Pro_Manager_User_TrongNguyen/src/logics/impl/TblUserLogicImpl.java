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
				return false;
			} else { // tài khoản tồn tại
				String encodedPass = CommonUtil.encodeMatKhau(pass, adminAccount.getSalt());
//				 System.out.println(encodedPass);
				boolean isTrungMatKhau = adminAccount.getPassword().equals(encodedPass);
				if (!isTrungMatKhau) { // 2 pass không trung nhau
					return false;
				}
			}

			return true;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#getUser(int)
	 */
	@Override
	public UserInforEntity getUserInfor(int userId) throws Exception {
		return tblUserDaoImpl.getUserInfor(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExistedLoginName(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExistedLoginName(Integer userId, String loginName) throws Exception {
		try {
			return tblUserDaoImpl.checkExistedLoginName(userId, loginName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExistedEmail(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExistedEmail(Integer userId, String email) throws Exception {
		return tblUserDaoImpl.checkExistedEmail(userId, email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#createUser(entities.UserInforEntity)
	 */
	@Override
	public boolean createUser(UserInforEntity userInfor) throws Exception {
		try {
			return tblUserDaoImpl.createUser(userInfor);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#getUserIdByLoginName(java.lang.String)
	 */
	@Override
	public Integer getUserIdByLoginName(String loginName) throws Exception {
		try {
			return tblUserDaoImpl.getUserIdByLoginName(loginName);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#deleteUserById(java.lang.Integer)
	 */
	@Override
	public boolean deleteUser(Integer userId) throws Exception {
		try {
			return tblUserDaoImpl.deleteUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#updateUser(entities.UserInforEntity)
	 */
	@Override
	public boolean updateUser(UserInforEntity userInforEntity) {
		try {
			return tblUserDaoImpl.updateUser(userInforEntity);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}
}
