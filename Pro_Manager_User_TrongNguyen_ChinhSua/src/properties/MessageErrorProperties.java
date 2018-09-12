/**
 * Copyright(C) 2018 	Luvina
 * MessageErrorProperties.java, Aug 15, 2018, TrongNguyen
 */
package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utils.ConstantUtil;

/**
 * @author TrongNguyen
 *
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MessageErrorProperties {
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
			InputStream input = DatabaseProperties.class.getClassLoader()
					.getResourceAsStream(ConstantUtil.MESSAGE_ERROR);
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
	 * @param key
	 *            tÃªn biáº¿n cáº§n láº¥y
	 * @return value tÆ°Æ¡ng á»©ng vá»›i key
	 * @throws IOException
	 */
	public static String getValue(String key) throws Exception {
		return keyValuePartners.get(key);
	}
}
