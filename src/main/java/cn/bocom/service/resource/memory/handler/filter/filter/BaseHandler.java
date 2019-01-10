package cn.bocom.service.resource.memory.handler.filter.filter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import cn.bocom.other.util.DateUtil;
import cn.bocom.service.resource.memory.handler.filter.FilterHandler;

public interface BaseHandler extends FilterHandler {

	@Override
	public default List<Map<String, Object>> notnull(String col, String params, List<Map<String, Object>> data){
		
		List<Map<String, Object>> dataNew = new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> map_:data){
			for (Map.Entry<String, Object> entry : map_.entrySet()) { 
				if(col.equals(entry.getKey())){
					if(entry.getValue()!=null && !"".equals(entry.getValue())){
						dataNew.add(map_);
					}
				}
			}
		}
		
		return dataNew;
	}	

	@Override
	public default List<Map<String, Object>> date(String col, String params, List<Map<String, Object>> data){
		
		List<Map<String, Object>> dataNew = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> dataNew_ = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = JSONObject.parseObject(params);
		
		if(map.get("begin")!=null){
			String begin = map.get("begin").toString();
			for(Map<String, Object> map_:data){
				for (Map.Entry<String, Object> entry : map_.entrySet()) { 
					if(col.equals(entry.getKey())){
							Date date1 = DateUtil.strToDate(entry.getValue().toString());
							Date date2 = DateUtil.strToDate(begin);
							if(date1.compareTo(date2)>0){
								dataNew.add(map_);
							}
					}
				}
			}
		}else {
			dataNew = data;
		}
		
		if(map.get("end")!=null){
			String end = map.get("end").toString();
			for(Map<String, Object> map_:dataNew){
				for (Map.Entry<String, Object> entry : map_.entrySet()) { 
					if(col.equals(entry.getKey())){
						Date date1 = DateUtil.strToDate(entry.getValue().toString());
						Date date2 = DateUtil.strToDate(end);
						if(date1.compareTo(date2)<0){
							dataNew_.add(map_);
						}
					}
				}
			}
		}else {
			dataNew_ = dataNew;
		}
		
//		for(Map<String, Object> map_:data){
//			for (Map.Entry<String, Object> entry : map_.entrySet()) { 
//				if(col.equals(entry.getKey())){
//					if(map.get("begin")!=null){
//						String begin = map.get("begin").toString();
//						if(DateUtil.strToDate(entry.getValue().toString()).compareTo(DateUtil.strToDate(begin))<0){
//							data.remove(map_);
//						}
//					}
//					if(map.get("end")!=null){
//						String end = map.get("end").toString();
//						if(DateUtil.strToDate(entry.getValue().toString()).compareTo(DateUtil.strToDate(end))>0){
//							data.remove(map_);
//						}
//					}
//				}
//			}
//		}

		return dataNew_;
	}
	
	@Override
	public default List<Map<String, Object>> content(String col, String params, List<Map<String, Object>> data){
		
		List<Map<String, Object>> dataNew = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = JSONObject.parseObject(params);
		
		String equal = map.get("equal").toString();
		String content = map.get("content").toString();
		String[] contents = content.split(",");
		if(contents.length>0){					
			for(String str:contents){				
				for(Map<String, Object> map_:data){
					for (Map.Entry<String, Object> entry : map_.entrySet()) { 
						if(col.equals(entry.getKey())){
							if(("yes".equals(equal) && str.equals(entry.getValue())) 
									|| ("no".equals(equal) && !str.equals(entry.getValue()))){
								dataNew.add(map_);
							}
						}
					}
				}
			}
		}
		
		return dataNew;
	}

}
