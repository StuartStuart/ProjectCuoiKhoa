/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.TblDetailUserJapanDao;
import dao.TblUserDao;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entities.TblDetailUserJapanEntity;
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
	private TblUserDao userDao;
	private TblDetailUserJapanDao japanDao;

	/*
	 * Khởi tạo tblUserDaoImpl
	 * 
	 * @see logics.BaseLogic#init()
	 */
	@Override
	public void init() {
		userDao = new TblUserDaoImpl();
		japanDao = new TblDetailUserJapanDaoImpl();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExist(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkExistUser(String loginId, String pass) throws Exception {
		try {
			// mỗi tài khoản có username duy nhất
			TblUserEntity adminAccount = userDao.getLoginUser(loginId);
			if (null == adminAccount) { // tài khoản không tồn tại
				return false;
			} else { // tài khoản tồn tại
				String encodedPass = CommonUtil.encodeMatKhau(pass, adminAccount.getSalt());
				// System.out.println(encodedPass);
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
	public int getTotalUser(Integer groupId, String fullName) throws Exception {
		try {
			return userDao.getTotalUser(groupId, fullName);
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
	public ArrayList<UserInforEntity> getListUser(int offSet, int limit, Integer groupId, String fullName,
			String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception {
		return this.userDao.getListUser(offSet, limit, groupId, fullName, sortType, sortByFullName, sortByCodeLevel,
				sortByEndDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#getUser(int)
	 */
	@Override
	public UserInforEntity getUserInfor(int userId) throws Exception {
		return userDao.getUserInfor(userId);
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
			return userDao.checkExistedLoginName(userId, loginName);
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
		return userDao.checkExistedEmail(userId, email);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#createUser(entities.UserInforEntity)
	 */
	@Override
	public boolean createUser(UserInforEntity userInfor) throws Exception {
		try {
			userDao.openConnection();
			// transaction
			userDao.setFalseAutoCommitTransaction();
			// lệnh 1
			userInfor.setUserId(userDao.insertUser(userInfor));
			// lệnh 2
			TblDetailUserJapanEntity detailUser = new TblDetailUserJapanEntity();
			detailUser.setUserId(userInfor.getUserId());
			detailUser.setCodeLevel(userInfor.getCodeLevel());
			detailUser.setStartDate(userInfor.getStartDate());
			detailUser.setEndDate(userInfor.getEndDate());
			detailUser.setTotal(userInfor.getTotal());
			japanDao.insertUser(detailUser);
			// commit
			userDao.commitTransaction();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// rollback kết quả
			userDao.rollbackTransaction();
			return false;
		} finally {
			try {
				userDao.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
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
			if (null != loginName && loginName.isEmpty()) {
				return null;
			} else {
				return userDao.getUserIdByLoginName(loginName);
			}
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
			userDao.openConnection();
			// transaction
			userDao.setFalseAutoCommitTransaction();
			// lệnh 1
			japanDao.setConnection(userDao.getConnection());
			japanDao.deleteUserById(userId);
			// lệnh 2
			userDao.deleteUserById(userId);
			// commit
			userDao.commitTransaction();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// rollback kết quả
			userDao.rollbackTransaction();

			return false;
		} finally {
			try {
				userDao.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkExistedUserId(java.lang.Integer)
	 */
	@Override
	public boolean checkExistedUserId(Integer userId) throws Exception {
		try {
			return userDao.checkExistedUserId(userId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#checkAdminAccount(java.lang.String)
	 */
	@Override
	public boolean checkAdminAccount(String loginId) throws Exception {
		try {
			return userDao.checkAdminAccount(loginId);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#updatePasswordForId(java.lang.Integer,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public boolean updatePasswordForId(Integer userId, String pass, String salt) throws Exception {
		try {
			return userDao.updatePasswordForId(userId, pass, salt);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#updateUser(entities.UserInforEntity, boolean)
	 */
	@Override
	public boolean updateUser(UserInforEntity userInforEntity, boolean isExistedUserId) throws Exception {
		try {
			userDao.openConnection();
			// transaction
			userDao.setFalseAutoCommitTransaction();
			// lệnh 1
			userDao.updateUser(userInforEntity);
			// lệnh 2
			japanDao.setConnection(userDao.getConnection());
			TblDetailUserJapanEntity detailUser = new TblDetailUserJapanEntity();
			detailUser.setUserId(userInforEntity.getUserId());
			detailUser.setCodeLevel(userInforEntity.getCodeLevel());
			detailUser.setStartDate(userInforEntity.getStartDate());
			detailUser.setEndDate(userInforEntity.getEndDate());
			detailUser.setTotal(userInforEntity.getTotal());
			// check tồn tại codeLevel - isExistedCodeLevel
			boolean isExistedCodeLevel = new MstJapanLogicImpl().checkExistCodeLevel(detailUser.getCodeLevel());
			if (!isExistedCodeLevel) {
				// nếu codeLevel ko tồn tại
				if (isExistedUserId) {
					// tồn tại id
					japanDao.deleteUserById(detailUser.getUserId());
				}
			} else {
				// codeLevel tồn tại
				if (isExistedUserId) {
					// tồn tại userId
					japanDao.updateUser(detailUser);
				} else {
					// ko tồn tại usr id
					japanDao.insertUser(detailUser);
				}
			}
			// commit
			userDao.commitTransaction();

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// rollback kết quả
			userDao.rollbackTransaction();

			return false;
		} finally {
			try {
				userDao.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
