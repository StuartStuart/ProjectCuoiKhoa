/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import dao.TblDetailUserJapanDao;
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
	public TblUserEntity getLoginUser(String userName) throws Exception {
		try {
			this.openConnection(); // mở kết nối

			query = "select * from tbl_user where `login_name` = ?;";
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, userName);

			ResultSet rs = ps.executeQuery(); // nháº­n vá»� cÃ¡c báº£n ghi
			if (rs.next()) {
				TblUserEntity tblU = new TblUserEntity();
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

				return tblU;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			this.closeConnection(); // đóng kết nối
		}
		return null;
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
			if (rs.next()) {
				totalUsers = rs.getInt("totalUsers");
			}

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
			String[] userInfors = { "tbl_user.user_id", "tbl_user.full_name", "tbl_user.birthday",
					"mst_group.group_name", "tbl_user.email", "tbl_user.tel", "mst_japan.name_level",
					"tbl_detail_user_japan.end_date", "tbl_detail_user_japan.total" };
			// thêm các trường thông tin cần lấy của User
			StringBuilder query = new StringBuilder("SELECT");
			for (String infor : userInfors) {
				query.append(" " + infor + ",");
			}
			query.deleteCharAt(query.length() - 1);

			// thêm bản ghi(đã được join với nhau) mà các thông tin được lấy từ
			// đó
			query.append("\nFROM");
			query.append(" tbl_user INNER JOIN mst_group ON tbl_user.group_id = mst_group.group_id");
			query.append(" LEFT JOIN (tbl_detail_user_japan");
			query.append(" INNER JOIN mst_japan ON tbl_detail_user_japan.code_level = mst_japan.code_level)");
			query.append(" ON tbl_user.user_id = tbl_detail_user_japan.user_id");

			// thêm điều kiện tìm kiếm
			query.append("\nWHERE category = ");
			query.append(ConstantUtil.USER_CATEGORY);
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
			query.append(chuoiDieuKienSX(sortType, sortByFullName, sortByCodeLevel, sortByEndDate));
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
				UserInforEntity userInfor = new UserInforEntity();
				userInfor.setUserId(rs.getInt("user_id"));
				userInfor.setFullName(rs.getString("full_name"));
				userInfor.setBirthDay(rs.getDate("birthday"));
				userInfor.setGroupName(rs.getString("group_name"));
				userInfor.setEmail(rs.getString("email"));
				userInfor.setTel(rs.getString("tel"));
				userInfor.setNameLevel(rs.getString("name_level"));
				userInfor.setEndDate(rs.getDate("end_date"));
				userInfor.setTotal(rs.getInt("total"));

				listUser.add(userInfor);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			this.closeConnection();
		}

		return listUser;
	}

	/**
	 * xác đinh chuỗi điều kiện order by
	 * 
	 * @param sortType
	 *            loại sắp xếp
	 * @param sortByFullName
	 *            kiểu sx của tên
	 * @param sortByCodeLevel
	 *            kiểu sx của codeLevel
	 * @param sortByEndDate
	 *            kiểu sx của endDate
	 * @return chuỗi điều kiện sx
	 * @throws Exception
	 */
	private String chuoiDieuKienSX(String sortType, String sortByFullName, String sortByCodeLevel, String sortByEndDate)
			throws Exception {
		StringBuilder chuoiSX = new StringBuilder("");
		// /*
		// * các kiểu sắp xếp tương ứng với loại sắp xếp
		// */
		// String[] sortingWays = { sortByFullName, sortByCodeLevel,
		// sortByEndDate };
		// int lengthSortTypes = WHITE_LIST.length;
		// /*
		// * thêm loại và kiểu sắp xếp còn lại theo thứ tự
		// */
		// if (!this.isExistColName(sortType)) { // be hacked
		// sortType = WHITE_LIST[0];
		// }
		//
		// String cachSapXepCuaKieuUuTien = "";
		// for (int i = 0; i < lengthSortTypes; i++) {
		// if (!WHITE_LIST[i].equals(sortType)) {
		// chuoiSX.append(", " + WHITE_LIST[i]);
		// if (sortingWays.length > i) {
		// chuoiSX.append(" " + sortingWays[i]);
		// } else { // Khi có kiểu sắp xếp với cách sắp được ngầm
		// // định
		// chuoiSX.append(" " + ConstantUtil.ADM002_SX_TANG);
		// }
		// } else {
		// cachSapXepCuaKieuUuTien = sortingWays[i];
		// }
		// }
		// // nối queryTail vào đuôi query
		// return sortType + " " + cachSapXepCuaKieuUuTien + " " +
		// chuoiSX.toString();

		try {
			/*
			 * sortType tại trong WhiteList và không phải fullName
			 */
			if (isExistColName(sortType) && !WHITE_LIST[0].equals(sortType)) {
				chuoiSX.append(sortType);
				if (WHITE_LIST[1].equals(sortType)) { // codeLevel
					chuoiSX.append(" " + sortByCodeLevel);
					/*
					 * Sắp xếp theo default
					 */
					// theo tên
					chuoiSX.append(", " + WHITE_LIST[0]);
					chuoiSX.append(" COLLATE utf8_unicode_ci");
					chuoiSX.append(" " + sortByFullName);
					// theo endDate
					chuoiSX.append(", " + WHITE_LIST[2]);
					chuoiSX.append(" " + sortByEndDate);
				} else if (WHITE_LIST[2].equals(sortType)) { // endDate
					chuoiSX.append(" " + sortByEndDate);
					/*
					 * Sắp xếp theo default
					 */
					// theo tên
					chuoiSX.append(", " + WHITE_LIST[0]);
					chuoiSX.append(" COLLATE utf8_unicode_ci");
					chuoiSX.append(" " + sortByFullName);
					// theo codeLevel
					chuoiSX.append(", " + WHITE_LIST[1]);
					chuoiSX.append(" " + sortByCodeLevel);
				}
			} else {
				/*
				 * Sắp xếp theo default
				 */
				// theo tên
				chuoiSX.append(WHITE_LIST[0]);
				chuoiSX.append(" COLLATE utf8_unicode_ci");
				chuoiSX.append(" " + sortByFullName);
				// theo codeLevel
				chuoiSX.append(", " + WHITE_LIST[1]);
				chuoiSX.append(" " + sortByCodeLevel);
				// theo endDate
				chuoiSX.append(", " + WHITE_LIST[2]);
				chuoiSX.append(" " + sortByEndDate);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return chuoiSX.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserInfor(int)
	 */
	@Override
	public UserInforEntity getUserInfor(int userId) throws Exception {
		try {
			openConnection();
			// viết câu query để lấy thông tin
			String[] userInfors = { "tbl_user.user_id", "tbl_user.login_name", "mst_group.group_name",
					"tbl_user.full_name", "tbl_user.full_name_kana", "tbl_user.birthday", "tbl_user.email",
					"tbl_user.tel", "mst_japan.code_level", "mst_japan.name_level", "tbl_detail_user_japan.start_date",
					"tbl_detail_user_japan.end_date", "tbl_detail_user_japan.total" };
			// thêm các trường thông tin cần lấy của User
			StringBuilder query = new StringBuilder("SELECT");
			for (String infor : userInfors) {
				query.append(" " + infor + ",");
			}
			query.deleteCharAt(query.length() - 1);

			// thêm bản ghi(đã được join với nhau) mà các thông tin được lấy từ
			// đó
			query.append("\nFROM");
			query.append(" tbl_user INNER JOIN mst_group ON tbl_user.group_id = mst_group.group_id");
			query.append(" LEFT JOIN (tbl_detail_user_japan");
			query.append(" INNER JOIN mst_japan ON tbl_detail_user_japan.code_level = mst_japan.code_level)");
			query.append(" ON tbl_user.user_id = tbl_detail_user_japan.user_id");

			// thêm điều kiện tìm kiếm category
			query.append("\nWHERE category = ");
			query.append(ConstantUtil.USER_CATEGORY);

			// thêm userId vào preparedStatement
			query.append(" AND tbl_user.user_id = ?;");

			// cho câu query vào preparedStatement
			ps = conn.prepareStatement(query.toString());

			// thực hiện truy vấn
			int i = 0;
			ps.setInt(++i, userId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				// tồn tại đối tượng

				// thì tạo đối tượng và set các thuộc tính
				UserInforEntity userInfor = new UserInforEntity();

				i = 0;
				userInfor.setUserId(rs.getInt(userInfors[i++]));
				userInfor.setLoginName(rs.getString(userInfors[i++]));
				userInfor.setGroupName(rs.getString(userInfors[i++]));
				userInfor.setFullName(rs.getString(userInfors[i++]));
				userInfor.setFullNameKana(rs.getString(userInfors[i++]));
				userInfor.setBirthDay(rs.getDate(userInfors[i++]));
				userInfor.setEmail(rs.getString(userInfors[i++]));
				userInfor.setTel(rs.getString(userInfors[i++]));
				userInfor.setCodeLevel(rs.getString(userInfors[i++]));
				userInfor.setNameLevel(rs.getString(userInfors[i++]));
				userInfor.setStartDate(rs.getDate(userInfors[i++]));
				userInfor.setEndDate(rs.getDate(userInfors[i++]));
				userInfor.setTotal(rs.getInt(userInfors[i++]));

				return userInfor;
			} else {
				// không tồn tại đối tượng

				// thì TO DO, tạm thời trả về null
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			closeConnection();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#checkExistedLoginName(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExistedLoginName(final Integer userId, final String loginName) throws Exception {
		try {
			openConnection();

			// viết câu query
			StringBuilder query = new StringBuilder("");
			query.append(
					"SELECT user_id, group_id, login_name, `password`, full_name, full_name_kana, email, tel, birthday, salt, category");
			query.append(" FROM tbl_user");
			query.append(" WHERE category = ?");
			if (null != userId) {
				query.append(" AND NOT user_id = ?");
			}
			if (!loginName.isEmpty()) {
				query.append(" AND login_name = ?");
			}
			query.append(";");
			// hoàn thiện query
			ps = conn.prepareStatement(query.toString());
			int i = 0;
			ps.setInt(++i, ConstantUtil.USER_CATEGORY);
			if (null != userId) {
				ps.setInt(++i, userId);
			}
			if (!loginName.isEmpty()) {
				ps.setString(++i, loginName);
			}
			// thực hiện query
			return ps.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#checkExistedEmail(java.lang.Integer,
	 * java.lang.String)
	 */
	@Override
	public boolean checkExistedEmail(Integer userId, String email) throws Exception {
		try {
			openConnection();

			// viết câu query
			StringBuilder query = new StringBuilder("");
			query.append(
					"SELECT user_id, group_id, login_name, `password`, full_name, full_name_kana, email, tel, birthday, salt, category");
			query.append(" FROM tbl_user");
			query.append(" WHERE category = ?");
			if (null != userId) {
				query.append(" AND NOT user_id = ?");
			}
			if (!email.isEmpty()) {
				query.append(" AND email = ?");
			}
			query.append(";");
			// hoàn thiện query
			ps = conn.prepareStatement(query.toString());
			int i = 0;
			ps.setInt(++i, ConstantUtil.USER_CATEGORY);
			if (null != userId) {
				ps.setInt(++i, userId);
			}
			if (!email.isEmpty()) {
				ps.setString(++i, email);
			}
			// thực hiện query
			return ps.executeQuery().next();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#createUser(entities.UserInforEntity)
	 */
	@Override
	public boolean createUser(UserInforEntity userInfor) throws Exception {

		try {
			// mở conn
			openConnection();
			// set commit
			conn.setAutoCommit(false);
			// tran 1
			insertUser(userInfor);
			// tran 2
			TblDetailUserJapanDao japanDao = new TblDetailUserJapanDaoImpl();
			japanDao.setConn(this.conn);
			japanDao.insertUser(userInfor);
			// commit
			conn.commit();
			// hoàn thành tất cả - trả về true
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// roll back ve save point
			conn.rollback();
			// phải roll back - trả về false
			return false;
		} finally {
			try {
				// đóng conn
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
	 * @see dao.TblUserDao#insertUser(entities.UserInforEntity)
	 */
	@Override
	public void insertUser(UserInforEntity userInfor) throws Exception {
		// viết query
		StringBuilder query = new StringBuilder("");
		query.append(
				"INSERT INTO tbl_user(group_id, login_name, `password`, full_name, full_name_kana, email, tel, birthday, salt, category)");
		query.append(" VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
		// hoàn thiện query
		ps = conn.prepareStatement(query.toString());
		int i = 0;
		ps.setInt(++i, userInfor.getGroupId());
		ps.setString(++i, userInfor.getLoginName());
		String salt = new Date().getTime() + "";
		ps.setString(++i, CommonUtil.encodeMatKhau(userInfor.getPass(), salt));
		ps.setString(++i, userInfor.getFullName());
		ps.setString(++i, userInfor.getFullNameKana());
		ps.setString(++i, userInfor.getEmail());
		ps.setString(++i, userInfor.getTel());
		ps.setDate(++i, new java.sql.Date(userInfor.getBirthDay().getTime()));
		ps.setString(++i, salt);
		ps.setInt(++i, ConstantUtil.USER_CATEGORY);
		// thực hiện query
		ps.executeUpdate();
		ps.close();
		ps = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.TblUserDao#getUserIdByLoginName(java.lang.String)
	 */
	@Override
	public Integer getUserIdByLoginName(String loginName) throws SQLException {

		try {
			openConnection();
			Integer userId;

			// viết query
			query = "SELECT user_id FROM tbl_user WHERE login_name = ?;";
			ps = conn.prepareStatement(query);
			int i = 0;
			ps.setString(++i, loginName);
			// thực hiện query
			ResultSet rs = ps.executeQuery();
			// trả về kết quả
			if (rs.next()) {
				userId = rs.getInt("user_id");
			} else {
				userId = null;
			}

			return userId;
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
	 * @see dao.TblUserDao#deleteUserById(java.lang.Integer)
	 */
	@Override
	public boolean deleteUser(Integer userId) throws SQLException {
		try {
			// đồng bộ conn
			openConnection();
			// set AutoCommit
			conn.setAutoCommit(false);
			// tran 1
			TblDetailUserJapanDao japanDao = new TblDetailUserJapanDaoImpl();
			japanDao.setConn(this.conn);
			japanDao.deleteUserById(userId);
			// tran 2
			deleteUserById(userId);
			// commit
			conn.commit();
			// trả về kết quả
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		} finally {
			try {
				closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * xóa user từ db
	 * 
	 * @param userId
	 *            id của user cần xóa
	 * @throws Exception
	 */
	public void deleteUserById(Integer userId) throws Exception {
		try {
			// viết query
			query = "DELETE FROM tbl_user WHERE user_id = ?; "/* + "SET @num := 0; "
					+ "	UPDATEtbl_usere SET id = @num := (@num+1);" + " ALTER TABLEtbl_usere AUTO_INCREMENT = 1;"*/;
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
	 * @see dao.TblUserDao#updateUser(entities.UserInforEntity)
	 */
	@Override
	public boolean updateUser(UserInforEntity userInforEntity) {
		System.out.println("Đã chạy qua đây");
		return false;
	}
}