/**
 * Copyright 2014 Pacific Controls Software Services LLC (PCSS). All Rights
 * Reserved.
 *
 * This software is the property of Pacific Controls Software Services LLC and
 * its suppliers. The intellectual and technical concepts contained herein are
 * proprietary to PCSS. Dissemination of this information or reproduction of
 * this material is strictly forbidden unless prior written permission is
 * obtained from Pacific Controls Software Services.
 *
 * PCSS MAKES NO REPRESENTATION OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 * WARRANTIES OF MERCHANTANILITY, FITNESS FOR A PARTICULAR PURPOSE, OR
 * NON-INFRINGMENT. PCSS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY
 * LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 */
package com.pcs.data.analyzer.util;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

/**
 * This class is responsible for ..(Short Description)
 *
 * @author pcseg199
 * @date Mar 31, 2015
 * @since galaxy-1.0.0
 */
public class YamlUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(YamlUtils.class);

	/**
	 * @param clazz
	 * @param filePath
	 * @return
	 */
	public static <T> T copyYamlFromClassPath(Class<T> clazz, String filePath) {
		Yaml yaml = new Yaml();
		FileReader fileReader = null;
		try {
			LOGGER.info("Loading file :" + filePath);
			URL url = ClassLoader.getSystemResource(filePath);
			String decode = URLDecoder.decode(url.getFile(), "UTF-8");
			fileReader = new FileReader(decode);

			return yaml.loadAs(fileReader, clazz);

		} catch (Exception ex) {
			throw new RuntimeException("Error in Loading ConfigurationFile", ex);
		} finally {
			yaml = null;
			try {
				if (fileReader != null)
					fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException("Not able to close fileReader", e);
			}
		}
	}

	/**
	 * @param clazz
	 * @param filePath
	 * @return
	 */
	public static <T> T copyYamlFromFile(Class<T> clazz, String filePath) {
		Yaml yaml = new Yaml();
		FileReader fileReader = null;
		try {
			LOGGER.info("Loading file :" + filePath);
			return yaml.loadAs(new FileInputStream(filePath), clazz);
		} catch (Exception ex) {
			throw new RuntimeException("Error in Loading ConfigurationFile", ex);
		} finally {
			yaml = null;
			try {
				if (fileReader != null)
					fileReader.close();
			} catch (IOException e) {
				throw new RuntimeException("Not able to close fileReader", e);
			}
		}
	}

}
