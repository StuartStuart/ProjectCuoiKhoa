/**
 * Copyright(C) 2018 	Luvina
 * BaseDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import dao.BaseDao;
import properties.DatabaseProperties;

/**
 * Ä�á»‘i tÆ°á»£ng base dao
 * 
 * @author TrongNguyen
 *
 */
public class BaseDaoImpl implements BaseDao {
	protected Connection conn;
	protected Statement stt;
	protected PreparedStatement ps;
	protected String query;

	protected static ArrayList<String> listDBFieldName;
	public static final String[] WHITE_LIST = { "tbl_user.full_name", "mst_japan.code_level",
			"tbl_detail_user_japan.end_date" };

	/* (non-Javadoc)
	 * @see dao.BaseDao#initListDBFieldName()
	 * khởi tạo whitelist
	 */
	@Override
	public void initListDBFieldName() throws Exception {
		if (null == listDBFieldName) {
			listDBFieldName = new ArrayList<>();
			try {
				/*
				 * kiểm tra db tồn tại
				 */
				// tên db đang được sử dụng
				final String nameDB = DatabaseProperties.getValue("dbname");

				/*
				 * thêm tên các trường vào listDBFieldName
				 */
				// rsTable chứa tên các Table thuộc DB đang xét
				ResultSet rsTables = conn.getMetaData().getTables(null, null, null, new String[] { "TABLE" });
				while (rsTables.next()) {
					String nameTable = rsTables.getString("TABLE_NAME");

					/*
					 * lấy tên tất cả các trường trong table
					 */
					ResultSet rsFields = conn.getMetaData().getColumns(null, nameDB, nameTable, null);
					while (rsFields.next()) {
						/*
						 * kiểm tra tên trường đã tồn tại hay chưa, nếu chưa thì
						 * thêm vào listTableFieldName
						 */
						String nameField = rsFields.getString("COLUMN_NAME");

						if (0 == listDBFieldName.size()) {
							listDBFieldName.add(nameTable + "." + nameField);
						} else {
							/*
							 * kiểm tra nameField đã tồn tại chưa nếu chưa thì
							 * thêm vào listTableFieldName
							 */
							for (final String oldColName : listDBFieldName) {
								// không tồn tại trong listDBFieldName
								if (!oldColName.matches(".*" + nameField + "$")) {
									listDBFieldName.add(nameTable + "." + nameField);
									break;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * má»Ÿ káº¿t ná»‘i db
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException
	 */
	@Override
	public void openConnection() throws Exception {
		String className;
		// nháº­n vá»� className
		try {
			className = DatabaseProperties.getValue("className");
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		// káº¿t ná»‘i Ä‘áº¿n Class
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}

		// má»Ÿ káº¿t ná»‘i Ä‘áº¿n cÆ¡ sá»Ÿ dá»¯ liá»‡u
		if (conn == null) {
			try {
				// thiáº¿t láº­p káº¿t ná»‘i
				conn = DriverManager.getConnection(
						DatabaseProperties.getValue("url") + DatabaseProperties.getValue("dbname"),
						DatabaseProperties.getValue("user"), DatabaseProperties.getValue("pass"));
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Ä‘Ã³ng káº¿t ná»‘i db
	 * 
	 * @throws SQLException
	 */
	@Override
	public void closeConnection() throws SQLException {
		try {
			if (conn != null) {
				conn.close();
				conn = null;
			}
			if (stt != null) {
				stt.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see dao.BaseDao#isExistColName(java.lang.String)
	 */
	@Override
	public boolean isExistColName(String nameField) throws Exception {
		try {
			this.initListDBFieldName();
			for (final String nameDBField : listDBFieldName) {
				if (nameDBField.matches(".*" + nameField + "$")) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
