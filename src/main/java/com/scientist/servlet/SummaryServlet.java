package com.scientist.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
import org.apache.solr.common.SolrDocumentList;

import com.scientist.util.ServletUtils;

import com.scientist.vo.SummaryResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@WebServlet("/getSummary")
public class SummaryServlet extends HttpServlet {

	private static final long serialVersionUID = 3552448024509013844L;
	Logger logger = Logger.getLogger(SummaryServlet.class.getName());
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// set response configuration
		long start = System.currentTimeMillis();
		resp.setContentType("application/json;charset=utf-8");
		// 跨源请求
		resp.setHeader("Access-Control-Allow-Origin", "*");
		SummaryResponse response = new SummaryResponse();
		PrintWriter pw = resp.getWriter();

		// get the body of request which contains query info.
		InputStream bodyStream = req.getInputStream();
		BufferedReader bfr = new BufferedReader(new InputStreamReader(bodyStream, "UTF-8"));
		String bodyStr = "";
		String line = "";
		while ((line = bfr.readLine()) != null) {
			bodyStr += line;
		}
		logger.info(bodyStr);
		try {
			// create SOLR client object.
			SolrClient solrClient = ServletUtils.getSolrClient();
			solrClient.ping();
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
		
			response.setNumFound(results.getNumFound());
			response.setQueryTime(solrResponse.getQTime());
			response.setStatusCode(0);
			response.setStatusMsg("success");
			log(JSONObject.fromObject(response).toString());
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
