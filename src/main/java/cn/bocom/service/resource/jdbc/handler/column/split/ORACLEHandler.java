package cn.bocom.service.resource.jdbc.handler.column.split;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

public class ORACLEHandler implements BaseHandler{

	@Override
	public String split(String col, String params) {
		/*
		 select substr('石家庄,保定,秦皇岛,',
             instr('石家庄,保定,秦皇岛,', ',', 1, 1) + 1,
             decode(instr('石家庄,保定,秦皇岛,', ',', 1, 2),
                    0,
                    0,
                    instr('石家庄,保定,秦皇岛,', ',', 1, 2)-instr('石家庄,保定,秦皇岛,', ',', 1, 1)-1))
 			from dual
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
		col = col + "||'" + symbol + "'";
		
		StringBuffer sb = new StringBuffer("");
		String startIndex = index==1 ? "0" : "instr("+col+", '"+symbol+"', 1, "+(index-1)+") + 1";
		String length = "decode(instr("+col+", '"+symbol+"', 1, "+index+"), "
				+ "0, "
				+ "0, "
				+ "instr("+col+", '"+symbol+"', 1, "+index+")-"+(index==1 ? "0" : "instr("+col+", '"+symbol+"', 1, "+(index-1)+")")+"-1)";
		
		sb.append("substr(").append(col).append(", ").append(startIndex).append(", ").append(length).append(")");
		
		return sb.toString();
	}

}
