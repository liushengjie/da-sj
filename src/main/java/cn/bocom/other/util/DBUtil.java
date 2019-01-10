package cn.bocom.other.util;

public class DBUtil {

	public static String changeDBType(String type){
    	String result = null;
    	switch (type.toUpperCase()) {
    	
	    	case "VARCHAR2":
	    	case "VARCHAR":
	    	case "CHAR":
	    	case "TINYTEXT":
	    	case "TEXT":
	    	case "MEDIUMTEXT":
	    	case "LONGTEXT":
	    	case "ENUM":
	    	case "SET":
	    		result = "varchar";
	    	    break;
	    		    
	    	case "BIT":
	    	case "TINYINT":
	    	case "SMALLINT":
	    	case "MEDIUMINT":
	    	case "INT":
	    	case "INT UNSIGNED":
	    	case "INT SIGNED":
	    	case "BIGINT":
	    	case "INTEGER":
	    		result = "int";
	    	    break;
	    	    
	    	case "NUMBER":
	    	case "NUMERIC":
	    	case "FLOAT":
	    	case "DOUBLE":
	    	case "DECIMAL":
	    		result = "float";
	    		break;
	    		
	    	case "DATE":
	    	case "YEAR":
	    		result = "date";
	    	    break;
	    	    
	    	case "DATETIME":
	    	case "TIMESTAMP":
	    	case "TIME":	    	
	    		result = "datetime";
	    	    break;
	    	    	    	
	    	case "TINYBLOB":
	    	case "BLOB":
	    	case "MEDIUMBLOB":
	    	case "LONGBLOB":
	    	case "CLOB":
	    		result = "byte";
	    	    break;
	    	    
	    	default:
	    		result = "varchar";
	    	    System.out.println("无对应转换类型："+type+",默认varchar");
	    	}
    	return result;
    }

	public static String toChar(String type, String col){
		if("DATE".equals(type.toUpperCase())){
			return "to_char("+ col +",'yyyy-mm-dd hh24:mi:ss')";
		}
		
		return col;		
	}
}
