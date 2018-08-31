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

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblDetailUserJapanDao#insertUser(entities.UserInforEntity)
	 */
	@Override
	public void insertUser(UserInforEntity userInfor) throws Exception {
		try {
			boolean isExistedConnection = !(null == conn || conn.isClosed());
			if (!isExistedConnection) {
				openConnection();
			}
			if (!userInfor.getCodeLevel().equalsIgnoreCase("N0")) {
				// viết query
				StringBuilder query = new StringBuilder("");
				query.append("INSERT INTO tbl_detail_user_japan(user_id, code_level, start_date, end_date, total)");
				query.append(" VALUES (?, ?, ?, ?, ?);");
				// hoàn thiện query
				ps = conn.prepareStatement(query.toString());
				int i = 0;
				ps.setInt(++i, new TblUserDaoImpl().getUserIdByLoginName(userInfor.getLoginName()));
				ps.setString(++i, userInfor.getCodeLevel());
				ps.setDate(++i, new java.sql.Date(userInfor.getStartDate().getTime()));
				ps.setDate(++i, new java.sql.Date(userInfor.getEndDate().getTime()));
				ps.setInt(++i, userInfor.getTotal());
				// thực hiện query
				ps.executeUpdate();
			}

			if (isExistedConnection) {
				closeConnection();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * xóa user trong tbl_detail_user_japan có id tương ứng
	 * 
	 * @param userId id của user cần xóa
	 * @throws Exception 
	 */
	public void deleteUserById(Integer userId) throws Exception {
		try {// viết query
			query = "DELETE FROM tbl_detail_user_japan WHERE user_id = ?;";
			// hoàn thiện query
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, userId);
			// thực hiện query
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
