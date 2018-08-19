/**
 * Copyright(C) 2018 	Luvina
 * BaseDao.java, Aug 13, 2018, TrongNguyen
 */
package dao;

import java.sql.SQLException;

/**
 * Đối tượng base dao
 * 
 * @author TrongNguyen
 *
 */
public interface BaseDao {
	void openConnection() throws Exception;

	void closeConnection() throws SQLException;

	/**
	 * Lưu tên các trường trong db chỉ với 1 câu query
	 * 
	 * Select COLUMN_NAME From INFORMATION_SCHEMA.COLUMNS Where TABLE_SCHEMA Like
	 * 'manager_user_trongnguyen';
	 * 
	 * @throws Exception
	 */
	void getListDBFieldName() throws Exception;
}
