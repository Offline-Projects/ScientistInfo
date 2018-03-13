package com.scientist.vo;

public class SummaryResponse {
 private int statusCode;
 private long numFound;
 private int queryTime;
 private String statusMsg;
public int getStatusCode() {
	return statusCode;
}
public void setStatusCode(int statusCode) {
	this.statusCode = statusCode;
}
public long getNumFound() {
	return numFound;
}
public void setNumFound(long numFound) {
	this.numFound = numFound;
}
public int getQueryTime() {
	return queryTime;
}
public void setQueryTime(int queryTime) {
	this.queryTime = queryTime;
}
public String getStatusMsg() {
	return statusMsg;
}
public void setStatusMsg(String statusMsg) {
	this.statusMsg = statusMsg;
}
 
}
