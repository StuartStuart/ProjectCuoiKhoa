/**
 * Copyright(C) 2018 	Luvina
 * ManagerUserTrongNguyenProperties.java, Aug 14, 2018, TrongNguyen
 */
package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utils.ConstantUtil;

/**
 * lÆ°u cÃ¡c cáº·p key - value trong .properties vÃ o thuá»™c tÃ­nh (HashMap)
 * keyValuePartner
 * 
 * @author TrongNguyen
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class DatabaseProperties {
	private static HashMap<String, String> keyValuePartners;

	/**
	 * nháº­n dá»¯ liá»‡u tá»« database.properties
	 * 
	 * @throws IOException
	 */
	static {
		Properties prop = new Properties();
		try {
			// Ä‘áº¡i diá»‡n cho db
			InputStream input = DatabaseProperties.class.getClassLoader().getResourceAsStream(ConstantUtil.DATABASE);
			// Ä‘á»• dá»¯ liá»‡u vÃ o prop
			prop.load(input);
			// Ä‘á»• dá»¯ liá»‡u vÃ o keyValuePartners
			keyValuePartners = new HashMap<String, String>((Map) prop);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * tráº£ vá»� value tÆ°Æ¡ng á»©ng key
	 * 
	 * @param key tÃªn biáº¿n cáº§n láº¥y
	 * @return value tÆ°Æ¡ng á»©ng vá»›i key
	 * @throws IOException
	 */
	public static String getValue(String key) throws IOException {
		return keyValuePartners.get(key);
	}
}
