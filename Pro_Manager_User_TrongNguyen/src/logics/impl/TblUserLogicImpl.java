/**
 * Copyright(C) 2018 	Luvina
 * TblUserDao.java, Aug 13, 2018, TrongNguyen
 */
package logics.impl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import dao.impl.TblUserDaoImpl;
import entities.TblUserEntity;
import entities.UserInforEntity;
import logics.TblUserLogic;

/**
 * Ä‘á»‘i tÆ°á»£ng TblUserLogic
 * 
 * @author TrongNguyen
 *
 */
public class TblUserLogicImpl extends BaseLogicImpl implements TblUserLogic {
	private TblUserDaoImpl tblUserDaoImpl;

	/*
	 * Khởi tạo tblUserDaoImpl
	 * 
	 * @see logics.BaseLogic#init()
	 */
	@Override
	public void init() {
		tblUserDaoImpl = new TblUserDaoImpl();
	}

	/**
	 * kiểm tra username có được nhập hay không
	 * 
	 * @param userName tên đăng nhập
	 * @return true (đã được nhập) hoặc false (chưa được nhập)
	 */
	public boolean isTenNguoiDungRong(String userName) {
		return "".equals(userName);
	}

	/**
	 * kiểm tra mật khẩu có rỗng hay không
	 * 
	 * @param pass mật khẩu
	 * @return true (đã được nhập) hoặc false (chưa được nhập)
	 */
	public boolean isMatKhauRong(String pass) {
		return "".equals(pass);
	}

	/**
	 * kiểm tra tài khoản có tồn tại trong database hay ko
	 * 
	 * @param userName tên đăng nhập
	 * @param pass     mật khẩu
	 * @return true (tài khoản không tồn tại) hoặc false (tài khoản tồn tại)
	 * @throws Exception
	 */
	public boolean isSaiTaiKhoan(String userName, String pass) throws Exception {
		try {
			// mỗi tài khoản có username duy nhất
			TblUserEntity adminAccount = tblUserDaoImpl.getAdminAccount(userName);
			if (null == adminAccount) { // tài khoản không tồn tại
				return true;
			} else { // tài khoản tồn tại
				String encodedPass = encodeMatKhau(pass, adminAccount.getSalt());
//				System.out.println(encodedPass);
				boolean isTrungMatKhau = adminAccount.getPassword().equals(encodedPass);
				if (!isTrungMatKhau) { // 2 pass không trung nhau
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * mã hóa chuỗi pass + salt sang SHA1
	 * 
	 * @param pass mật khẩu
	 * @param salt chuỗi gây nhiễu
	 * @return chuỗi đã mã hóa
	 * @throws NoSuchAlgorithmException
	 */
	private String encodeMatKhau(String pass, String salt) throws NoSuchAlgorithmException {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest((pass + salt).getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
			return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw e;
		}
	}

	/*
	 * @see logics.TblUserLogic#getTotalUser(int, java.lang.String)
	 */
	@Override
	public int getTotalUser(int groupId, String fullName) throws Exception {
		try {
			return tblUserDaoImpl.getTotalUser(groupId, fullName);
		} catch (Exception e) {
			throw e;
		}
	}

	/*
	 * 
	 * @see logics.TblUserLogic#getListUser(int, int, int, java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<UserInforEntity> getListUser(int offSet, int limit, int groupId, String fullName, String sortType,
			String sortByFullName, String sortByCodeLevel, String sortByEndDate) throws Exception {
		return this.tblUserDaoImpl.getListUser(offSet, limit, groupId, fullName, sortType, sortByFullName,
				sortByCodeLevel, sortByEndDate);
	}
}
