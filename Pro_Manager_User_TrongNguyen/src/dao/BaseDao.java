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
}
