package cn.bocom.service.resource.memory.handler.column;

import java.util.List;
import java.util.Map;

public interface ColumnHandler {

	public default List<Map<String, Object>> substr(String col, String colNew, String params, List<Map<String, Object>> data){
		return null;
	}

	public default List<Map<String, Object>> split(String col, String colNew, String params, List<Map<String, Object>> data){
		return null;
	}
	
}
