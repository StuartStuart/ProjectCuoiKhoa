/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.ResultSet;

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
				ps.setDate(++i, new java.sql.Date(userInfor.getStartDate().getTime()));
				ps.setDate(++i, new java.sql.Date(userInfor.getEndDate().getTime()));
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
	 * @param userId
	 *            id của user cần xóa
	 * @throws Exception
	 */
	public void deleteUserById(Integer userId) throws Exception {
		try {// viết query
			query = "DELETE FROM tbl_detail_user_japan WHERE user_id = ?; "
					/*+ "SET @num := 0; "
					+ "UPDATE tbl_detail_user_japan SET id = @num := (@num+1); "
					+ "ALTER TABLE tbl_detail_user_japan AUTO_INCREMENT = 1;"*/;

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
