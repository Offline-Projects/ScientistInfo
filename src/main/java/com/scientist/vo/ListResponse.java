package com.scientist.vo;

import java.util.List;

public class ListResponse {
	private int statusCode;
	private List<ScientistInfo> scientistList;
	private String statusMsg;
	private long numFound;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public List<ScientistInfo> getScientistList() {
		return scientistList;
	}
	public void setScientistList(List<ScientistInfo> scientistList) {
		this.scientistList = scientistList;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public long getNumFound() {
		return numFound;
	}
	public void setNumFound(long numFound) {
		this.numFound = numFound;
	}
	
}
