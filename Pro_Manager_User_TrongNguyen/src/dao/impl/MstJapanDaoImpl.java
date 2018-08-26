/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.MstJapanDao;
import entities.MstJapanEntity;

/**
 * đối tượng MstJapanDao
 * 
 * @author TrongNguyen
 *
 */
public class MstJapanDaoImpl extends BaseDaoImpl implements MstJapanDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.MstJapanDao#getAllMstJapan()
	 */
	@Override
	public ArrayList<MstJapanEntity> getAllMstJapan() throws Exception {
		ArrayList<MstJapanEntity> listAllMstJapan = new ArrayList<>();

		try {
			this.openConnection();

			query = "select * from mst_japan;";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery(); // nháº­n vá»� cÃ¡c báº£n ghi

			// nhận các thông tin từ db
			while (rs.next()) {
				// lưu thông tin vào đối tượng
				MstJapanEntity mstJapanEntity = new MstJapanEntity();
				mstJapanEntity.setCodeLevel(rs.getString("code_level"));
				mstJapanEntity.setNameLevel(rs.getString("name_level"));

				listAllMstJapan.add(mstJapanEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeConnection();
		}

		return listAllMstJapan;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.MstJapanDao#checkExistedCodeLevel(int)
	 */
	@Override
	public boolean checkExistedCodeLevel(String codeLevel) throws SQLException {
		try { // mở conn
			openConnection();
			// tạo query
			StringBuilder query = new StringBuilder("");
			query.append("SELECT COUNT(code_level)");
			query.append(" FROM mst_japan");
			query.append(" WHERE code_level = ?");
			query.append(";");
			// hoàn thiện query
			ps = conn.prepareStatement(query.toString());
			int i = 0;
			ps.setString(++i, codeLevel);
			// thực hiện query
			ResultSet rs = ps.executeQuery();
			// kiểm tra tồn tại
			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			// đóng conn
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
}
