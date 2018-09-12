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
	 * @return connection
	 */
	Connection getConnection();

	/**
	 * @param conn connection
	 */
	void setConnection(Connection conn);

	/**
	 * kết nối connection conn
	 * 
	 * @param conn connection cần kết nối
	 * @throws Exception
	 */
	void openConnection(Connection conn) throws Exception;

	/**
	 * ngắt kết nối connection conn
	 * 
	 * @param conn connection cần ngắt kết nối
	 * @throws SQLException
	 */
	void closeConnection(Connection conn) throws SQLException;

	/**
	 * set autocommit cho connection là false để thực hiện transaction
	 * 
	 * @param conn connection cần set auto commit
	 * @throws SQLException
	 */
	void setAutoCommit(Connection conn) throws SQLException;

	/**
	 * commit nhóm lệnh đã thực hiện
	 * 
	 * @param conn connection cần commit
	 * @throws SQLException
	 */
	void commitTransaction(Connection conn) throws SQLException;

	/**
	 * rollback nhóm lệnh đã thực hiện
	 * 
	 * @param conn connection cần rollback
	 * @throws SQLException
	 */
	void rollbackTransaction(Connection conn) throws SQLException;

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
}
