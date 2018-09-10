/**
 * Copyright(C) 2018 	Luvina
 * TblDetailUserJapanDao.java, Aug 13, 2018, TrongNguyen
 */
package logics;

/**
 * đối tượng TblDetailUserJapanLogic
 * 
 * @author TrongNguyen
 *
 */
public interface TblDetailUserJapanLogic {

	/**
	 * kiểm tra sự tồn tại của userId trong tbl_detail
	 * 
	 * @param userId
	 *            userId cần check
	 * @return true là userId có tồn tại trong tbl_detail
	 * @throws Exception 
	 */
	boolean checkExistUserId(Integer userId) throws Exception;
}
