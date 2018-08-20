/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import dao.TblUserDao;
import entities.TblUserEntity;
import entities.UserInforEntity;
import utils.CommonUtil;
import utils.ConstantUtil;

/**
 * Ä‘á»‘i tÆ°á»£ng TblUserDao
 * 
 * @author TrongNguyen
 *
 */
public class TblUserDaoImpl extends BaseDaoImpl implements TblUserDao {

	/**
	 * tìm tài khoản tương ứng username trong db
	 * 
	 * @param userName
	 *            tên đăng nhập
	 * @return tài khoản tương ứng username trong db
	 * @throws Exception
	 */
	@Override
	public TblUserEntity getAdminAccount(String userName) throws Exception {
		TblUserEntity tblU = new TblUserEntity();
		try {
			this.openConnection(); // mở kết nối
			query = "select * from tbl_user where `login_name` = ?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);

			ResultSet rs = ps.executeQuery(); // nháº­n vá»� cÃ¡c báº£n ghi
			rs.next();

			// lưu thông tin vào đối tượng
			tblU.setUserId(rs.getInt("tbl_user.user_id"));
			tblU.setGroupId(rs.getInt("tbl_user.group_id"));
			tblU.setLoginName(rs.getString("tbl_user.login_name"));
			tblU.setPassword(rs.getString("tbl_user.password"));
			tblU.setFullName(rs.getString("tbl_user.full_name"));
			tblU.setFullNameKana(rs.getString("tbl_user.full_name_kana"));
			tblU.setEmail(rs.getString("tbl_user.email"));
			tblU.setTel(rs.getString("tbl_user.tel"));
			tblU.setBirthDay(rs.getString("tbl_user.birthday"));
			tblU.setSalt(rs.getString("tbl_user.salt"));
			tblU.setCategory(rs.getInt("tbl_user.category"));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(); // đóng kết nối
		}
		return tblU;
	}

	/*
	 * @see dao.TblUserDao#getTotalUser(int, java.lang.String)
	 */
	@Override
	public int getTotalUser(int groupIdSearching, String fullNameSearching) throws Exception {
		int totalUsers = -1;
		try {
			this.openConnection();

			// tạo câu truy vấn
			StringBuilder query = new StringBuilder("SELECT COUNT(*) AS totalUsers FROM tbl_user WHERE category = 1");
			if (groupIdSearching > 0) {
				query.append(" AND group_id = ?");
			}
			if (null != fullNameSearching && !"".equals(fullNameSearching)) {
				query.append(" AND full_name LIKE ?");
			}
			query.append(";");
			ps = conn.prepareStatement(query.toString());

			// hoàn thiện câu truy vấn
			int i = 0;
			if (groupIdSearching > 0) {
				ps.setInt(++i, groupIdSearching);
			}
			if (null != fullNameSearching && !"".equals(fullNameSearching)) {
				fullNameSearching = CommonUtil.convertWildCard(fullNameSearching);
				ps.setString(++i, "%" + fullNameSearching + "%");
			}

			// lấy về kết quả
			ResultSet rs = ps.executeQuery();
			rs.next();
			totalUsers = rs.getInt("totalUsers");

			return totalUsers;
		} catch (Exception e) {
			throw e;
		} finally {
			this.closeConnection();
		}
	}

