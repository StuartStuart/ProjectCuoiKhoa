/**
 * Copyright(C) 2018 	Luvina
 * MstGroupDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dao.MstGroupDao;
import entities.TblMstGroupEntity;
import utils.ConstantUtil;

/**
 * đối tượng MstGroupDao
 * 
 * @author TrongNguyen
 *
 */
public class MstGroupDaoImpl extends BaseDaoImpl implements MstGroupDao {

	/*
	 * nhận về tất cả mst group trong db
	 * 
	 * @see dao.MstGroupDao#getAllMstGroup()
	 */
	@Override
	public ArrayList<TblMstGroupEntity> getAllMstGroup() throws Exception {
		ArrayList<TblMstGroupEntity> listAllMstGroup = new ArrayList<>();

		try {
			this.openConnection();

			query = "select * from mst_group;";
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery(); // nháº­n vá»� cÃ¡c báº£n ghi

			// nhận các thông tin từ db
			while (rs.next()) {
				// lưu thông tin vào đối tượng
				TblMstGroupEntity tblMstGroupEntity = new TblMstGroupEntity();
				tblMstGroupEntity.setGroupId(rs.getInt("group_id"));
				tblMstGroupEntity.setGroupName(rs.getString("group_name"));

				listAllMstGroup.add(tblMstGroupEntity);
			}

		} catch (Exception e) {
			throw e;
		} finally {
			this.closeConnection();
		}

		return listAllMstGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.MstGroupDao#checkExistedGroupId(int)
	 */
	@Override
	public boolean checkExistedGroupId(int groupId) throws Exception {
		try {
			openConnection();
			// tạo query
			query = "SELECT COUNT(*) AS totalGroup FROM mst_group WHERE group_id = ?;";
			// hoàn thiện query
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setInt(++i, groupId);
			// nhận về giá trị
			return ps.executeQuery().next();

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				this.closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
