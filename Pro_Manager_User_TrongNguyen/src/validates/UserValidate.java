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
import utils.ConstantUtil;

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
			if (null == userInfor.getUserId()) {
				// validate login name
				// khi là thêm ms

				if (userInfor.getLoginName().isEmpty()) {
					// check ko nhập
					listErrMsg.add(MessageErrorProperties.getValue("Error001_LoginName"));
				} else if (!userInfor.getLoginName().matches(ConstantUtil.LOGIN_NAME_REGREX)) {
					// check định dạng
					listErrMsg.add(MessageErrorProperties.getValue("Error019_LoginName"));
				} else if (userInfor.getLoginName().length() < CommonUtil
						.convertStrToInteger(ConfigProperties.getValue("MinLength_LoginName"))
						|| userInfor.getLoginName().length() > CommonUtil
								.convertStrToInteger(ConfigProperties.getValue("MaxLength_LoginName"))) {
					// check độ dài trong khoảng
					listErrMsg.add(MessageErrorProperties.getValue("Error007_LoginName"));
				} else if (new TblUserLogicImpl().checkExistedLoginName(userInfor.getUserId(),
						userInfor.getLoginName())) {
					// check đã tồn tại
					listErrMsg.add(MessageErrorProperties.getValue("Error003_LoginName"));
				}
			} /*
				 * else { userInfor.setUserId(null); }
				 */
			// validate group
			{
				// check ko chọn
				if (0 == userInfor.getGroupId()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error002_Group"));
				} else if (!new MstGroupLogicImpl().checkExistedGroupId(userInfor.getGroupId())) {
					// check ko tồn tại
					listErrMsg.add(MessageErrorProperties.getValue("Error004_Group"));
				}
			}
			// validate full name
			{
				// check ko nhập
				if (userInfor.getFullName().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_FullName"));
				} else {
					// check max length
					int maxLength = CommonUtil.convertStrToInteger(ConfigProperties.getValue("MaxLength_FullName"));
					int currentLength = userInfor.getFullName().length();
					if (currentLength > maxLength) {
						listErrMsg.add(MessageErrorProperties.getValue("Error006_FullName"));
					}
				}
			}
			// validate full name kana
			{
				// check max length
				int maxLength = CommonUtil.convertStrToInteger(ConfigProperties.getValue("MaxLength_FullNameKana"));
				int currentLength = userInfor.getFullNameKana().length();
				if (currentLength > maxLength) {
					listErrMsg.add(MessageErrorProperties.getValue("Error006_FullNameKana"));
				} else {
					// check kana
					String chuoiKana = userInfor.getFullNameKana();
					if (!chuoiKana.isEmpty() && !CommonUtil.isKanaString(chuoiKana)) {
						listErrMsg.add(MessageErrorProperties.getValue("Error009_FullNameKana"));
					}
				}
			}
			// validate category
			{
				int category = userInfor.getCategory();
				if (category > 1 || category < 0) {
					listErrMsg.add(MessageErrorProperties.getValue("ErrorCategory"));
				}
			}
			// validate birthday
			{
				// check ngày hợp lệ
				if (!CommonUtil.checkDate(userInfor.getBirthDay())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error011_BirthDay"));
				}
			}
			// validate email
			{
				// check ko nhập
				if (userInfor.getEmail().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_Email"));
				} else
				// check max length
				if (userInfor.getEmail().length() > CommonUtil
						.convertStrToInteger(ConfigProperties.getValue("MaxLength_Email"))) {
					listErrMsg.add(MessageErrorProperties.getValue("Error006_Email"));
				} else
				// check sai format
				if (!userInfor.getEmail().matches(ConstantUtil.EMAIL_REGREX)) {
					listErrMsg.add(MessageErrorProperties.getValue("Error005_Email"));
				} else
				// check đã tồn tại
				if (new TblUserLogicImpl().checkExistedEmail(userInfor.getUserId(), userInfor.getEmail())) {
					listErrMsg.add(MessageErrorProperties.getValue("Error003_Email"));
				}
			}
			// validate tel
			{
				// check ko nhập
				if (userInfor.getTel().isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_Tel"));
				} else
				// check sai format
				if (!userInfor.getTel().matches("[0-9]{1,4}-[0-9]{1,4}-[0-9]{1,4}")) {
					listErrMsg.add(MessageErrorProperties.getValue("Error005_Tel"));
				}
			}
			// validate pass
			if (null == userInfor.getUserId()) {
				// khi là thêm ms
				String errMess = validatePass(userInfor.getPass(), userInfor.getRepass());
				// check ko nhập
				if (!errMess.isEmpty()) {
					listErrMsg.add(MessageErrorProperties.getValue("Error001_Password"));
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
					if (!CommonUtil.checkDate(userInfor.getStartDate())) {
						listErrMsg.add(MessageErrorProperties.getValue("Error011_StartDate"));
					}
				}
				// validate end date
				{
					// check ngày ko hợp lệ
					if (!CommonUtil.checkDate(userInfor.getEndDate())) {
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
					if (null == userInfor.getTotal()) {
						listErrMsg.add(MessageErrorProperties.getValue("Error001_Total"));
					} else if (!CommonUtil.checkHalfSizeNumber("" + userInfor.getTotal())) {
						// check là số haffsize
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

	public String validatePass(String pass, String repass) throws Exception {
		try {
			String errMess = "";
			// check ko nhập
			if (pass.isEmpty()) {
				errMess = MessageErrorProperties.getValue("Error001_Password");
			} else {
				// check dộ dài trong khoảng
				int maxLength = CommonUtil.convertStrToInteger(ConfigProperties.getValue("MaxLength_Password"));
				int minLength = CommonUtil.convertStrToInteger(ConfigProperties.getValue("MinLength_Password"));
				int currentLengh = pass.length();
				if (currentLengh < minLength || currentLengh > maxLength) {
					errMess = MessageErrorProperties.getValue("Error007_Password");
				} else
				// check ký tự 1 byte
				if (!CommonUtil.checkOneByteString(pass)) {
					errMess = MessageErrorProperties.getValue("Error008_Password");
				} else

				// validate repass

				// khi là thêm ms

				// check repass ko đúng
				if (!repass.equals(pass)) {
					errMess = MessageErrorProperties.getValue("Error017_RePass");
				}
			}

			return errMess;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}