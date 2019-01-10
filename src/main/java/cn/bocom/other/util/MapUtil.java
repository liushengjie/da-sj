/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.util 
 * @author: liushengjie   
 * @date: 2018年9月19日 下午4:26:22 
 */
package cn.bocom.other.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: MapUtil
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月19日 下午4:26:22
 */
public class MapUtil {
	
	public static Map<String, Object> transformLowerCase(Map<String, Object> map) {
		Map<String, Object> resultMap = new HashMap<>();
		if (map == null || map.isEmpty()) {
			return resultMap;
		}
		Set<String> keySet = map.keySet();
		for (String key : keySet) {
			String newKey = key.toLowerCase();
			resultMap.put(newKey, map.get(key));
		}
		return resultMap;
	}
}
