package cn.bocom.service.resource.jdbc.handler.filter.filter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.bocom.other.util.DBUtil;

public class ORACLEHandler implements BaseHandler{

	@Override
	public String date(String col, String params){
	
		Map<String, Object> map = JSONObject.parseObject(params);
		
		if(map.get("type")!=null){
			String type = map.get("type").toString();
			StringBuffer sb = new StringBuffer();
			if(map.get("begin")!=null){
				String begin = map.get("begin").toString();
				if(StringUtils.isNotBlank(begin)){
					sb.append(" and ").append(DBUtil.toChar(type, col)).append(" >= '").append(begin).append("'");
				}
			}
			if(map.get("end")!=null){
				String end = map.get("end").toString();
				if(StringUtils.isNotBlank(end)){
					sb.append(" and ").append(DBUtil.toChar(type, col)).append(" <= '").append(end).append("'");
				}
			}
			return sb.toString();
		}
	
		return null;
	}
	
	@Override
	public String content(String col, String params){
		
		Map<String, Object> map = JSONObject.parseObject(params);
		
		if(map.get("type")!=null){
			String type = map.get("type").toString();
			
			String equal = map.get("equal").toString();
			String content = map.get("content").toString();
			String[] contents = content.split(",");
			if(contents.length>0){
				StringBuffer sb = new StringBuffer();
				if("yes".equals(equal)){
					sb.append(" and ").append(DBUtil.toChar(type, col)).append(" in (");
				}else if("no".equals(equal)){
					sb.append(" and ").append(DBUtil.toChar(type, col)).append(" not in (");
				}
				
				for(String str:contents){
					sb.append("'").append(str).append("' ,");
				}
				return sb.toString().substring(0, sb.length()-1)+")";
			}
		}

		return null;
	}
}
