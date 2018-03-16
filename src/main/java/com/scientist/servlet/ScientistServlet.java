package com.scientist.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;

import com.scientist.util.ServletUtils;
import com.scientist.vo.MapResponse;
import com.scientist.vo.ScientistInfo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@WebServlet("/getScientistInfo")
public class ScientistServlet extends HttpServlet {
	private static final String COMMA = ":";
	private static final String TILDE = "~";
	private static final String COLON = ",";
	private static final String PRE_BRACKET = "{";
	private static final String POST_BRACKET = "}";

	private static final Logger logger = Logger.getLogger(ScientistServlet.class.getName());
	private static final long serialVersionUID = -2579884687198191080L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// set response configuration
		long start = System.currentTimeMillis();
		resp.setContentType("application/json;charset=utf-8");
		//跨源请求
		resp.setHeader("Access-Control-Allow-Origin", "*");
		MapResponse response = new MapResponse();
		PrintWriter pw = resp.getWriter();

		// get the body of request which contains query info.
		InputStream bodyStream = req.getInputStream();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(bodyStream, "UTF-8"));
		String bodyStr = "";
		String line = "";
		while ((line = bfr.readLine()) != null) {
			bodyStr += line;
		}
		log(bodyStr);
		try {
			//create SOLR client object.
			SolrClient solrClient = ServletUtils.getSolrClient();
			SolrQuery params = new SolrQuery();
			
			//set SOLR query parameters according to the request JSON.
			JSONObject body = JSONObject.fromObject(bodyStr);
			
			if (body.containsKey("query") && !ServletUtils.isNullorEmpty(body.getString("query"))
					&& ServletUtils.validQuery(body.getString("query"))) {
				// here split query string to identify if text search add single/double quote before search params
				params.set("q", body.getString("query"));
			}
			if (body.containsKey("filter") && !ServletUtils.isNullorEmpty(body.getString("filter"))) {
				params.set("fl", body.getString("filter"));
			}
			if (body.containsKey("start") && !ServletUtils.isNullorEmpty(body.getString("start"))) {
				params.set("start", body.getString("start"));
			}
			if (body.containsKey("rows") && !ServletUtils.isNullorEmpty(body.getString("rows"))) {
				params.set("rows", body.getString("rows"));
			}
			if (body.containsKey("sort") && !ServletUtils.isNullorEmpty("sort")) {
				params.set("sort", body.getString("sort"));
			}

			QueryResponse solrResponse = solrClient.query(params);
			SolrDocumentList results = solrResponse.getResults();
			Map<String, ScientistInfo> echartMap = new HashMap<String, ScientistInfo>();
			StringBuilder geoPosition = new StringBuilder();
			StringBuilder echartValue = new StringBuilder();
			
			for (int i = 0; i < results.size(); i++) {
				
				SolrDocument element = results.get(i);
				String order = (String)element.get("序号");
				String name = (String)element.get("姓名");
				String country = (String)element.get("国家");
				String company = (String)element.get("单位");
				String rank = (String)element.get("职称");
				String docNum = (String)element.get("总发文");
				String quotedNum = (String)element.get("总引用");
				String indexH = (String)element.get("H指数");
				String contact = (String)element.get("联系方式");
				String personalPage = (String)element.get("个人主页");
				String domain = (String)element.get("领域");
				String longitude = (String)element.get("经度");
				String latitude = (String)element.get("纬度");
				String locationCode = (String)element.get("位置代码");
				
				ScientistInfo infoForMap = new ScientistInfo(order, name, country, company, rank, 
						docNum, quotedNum, indexH, contact, personalPage, domain, longitude, latitude, locationCode, calculateValue(docNum));

				if (echartMap.containsKey(element.get("位置代码"))) {
					ScientistInfo scientistForMap = echartMap.get(element.get("位置代码"));
					scientistForMap.setOrder(scientistForMap.getOrder() + TILDE + (String) element.get("序号"));
					scientistForMap.setName(scientistForMap.getName() + TILDE + (String) element.get("姓名"));
					scientistForMap.setCountry(scientistForMap.getCountry() + TILDE + (String) element.get("国家"));
					scientistForMap.setRank(scientistForMap.getRank() + TILDE + (String) element.get("职称"));
					scientistForMap.setDocNum(scientistForMap.getDocNum() + TILDE + (String) element.get("总发文"));
					scientistForMap
							.setQuotedNum(scientistForMap.getQuotedNum() + TILDE + (String) element.get("总引用"));
					scientistForMap.setIndexH(scientistForMap.getIndexH() + TILDE + (String) element.get("H指数"));
					scientistForMap.setContact(scientistForMap.getContact() + TILDE + (String) element.get("联系方式"));
					scientistForMap
							.setPersonalPage(scientistForMap.getPersonalPage() + TILDE + (String) element.get("个人主页"));
					scientistForMap.setDomain(scientistForMap.getDomain() + TILDE + (String) element.get("领域"));
					scientistForMap.setValue(scientistForMap.getValue() + calculateValue((String) element.get("总发文")));
				} else {
					echartMap.put(infoForMap.getLocationCode(), infoForMap);
				}

			}
			geoPosition.append(PRE_BRACKET);
			echartValue.append("[");
			TreeSet<String> keys = new TreeSet<String>(echartMap.keySet());
			ScientistInfo scientist;
			for (String key : keys) {
				scientist = echartMap.get(key);
				geoPosition.append(key).append(COMMA).append(PRE_BRACKET);
				geoPosition.append("'latitude'").append(COMMA).append(scientist.getLongitude());
				geoPosition.append(COLON);
				geoPosition.append("'longitude'").append(COMMA).append(scientist.getLatitude());
				geoPosition.append(POST_BRACKET);
				geoPosition.append(COLON);
				echartValue.append(PRE_BRACKET).append("\"code\"").append(COMMA).append("\"").append(key).append("\"").append(COLON);
				echartValue.append("\"name\"").append(COMMA).append("\"").append(scientist.getCompany().split(TILDE)[0]).append("\"").append(COLON);
				echartValue.append("\"value\"").append(COMMA).append(scientist.getValue()).append(POST_BRACKET).append(COLON);
			}
			geoPosition.deleteCharAt(geoPosition.lastIndexOf(COLON));
			geoPosition.append(POST_BRACKET);
			echartValue.deleteCharAt(echartValue.lastIndexOf(COLON));
			echartValue.append("]");
			
			response.setGeoPosition(geoPosition.toString());
			response.setEchartValue(echartValue.toString());
			response.setStatusCode(0);
			response.setStatusMsg("success");
			
			pw.write(JSONObject.fromObject(response).toString());
			pw.flush();

		} catch (JSONException e) {
			response.setStatusCode(1);
			response.setStatusMsg(e.getMessage());
			logger.log(Level.SEVERE,"详细错误信息：" + e.getMessage());
			pw.write(JSONObject.fromObject(response).toString());
		} catch (SolrServerException e) {
			response.setStatusCode(1);
			response.setStatusMsg(e.getMessage());
			logger.log(Level.SEVERE,"详细错误信息：" + e.getMessage());
			pw.write(JSONObject.fromObject(response).toString());
		}

		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	//if calculate function changes, plz update this method 
	private int calculateValue(String docNum) {
		return Integer.parseInt(docNum);
	}
}
