/**
 * Copyright(C) 2018	Luvina
 * UserValidate.java, Aug 25, 2018, A
 */
package validates;

import java.util.ArrayList;

import entities.UserInforEntity;
import logics.impl.MstGroupLogicImpl;
import logics.impl.MstJapanLogicImpl;
import logics.impl.TblUserLogicImpl;
import properties.ConfigProperties;
import properties.MessageErrorProperties;
import utils.CommonUtil;

/**
 * @author TrongNguyen
 *
 */
public class UserValidate {

	/**
	 * kiểm tra các lỗi có thể tồn tại trong userInfor
	 * 
	 * @param userInfor đối tượng được kiểm tra
	 * @return danh sách các lỗi xuất hiện
	 * @throws Exception
	 */
	public ArrayList<String> validateUser(UserInforEntity userInfor) throws Exception {
		ArrayList<String> listErrMsg = new ArrayList<>();

		try {
			// validate login name
			{
				// check ko nhập
				if (userInfor.getLoginName().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_LoginName"));
				}
				// check đã tồn tại
				if (new TblUserLogicImpl().checkExistedLoginName(userInfor.getUserId(), userInfor.getLoginName())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error003_LoginName"));
				}
				// check độ dài trong khoảng
				int maxLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MaxLength_LoginName"));
				int minLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MinLength_LoginName"));
				int currentLengh = userInfor.getLoginName().length();
				if (currentLengh < minLength || currentLengh > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error007_LoginName"));
				}
				// check định dạng
				if (CommonUtil.checkAccountFormat(userInfor.getLoginName())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error019_LoginName"));
				}
			}
			// validate group
			{
				// check ko chọn
				if (0 == userInfor.getGroupId()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error002_Group"));
				}
				// check ko tồn tại
				if (!new MstGroupLogicImpl().checkExistedGroupId(userInfor.getGroupId())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error004_Group"));
				}
			}
			// validate full name
			{
				// check ko nhập
				if (userInfor.getFullName().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_FullName"));
				}
				// check max length
				int maxLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MaxLength_FullName"));
				int currentLength = userInfor.getFullName().length();
				if (currentLength > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error006_FullName"));
				}
			}
			// validate full name kana
			{
				// check max length
				int maxLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MaxLength_FullNameKana"));
				int currentLength = userInfor.getFullNameKana().length();
				if (currentLength > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error006_FullNameKana"));
				}
				// check kana
//				ErrMsg.add(MessageErrorProperties.getValue("Error009_FullNameKana"));
			}
			// validate birthday
			{
				// check ngày ko hợp lệ
				if (null == userInfor.getBirthDay()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error0011_BirthDay"));
				}
			}
			// validate email
			{
				// check ko nhập
				if (userInfor.getEmail().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_Email"));
				}
				// check đã tồn tại
				if (new TblUserLogicImpl().checkExistedEmail(userInfor.getUserId(), userInfor.getEmail())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error003_Email"));
				}
				// check sai format
//				ErrMsg.add(MessageErrorProperties.getValue("Error005_Email"));
				// check max length
				int maxLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MaxLength_Email"));
				int currentLength = userInfor.getEmail().length();
				if (currentLength > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error006_Email"));
				}
			}
			// validate tel
			{
				// check ko nhập
				if (userInfor.getTel().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_Tel"));
				}
				// check sai format
//				ErrMsg.add(MessageErrorProperties.getValue("Error005_Tel"));
				// check maxlength
				// check max length
				int maxLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MaxLength_Tel"));
				int currentLength = userInfor.getTel().length();
				if (currentLength > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error006_Tel"));
				}
			}
			// validate pass
			{
				// check ko nhập
				if (userInfor.getPass().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_Password"));
				}
				// check dộ dài trong khoảng
				int maxLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MaxLength_Password"));
				int minLength = CommonUtil.convertStrToInt(ConfigProperties.getValue("MinLength_Password"));
				int currentLengh = userInfor.getPass().length();
				if (currentLengh < minLength || currentLengh > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error007_Password"));
				}
				// check ký tự 1 byte
//				ErrMsg.add(MessageErrorProperties.getValue("Error008_Password"));
			}
			// validate repass
			{
				// check repass ko đúng
				if (!userInfor.getRepass().equals(userInfor.getPass())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error017_RePass"));
				}
			}
			// validate japan zone
			if (!userInfor.getCodeLevel().equalsIgnoreCase("N0")) {
				// đã select code level
				
				// thì
				// validate japan level
				{
					// check ko tồn tại
					if (!new MstJapanLogicImpl().checkExistedCodeLevel(userInfor.getCodeLevel())) {
						listErrMsg.add(MessageErrorProperties.getValue("Error004_JapanLevel"));
					}
				}
				// validate start date
				{
					// check ngày ko hợp lệ
					if (null == userInfor.getStartDate()) {
						listErrMsg.add(MessageErrorProperties.getValue("Error011_StartDate"));
					}
				}
				// validate end date
				{
					// check ngày ko hợp lệ
					if (null == userInfor.getEndDate()) {
						listErrMsg.add(MessageErrorProperties.getValue("Error011_EndDate"));
					}
					// check ngày hết hạn nhỏ hơn ngày cấp
					if (userInfor.getEndDate().compareTo(userInfor.getStartDate()) < 1) {
						listErrMsg.add(MessageErrorProperties.getValue("Error012_EndDate"));
					}
				}
				// validate total
				{
					// check ko nhập
//					ErrMsg.add(MessageErrorProperties.getValue("Error001_Total"));
					// check là số haffsize
					if (!CommonUtil.checkHalfSizeNumber(userInfor.getTotal())) {
						listErrMsg.add(MessageErrorProperties.getValue("Error018_Total"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

		return listErrMsg;
	}

}
