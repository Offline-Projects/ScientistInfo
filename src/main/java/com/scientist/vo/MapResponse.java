package com.scientist.vo;

//import java.util.List;

public class MapResponse {
	private int statusCode;
	private String statusMsg;
	//private List<ScientistInfo> sourceData;
	//private String recombinedData;
	private String echartValue;
	private String geoPosition;

	/**
	 * no-argument constructor.
	 */
	public MapResponse(){}
	
	/**
	 * constructor with arguments.
	 * @param statusCode status code
	 * @param statusMsg status message
	 * @param sourceData raw data from SOLR server
	 * @param recombinedData recombined for ECHARTs
	 */
	public MapResponse(int statusCode, String statusMsg, //List<ScientistInfo> sourceData,
			String recombinedData, String echartValue, String geoPosition) {
		this.statusCode = statusCode;
		this.statusMsg = statusMsg;
		//this.sourceData = sourceData;
		//this.recombinedData = recombinedData;	
		this.echartValue = echartValue;
		this.geoPosition = geoPosition;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}


	//public String getRecombinedData() {
		//return recombinedData;
	//}

	//public void setRecombinedData(String recombinedData) {
		//this.recombinedData = recombinedData;
	//}
	public String getEchartValue() {
		return this.echartValue;
	}
	public void setEchartValue(String echartValue) {
		this.echartValue = echartValue;
	}
	public String getGeoPosition() {
		return this.geoPosition;
	}
	public void setGeoPosition(String geoPosition) {
		this.geoPosition = geoPosition;
	}
}
