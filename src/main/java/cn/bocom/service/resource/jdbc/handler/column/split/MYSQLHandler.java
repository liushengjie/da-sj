package cn.bocom.service.resource.jdbc.handler.column.split;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class MYSQLHandler implements BaseHandler{

	@Override
	public String split(String col, String params) {
		/*
		select replace(replace(substring_index('石家庄,保定,秦皇岛',',', 1),substring_index('石家庄,保定,秦皇岛',',', 0),''),',','');
		select replace(replace(substring_index('石家庄,保定,秦皇岛',',', 2),substring_index('石家庄,保定,秦皇岛',',', 1),''),',','');
		select replace(replace(substring_index('石家庄,保定,秦皇岛',',', 3),substring_index('石家庄,保定,秦皇岛',',', 2),''),',','');
		select replace(replace(substring_index('石家庄,保定,秦皇岛',',', 4),substring_index('石家庄,保定,秦皇岛',',', 3),''),',','');
		 */
		Map<String, Object> map = null;
        try {
            map = JSONObject.parseObject(params);
        } catch (Exception e) {
            map = null;
            e.printStackTrace();
            return col;
        }
		String symbol = map.get("symbol").toString();//分割符号
		int index = Integer.parseInt(map.get("index").toString());//要取第几列
		
		StringBuffer sb = new StringBuffer("");
		sb.append("replace(");
		sb.append("replace(substring_index("+col+",'"+symbol+"', "+index+"),substring_index("+col+",'"+symbol+"', "+(index-1)+"),'')");
		sb.append(",'"+symbol+"','')");
		
		return sb.toString();
	}

}
