/**
 * Copyright(C) 2018 Luvina
 * ConfigProperties.java, Aug 17, 2018 Stuart
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
public class ConfigProperties {
	private static HashMap<String, String> keyValuePartners;

	/**
	 * nhận dữ liệu từ Config.properties
	 * 
	 * @throws IOException
	 */
	static {
		Properties prop = new Properties();
		try {
			// Ä‘áº¡i diá»‡n cho db
			InputStream input = ConfigProperties.class.getClassLoader().getResourceAsStream(ConstantUtil.CONFIG);
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
	public static String getValue(String key) throws Exception {
		return keyValuePartners.get(key);
	}
}
