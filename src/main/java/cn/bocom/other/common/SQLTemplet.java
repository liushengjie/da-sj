package cn.bocom.other.common;

public class SQLTemplet {
	
	public static final String INTERSECT = "select <col> from (<ft>) as <fa> <join> (<st>) as <sa> on <filter>";
	
	public static final String FILTER = "select <col> from (<ft>) where <filter>";
	
}
