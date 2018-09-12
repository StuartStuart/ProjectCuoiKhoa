/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.TblDetailUserJapanDao;
import dao.impl.TblDetailUserJapanDaoImpl;
import dao.impl.TblUserDaoImpl;
import entities.TblUserEntity;
import entities.UserInforEntity;
import logics.TblUserLogic;
import utils.CommonUtil;
import utils.ConstantUtil;

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
		tblUserDaoImpl.openConnection();
		Connection conn = tblUserDaoImpl.getConnection();
		try {
			// transaction
			tblUserDaoImpl.setAutoCommit(conn);
			// lệnh 1
			tblUserDaoImpl.insertUser(userInfor);
			// lệnh 2
			TblDetailUserJapanDao japanDao = new TblDetailUserJapanDaoImpl();
			japanDao.setConnection(conn);
			japanDao.insertUser(userInfor);
			// commit
			tblUserDaoImpl.commitTransaction(conn);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// rollback kết quả
			tblUserDaoImpl.rollbackTransaction(conn);

			return false;
		} finally {
			try {
				tblUserDaoImpl.closeConnection();
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
			if (loginName.isEmpty()) {
				return null;
			} else {
				return tblUserDaoImpl.getUserIdByLoginName(loginName);
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
		tblUserDaoImpl.openConnection();
		Connection conn = tblUserDaoImpl.getConnection();
		try {
			// transaction
			tblUserDaoImpl.setAutoCommit(conn);
			// lệnh 1
			TblDetailUserJapanDao japanDao = new TblDetailUserJapanDaoImpl();
			japanDao.setConnection(conn);
			japanDao.deleteUserById(userId);
			// lệnh 2
			tblUserDaoImpl.deleteUserById(userId);
			// commit
			tblUserDaoImpl.commitTransaction(conn);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// rollback kết quả
			tblUserDaoImpl.rollbackTransaction(conn);

			return false;
		} finally {
			try {
				tblUserDaoImpl.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see logics.TblUserLogic#updateUser(entities.UserInforEntity)
	 */
	@Override
	public boolean updateUser(UserInforEntity userInforEntity, String changeTblDetail) throws SQLException {
		tblUserDaoImpl.openConnection();
		Connection conn = tblUserDaoImpl.getConnection();
		try {
			// transaction
			tblUserDaoImpl.setAutoCommit(conn);
			// lệnh 1
			tblUserDaoImpl.updateUser(userInforEntity);
			// lệnh 2
			TblDetailUserJapanDao japanDao = new TblDetailUserJapanDaoImpl();
			japanDao.setConnection(conn);
			switch (changeTblDetail) {
			case ConstantUtil.UPDATE_DETAIL_JAPAN_USER:
				japanDao.updateUser(userInforEntity);

				break;
			case ConstantUtil.INSERT_DETAIL_JAPAN_USER:
				// insert
				japanDao.insertUser(userInforEntity);
				break;
			case ConstantUtil.DELETE_DETAIL_JAPAN_USER:
				// delete
				japanDao.deleteUserById(userInforEntity.getUserId());
				break;
			case ConstantUtil.DO_NOTHING_DETAIL_JAPAN_USER:
			default:
				break;
			}
			// commit
			tblUserDaoImpl.commitTransaction(conn);

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// rollback kết quả
			tblUserDaoImpl.rollbackTransaction(conn);

			return false;
		} finally {
			try {
				tblUserDaoImpl.closeConnection();
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
			return tblUserDaoImpl.checkExistedUserId(userId);
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
			return tblUserDaoImpl.checkAdminAccount(loginId);
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
			return tblUserDaoImpl.updatePasswordForId(userId, pass, salt);
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
			// check tồn tại codeLevel - isExistedCodeLevel
			boolean isExistedCodeLevel = new MstJapanLogicImpl().checkExistCodeLevel(userInforEntity.getCodeLevel());
			String changeTblDetail;
			if (!isExistedCodeLevel) {
				// nếu codeLevel ko tồn tại
				if (isExistedUserId) {
					// tồn tại id
					changeTblDetail = ConstantUtil.DELETE_DETAIL_JAPAN_USER;
				} else {
					// ko tồn tại id
					changeTblDetail = ConstantUtil.DO_NOTHING_DETAIL_JAPAN_USER;
				}
			} else {
				// codeLevel tồn tại
				if (isExistedUserId) {
					// tồn tại userId
					changeTblDetail = ConstantUtil.UPDATE_DETAIL_JAPAN_USER;
				} else {
					// ko tồn tại usr id
					changeTblDetail = ConstantUtil.INSERT_DETAIL_JAPAN_USER;
				}
			}
			/*
			 * thực hiện update
			 */
			tblUserDaoImpl.openConnection();
			Connection conn = tblUserDaoImpl.getConnection();
			try {
				// transaction
				tblUserDaoImpl.setAutoCommit(conn);
				// lệnh 1
				tblUserDaoImpl.updateUser(userInforEntity);
				// lệnh 2
				TblDetailUserJapanDao japanDao = new TblDetailUserJapanDaoImpl();
				japanDao.setConnection(conn);
				switch (changeTblDetail) {
				case ConstantUtil.UPDATE_DETAIL_JAPAN_USER:
					japanDao.updateUser(userInforEntity);

					break;
				case ConstantUtil.INSERT_DETAIL_JAPAN_USER:
					// insert
					japanDao.insertUser(userInforEntity);
					break;
				case ConstantUtil.DELETE_DETAIL_JAPAN_USER:
					// delete
					japanDao.deleteUserById(userInforEntity.getUserId());
					break;
				case ConstantUtil.DO_NOTHING_DETAIL_JAPAN_USER:
				default:
					break;
				}
				// commit
				tblUserDaoImpl.commitTransaction(conn);

				return true;
			} catch (Exception e) {
				e.printStackTrace();
				// rollback kết quả
				tblUserDaoImpl.rollbackTransaction(conn);

				return false;
			} finally {
				try {
					tblUserDaoImpl.closeConnection();
				} catch (SQLException e) {
					e.printStackTrace();
					throw e;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
