package com.scientist.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

public class ServletUtils {
	private static String COMMA = ":";
	//private static final String PROPERTY_PATH = "/Users/KaikaiFu/Desktop/backup/save/solr-release.properties";
	private static final String PROPERTY_PATH = "C:/property/solr.properties";
	private static Properties prop = loadProperties();
	private static final String solrServerUrl = prop.getProperty("SOLR_SERVER");//"http://www.mxonline.top/webapp/solr";
	private static final String solrCroeHome = prop.getProperty("SOLR_CORE");//"demo";
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return prop;
    }
}
