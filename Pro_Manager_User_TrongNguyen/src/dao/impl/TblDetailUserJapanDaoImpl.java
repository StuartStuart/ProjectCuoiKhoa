/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

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
			if (!userInfor.getCodeLevel().equalsIgnoreCase("N0")) {
				Integer userId;
				//
				// viết query
				// viết query
				query = "SELECT user_id FROM tbl_user WHERE login_name = ?;";
				ps = conn.prepareStatement(query);
				int i = 0;
				ps.setString(++i, userInfor.getLoginName());
				// thực hiện query
				ResultSet rs = ps.executeQuery();
				// trả về kết quả
				if (rs.next()) {
					userId = rs.getInt("user_id");
				} else {
					userId = null;
				}
				// viết query
				StringBuilder query = new StringBuilder("");
				query.append("INSERT INTO tbl_detail_user_japan(user_id, code_level, start_date, end_date, total)");
				query.append(" VALUES (?, ?, ?, ?, ?);");
				// hoàn thiện query
				ps = conn.prepareStatement(query.toString());

				//
				i = 0;
				ps.setInt(++i, userId);
				ps.setString(++i, userInfor.getCodeLevel());
				ps.setString(++i, userInfor.getStartDate());
				ps.setString(++i, userInfor.getEndDate());
				ps.setInt(++i, userInfor.getTotal());
				// thực hiện query
				ps.executeUpdate();

				ps.close();
				ps = null;
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
			query = "DELETE FROM tbl_detail_user_japan WHERE user_id = ?; ";

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblDetailUserJapanDao#checkExistUserId(java.lang.Integer)
	 */
	@Override
	public boolean checkExistUserId(Integer userId) throws Exception {
		try {
			openConnection();

			// viết query
			query = "SELECT user_id FROM tbl_detail_user_japan WHERE user_id = ?;";
			// hoàn thiện query
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, userId);
			// thực hiện query và trả về kết quả
			return ps.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblDetailUserJapanDao#updateUser(entities.UserInforEntity)
	 */
	@Override
	public void updateUser(UserInforEntity userInforEntity) throws Exception {
		try {
			StringBuilder sb = new StringBuilder("");
			sb.append("UPDATE tbl_detail_user_japan");
			sb.append(" SET code_level = ?, start_date = ?, end_date = ?, total = ?");
			sb.append(" WHERE user_id = ?;");
			// hoàn thiện query
			ps = conn.prepareStatement(sb.toString());
			int i = 0;
			ps.setString(++i, userInforEntity.getCodeLevel());
			ps.setString(++i, userInforEntity.getStartDate());
			ps.setString(++i, userInforEntity.getStartDate());
			ps.setInt(++i, userInforEntity.getTotal());
			ps.setInt(++i, userInforEntity.getUserId());
			// thực hiện query
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
