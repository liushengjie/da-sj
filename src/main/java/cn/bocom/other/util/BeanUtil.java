package cn.bocom.other.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bocom.entity.DataCol;

public class BeanUtil<T> {

	public List<T> mapToObject(List<Map> list, Class<T> beanClass) throws Exception{
		List<T> reslut = new ArrayList<T>();
		for(Map map:list){
			T obj = beanClass.newInstance();  
			Field[] fields = obj.getClass().getDeclaredFields();   
		    for (Field field : fields) {       
		        field.setAccessible(true);    
		        field.set(obj, map.get(field.getName()));   
		    } 
		    reslut.add(obj);
		}
		return reslut;
	}
}
