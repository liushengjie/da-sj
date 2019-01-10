package cn.bocom.service.resource.jdbc.handler.column;

public interface ColumnHandler {

	public default String substr(String col, String params){
		return null;
	}

	public default String split(String col, String params){
		return null;
	}
	
}
