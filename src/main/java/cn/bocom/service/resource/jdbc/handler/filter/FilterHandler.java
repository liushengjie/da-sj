package cn.bocom.service.resource.jdbc.handler.filter;

public interface FilterHandler {

	public default String notnull(String col, String params){
		return null;
	}	

	public default String date(String col, String params){
		return null;
	}
	
	public default String content(String col, String params){
		return null;
	}
	
}
