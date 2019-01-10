package cn.bocom.entity;

import java.util.List;
import java.util.Map;

public class Node {
	private String id;
	private String name;
	private String type;
	private Map<String, Object> param;
	private List<Map<String, String>> col;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	public List<Map<String, String>> getCol() {
		return col;
	}
	public void setCol(List<Map<String, String>> col) {
		this.col = col;
	}
	

}
