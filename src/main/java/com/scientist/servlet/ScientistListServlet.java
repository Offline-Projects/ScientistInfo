package com.scientist.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
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
import com.scientist.vo.ListResponse;
import com.scientist.vo.ScientistInfo;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@WebServlet("/getScientistList")
public class ScientistListServlet extends HttpServlet {

	private static final long serialVersionUID = 7590883676848193154L;
	private static final Logger logger = Logger.getLogger(ScientistListServlet.class.getName());
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// set response configuration
		long start = System.currentTimeMillis();
		resp.setContentType("application/json;charset=utf-8");
		// 跨源请求
		resp.setHeader("Access-Control-Allow-Origin", "*");
		ListResponse response = new ListResponse();
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
			// create SOLR client object.
			SolrClient solrClient = ServletUtils.getSolrClient();
			SolrQuery params = new SolrQuery();

			// set SOLR query parameters according to the request JSON.
			JSONObject body = JSONObject.fromObject(bodyStr);

			if (body.containsKey("query") && !ServletUtils.isNullorEmpty(body.getString("query"))
					&& ServletUtils.validQuery(body.getString("query"))) {
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
			long numFound = results.getNumFound();
			List<ScientistInfo> sourceData = new LinkedList<ScientistInfo>();
			for (int i = 0; i < results.size(); i++) {

				SolrDocument element = results.get(i);
				String order = (String) element.get("序号");
				String name = (String) element.get("姓名");
				String country = (String) element.get("国家");
				String company = (String) element.get("单位");
				String rank = (String) element.get("职称");
				String docNum = (String) element.get("总发文");
				String quotedNum = (String) element.get("总引用");
				String indexH = (String) element.get("H指数");
				String contact = (String) element.get("联系方式");
				String personalPage = (String) element.get("个人主页");
				String domain = (String) element.get("领域");
				String longitude = (String) element.get("经度");
				String latitude = (String) element.get("纬度");
				String locationCode = (String) element.get("位置代码");
				ScientistInfo infoForList = new ScientistInfo(order, name, country, company, rank, docNum, quotedNum,
						indexH, contact, personalPage, domain, longitude, latitude, locationCode,
						docNum==null?0:Integer.parseInt(docNum));

				sourceData.add(infoForList);

			}
			response.setScientistList(sourceData);
			response.setNumFound(numFound);
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

}
