/**
 * Copyright(C) 2018 	Luvina
 * BaseDao.java, Aug 13, 2018, TrongNguyen
 */
package dao.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import dao.BaseDao;
import properties.ConfigProperties;
import properties.DatabaseProperties;
import utils.ConstantUtil;

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
				conn = DriverManager.getConnection(DatabaseProperties.getValue("url"),
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
