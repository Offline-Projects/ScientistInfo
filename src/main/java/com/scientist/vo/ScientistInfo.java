package com.scientist.vo;

public class ScientistInfo {
	private String order;
	private String name;
	private String country;
	private String company;
	private String rank;
	private String docNum;
	private String quotedNum;
	private String indexH;
	private String contact;
	private String personalPage;
	private String domain;
	private String longitude;//经度
	private String latitude;
	private String locationCode;
	private int value;//echart map value
	
	/**
	 * no-argument constructor.
	 */
	public ScientistInfo(){
		
	}
	
	public ScientistInfo(String order, String name, String country, String company, String rank,
			String docNum, String quotedNum, String indexH, String contact, String personalPage,
			String domain, String longitude, String latitude, String locationCode, int value){
		this.order = order;
		this.name = name;
		this.country = country;
		this.company = company;
		this.rank = rank;
		this.docNum = docNum;
		this.quotedNum = quotedNum;
		this.indexH = indexH;
		this.contact = contact;
		this.personalPage = personalPage;
		this.domain = domain;
		this.longitude = longitude;
		this.latitude = latitude;
		this.locationCode = locationCode;
		this.value = value;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getQuotedNum() {
		return quotedNum;
	}
	public void setQuotedNum(String quotedNum) {
		this.quotedNum = quotedNum;
	}
	public String getIndexH() {
		return indexH;
	}
	public void setIndexH(String indexH) {
		this.indexH = indexH;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPersonalPage() {
		return personalPage;
	}
	public void setPersonalPage(String personalPage) {
		this.personalPage = personalPage;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public int getValue() {
		return this.value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}
