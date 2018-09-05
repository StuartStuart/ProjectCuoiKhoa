/**
 * Copyright(C) 2018 	Luvina
 * BaseDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Đối tượng base dao
 * 
 * @author TrongNguyen
 *
 */
public interface BaseDao {
	/**
	 * mở kết nối đến db
	 * 
	 * @throws Exception
	 */
	void openConnection() throws Exception;

	/**
	 * đóng kết nối đến db
	 * 
	 * @throws SQLException
	 */
	void closeConnection() throws SQLException;

	/**
	 * khởi tạo danh sách tên các trường trong db
	 * 
	 * @throws Exception
	 */
	void initListDBFieldName() throws Exception;

	/**
	 * kiểm tra tên trường có tồn tại trong db hay ko
	 * 
	 * @param colName tên trường cần kiểm tra
	 * @return true - tồn tại trong db
	 */
	boolean isExistColName(String colName) throws Exception;

	/**
	 * set kết nối cho conn
	 * 
	 * @param conn obj kết nối đươc thiết lập
	 */
	void setConn(Connection conn);
}
