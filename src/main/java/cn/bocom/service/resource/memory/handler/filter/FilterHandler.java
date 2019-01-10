package cn.bocom.service.resource.memory.handler.filter;

import java.util.List;
import java.util.Map;

public interface FilterHandler {

	public default List<Map<String, Object>> notnull(String col, String params, List<Map<String, Object>> data){
		return null;
	}	

	public default List<Map<String, Object>> date(String col, String params, List<Map<String, Object>> data){
		return null;
	}
	
	public default List<Map<String, Object>> content(String col, String params, List<Map<String, Object>> data){
		return null;
	}
	
}
