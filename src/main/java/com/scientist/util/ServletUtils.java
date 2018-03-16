package com.scientist.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class ServletUtils {
	private static String COMMA = ":";
	private static final Logger logger = Logger.getLogger(ServletUtils.class.getName());
	private static final String PROPERTY_PATH = "C:/property/solr.properties";
	private static Properties prop = loadProperties();
	private static final String solrServerUrl = prop.getProperty("SOLR_SERVER");
	private static final String solrCroeHome = prop.getProperty("SOLR_CORE");
	public static SolrClient getSolrClient() {
		return new HttpSolrClient(solrServerUrl + "/" + solrCroeHome);
	}
	
	public static boolean isNullorEmpty(String str) {
		return str == null || str.length() == 0 ? true : false;
	}
	
	public static boolean validQuery(String str) {
		return str.indexOf(COMMA) != -1 && str.indexOf(COMMA) != 0 && str.indexOf(COMMA) != str.length() ? true : false;
	}
	
	private static Properties loadProperties() {
        Properties prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(PROPERTY_PATH));
            prop.load(in);
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE,"无法找到属性文件，详细错误信息：" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
			logger.log(Level.SEVERE,"详细错误信息：" + e.getMessage());
            e.printStackTrace();
        }
        return prop;
    }
}
