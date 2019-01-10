package cn.bocom.service.resource.memory.handler.column.split;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.bocom.service.resource.memory.handler.column.ColumnHandler;

public interface BaseHandler extends ColumnHandler {
	
	@Override
	public default List<Map<String, Object>> substr(String col, String colNew, String params, List<Map<String, Object>> data){

		List<Map<String, Object>> dataNew = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = JSONObject.parseObject(params);
		
		//自定义按位置截取
		if(map.get("begin")!=null && map.get("end")!=null){
			
			int begin = Integer.parseInt(map.get("begin").toString());
			int end = Integer.parseInt(map.get("end").toString());
			for(Map<String, Object> map_:data){
				Map<String, Object> mapNew = new HashMap<String, Object>();
				for (Map.Entry<String, Object> entry : map_.entrySet()) {
					mapNew.put(entry.getKey(), entry.getValue());
					if(col.equals(entry.getKey())){
						if(entry.getValue()!=null){
							mapNew.put(colNew, entry.getValue().toString().substring(begin-1, end));
						}
					}
				}
				dataNew.add(mapNew);
			}

			
		//左右方向按长度截取
		}else if(map.get("lr")!=null && map.get("len")!=null){
			
			String lr = map.get("lr").toString();
			int len = Integer.parseInt(map.get("len").toString());
			if("left".equals(lr)){
				for(Map<String, Object> map_:data){
					Map<String, Object> mapNew = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : map_.entrySet()) { 
						mapNew.put(entry.getKey(), entry.getValue());
						if(col.equals(entry.getKey())){
							if(entry.getValue()!=null){
								mapNew.put(colNew, entry.getValue().toString().substring(0, len));
							}
						}
					}
					dataNew.add(mapNew);
				}
			} else if("right".equals(lr)){
				for(Map<String, Object> map_:data){
					Map<String, Object> mapNew = new HashMap<String, Object>();
					for (Map.Entry<String, Object> entry : map_.entrySet()) { 
						mapNew.put(entry.getKey(), entry.getValue());
						if(col.equals(entry.getKey())){
							if(entry.getValue()!=null){
								mapNew.put(colNew, entry.getValue().toString().substring(entry.getValue().toString().length()-len));
							}
						}
					}
					dataNew.add(mapNew);
				}
			}
			
		}
		
		return dataNew;
	}

	@Override
	public default List<Map<String, Object>> split(String col, String colNew, String params, List<Map<String, Object>> data){
		
		List<Map<String, Object>> dataNew = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = JSONObject.parseObject(params);
		
		String symbol = map.get("symbol").toString();//分割符号
		int index = Integer.parseInt(map.get("index").toString());//要取第几列
		
		for(Map<String, Object> map_:data){
			Map<String, Object> mapNew = new HashMap<String, Object>();
			for (Map.Entry<String, Object> entry : map_.entrySet()) { 
				mapNew.put(entry.getKey(), entry.getValue());
				if(col.equals(entry.getKey())){
					if(entry.getValue()!=null){
						String[] str = entry.getValue().toString().split(symbol);
						if(str.length<index){
							mapNew.put(colNew, "");
						}else{
							mapNew.put(colNew, str[index-1]);
						}
						
					}
				}
			}
			dataNew.add(mapNew);
		}
		
		return dataNew;
	}

}
