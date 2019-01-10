package cn.bocom.service.resource.jdbc.handler.column.split;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.bocom.service.resource.jdbc.handler.column.ColumnHandler;

public interface BaseHandler extends ColumnHandler {
	
	@Override
	public default String substr(String col, String params){
		
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = JSONObject.parseObject(params);
		
		//自定义按位置截取
		if(map.get("begin")!=null && map.get("end")!=null){
			
			String begin = map.get("begin").toString();
			String end = map.get("end").toString();
			sb.append("substr(").append(col).append(",").append(begin).append(",").append(end).append(")");
			
		//左右方向按长度截取
		}else if(map.get("lr")!=null && map.get("len")!=null){
			
			String lr = map.get("lr").toString();
			String len = map.get("len").toString();
			if("left".equals(lr)){
				sb.append("substr(").append(col).append(",").append(1).append(",").append(len).append(")");
			} else if("right".equals(lr)){
				sb.append("substr(").append(col).append(",").append("-").append(len).append(")");
			}
			
		}else{			
			sb.append(col);				
		}
		
		return sb.toString();
	}

}
