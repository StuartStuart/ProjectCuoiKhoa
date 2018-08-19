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

	protected void initListDMFieldName() throws Exception {
		if (null == listDBFieldName) {
			listDBFieldName = new ArrayList<>();
			try {
				openConnection();
				/*
				 * kiểm tra db tồn tại
				 */
				// tên db đang được sử dụng
				final String nameDB = DatabaseProperties.getValue("dbname");

				// rsDB chứa tên các DB
				ResultSet rsDataBases = conn
						.prepareStatement("Select DISTINCT(TABLE_SCHEMA) From INFORMATION_SCHEMA.COLUMNS;")
						.executeQuery();
				while (rsDataBases.next()) {
					// db tồn tại
					if (nameDB.equals(rsDataBases.getString("TABLE_SCHEMA"))) {
						/*
						 * thêm tên các trường vào listDBFieldName
						 */
						// rsTable chứa tên các Table thuộc DB đang xét
						ResultSet rsTables = conn.prepareStatement(
								"Select DISTINCT(TABLE_NAME) From INFORMATION_SCHEMA.COLUMNS Where TABLE_SCHEMA = '"
										+ nameDB + "';")
								.executeQuery();
						while (rsTables.next()) {
							/*
							 * list tên trường thuộc 1 table sẽ đổ dữ liệu đã qua xử lý vào listDBFieldName
							 */
							ArrayList<String> listTableFieldName = new ArrayList<>();

							String nameTable = rsTables.getString("TABLE_NAME");

							/*
							 * lấy tên tất cả các trường trong table
							 */
							ResultSet rsFields = conn.prepareStatement("Select COLUMN_NAME "
									+ "From INFORMATION_SCHEMA.COLUMNS " + "Where TABLE_SCHEMA = '" + nameDB
									+ "' AND TABLE_NAME = " + nameTable + ";").executeQuery();
							while (rsFields.next()) {
								/*
								 * kiểm tra tên trường đã tồn tại hay chưa, nếu chưa thì thêm vào
								 * listTableFieldName
								 */
								String nameField = rsFields.getString("COLUMN_NAME");

								if (0 == listDBFieldName.size()) {
									listTableFieldName.add(nameTable + "." + nameField);
								} else {
									/*
									 * kiểm tra nameField đã tồn tại chưa nếu chưa thì thêm vào listTableFieldName
									 */
								}
							}
							// đổ dữ liệu listTableFieldName vào listDBFieldName

						}
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
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

	public static void main(String[] args) {
		ArrayList<Integer> a1 = new ArrayList<>();

		ArrayList<Integer> a2 = new ArrayList<>();
		a2.add(1);
		a2.add(2);
		a2.add(3);
		a2.add(4);
		a2.add(5);
		a1.addAll(a2);
		a1.addAll(a2);
		for(int i:a1) {
			System.out.print(i);
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
	 * @see dao.BaseDao#getListDBColummeName()
	 * 
	 */
	@Override
	public void getListDBFieldName() throws Exception {
		if (null == listDBFieldName) {
			/*
			 * Select COLUMN_NAME From INFORMATION_SCHEMA.COLUMNS Where TABLE_SCHEMA Like
			 * 'manager_user_trongnguyen' AND TABLE_NAME in
			 * ('tbl_user','mst_japan','tbl_detail_user_japan');
			 */
			// lấy trực tiếp danh sách tên các trường trong DB
			// gán listDBFieldName là danh sách các trường đã lấy

		}
	}
}
