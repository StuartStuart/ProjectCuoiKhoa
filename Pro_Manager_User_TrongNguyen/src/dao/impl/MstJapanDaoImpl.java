/**
 * Copyright(C) 2018 	Luvina
 * MstJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.ResultSet;
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

	/* (non-Javadoc)
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
}