	/*
	 * @see dao.TblUserDao#getTotalUser(int, java.lang.String)
	 */
	@Override
	public ArrayList<UserInforEntity> getListUser(int offSet, int limit, int groupIdSearching, String fullNameSearching,
			String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception {
		ArrayList<UserInforEntity> listUser = new ArrayList<>();
		try {
			this.openConnection();

			// thêm các trường thông tin cần lấy của User
			StringBuilder query = new StringBuilder("SELECT");
			for (String infor : ConstantUtil.USER_INFORMATIONS) {
				query.append(" " + infor + ",");
			}
			query.deleteCharAt(query.length() - 1);

			// thêm bản ghi(đã được join với nhau) mà các thông tin được lấy từ
			// đó
			query.append("\nFROM");
			query.append(" tbl_user INNER JOIN mst_group ON tbl_user.group_id = mst_group.group_id");
			query.append(" LEFT JOIN tbl_detail_user_japan ON tbl_user.user_id = tbl_detail_user_japan.user_id");
			query.append(" LEFT JOIN mst_japan ON tbl_detail_user_japan.code_level = mst_japan.code_level");

			// thêm điều kiện tìm kiếm
			query.append("\nWHERE category = 1");
			if (groupIdSearching > 0) { // tìm kiếm theo groupId được lựa chọn
				query.append(" AND tbl_user.group_id = ?");
			}
			if (null != fullNameSearching && !"".equals(fullNameSearching)) {
				query.append(" AND tbl_user.full_name LIKE ?");
			}

			/*
			 * nếu sortType tồn tại trong db thì thực hiện order by, nếu không
			 * thì thực hiện bình thường
			 */
			// thêm điều kiện sắp xếp
			query.append("\nORDER BY ");

			StringBuilder queryTail = new StringBuilder("");
			/*
			 * các kiểu sắp xếp tương ứng với loại sắp xếp
			 */
			String[] sortingWays = { sortByFullName, sortByCodeLevel, sortByEndDate };
			int lengthSortTypes = ConstantUtil.CAC_LOAI_SAP_XEP.length;
			/*
			 * thêm loại và kiểu sắp xếp còn lại theo thứ tự
			 */
			if (!this.isExistColName(sortType)) { // be hacked
				sortType = ConstantUtil.CAC_LOAI_SAP_XEP[0];
			}
			
			String cachSapXepCuaKieuUuTien = "";
			for (int i = 0; i < lengthSortTypes; i++) {
				if (!ConstantUtil.CAC_LOAI_SAP_XEP[i].equals(sortType)) {
					queryTail.append(", " + ConstantUtil.CAC_LOAI_SAP_XEP[i]);
					if (sortingWays.length > i) {
						queryTail.append(" " + sortingWays[i]);
					} else { // Khi có kiểu sắp xếp với cách sắp được ngầm
								// định
						queryTail.append(" " + ConstantUtil.SAP_XEP_MAC_DINH);
					}
				} else {
					cachSapXepCuaKieuUuTien = sortingWays[i];
				}
			}
			// nối queryTail vào đuôi query
			query.append(sortType).append(" ").append(cachSapXepCuaKieuUuTien);
			query.append(queryTail);
			// thêm giời hạn số lượng bản ghi
			query.append(" LIMIT " + limit);

			// thêm vị trí đầu tiên lấy
			query.append(" OFFSET " + offSet);

			// đúng syntax
			query.append(";");

			/*
			 * hoàn thiện câu truy vấn
			 */
			ps = conn.prepareStatement(query.toString());
			int i = 0;
			if (groupIdSearching > 0) { // tìm kiếm theo groupId được lựa chọn
				ps.setInt(++i, groupIdSearching);
			}
			if (null != fullNameSearching && !"".equals(fullNameSearching)) {
				fullNameSearching = CommonUtil.convertWildCard(fullNameSearching);
				ps.setString(++i, "%" + fullNameSearching + "%");
			}

			/*
			 * lấy về dữ liệu
			 */
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				// lưu thông tin vào đối tượng
				UserInforEntity userInfors = new UserInforEntity();
				userInfors.setUserId(rs.getInt("user_id"));
				userInfors.setFullName(rs.getString("full_name"));
				userInfors.setBirthDay(rs.getString("birthday"));
				userInfors.setGroupName(rs.getString("group_name"));
				userInfors.setEmail(rs.getString("email"));
				userInfors.setTel(rs.getString("tel"));
				userInfors.setNameLevel(rs.getString("name_level"));
				userInfors.setEndDate(rs.getString("end_date"));
				userInfors.setTotal(rs.getInt("total"));

				listUser.add(userInfors);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeConnection();
		}

		return listUser;
	}
}
