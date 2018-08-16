/**
 * Copyright(C) 2018 	Luvina
 * MessageErrorProperties.java, Aug 15, 2018, LA-PM
 */
package properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import utils.ConstantUtil;

/**
 * @author LA-PM
 *
 */
public class MessageErrorProperties {
	private static HashMap<String, String> keyValuePartners;

	/**
	 * nháº­n dá»¯ liá»‡u tá»« database.properties
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void getFile() throws Exception {
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
			throw e;
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
		if (keyValuePartners == null) {
			try {
				// Ä‘á»• dá»¯ liá»‡u vÃ o HashMap keyValuePartners
				getFile();
			} catch (IOException e) {
				throw e;
			}
		}

		return keyValuePartners.get(key);
	}
}
