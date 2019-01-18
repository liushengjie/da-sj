package cn.bocom.r_service.process;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import cn.bocom.other.common.SjException;

public interface IProcess<T>{
    /**
     * 获取可用的处理器
     * @return
     */
    public List<Map<String, Object>> availableFunction(String procType);
    
    /**
     * 行非空
     * @param col
     * @param params
     * @return
     */
    public T notNull(String col, T data, String params);

    /**
     * 行日期选择
     * @param col
     * @param params
     * @return
     */
    public T date(String col, T data, String params);
    
    /**
     * 行内容选择
     * @param col
     * @param params
     * @return
     */
    public T content(String col, T data, String params);
    
    /**
     * 列截取
     * @param col
     * @param params
     * @return
     */
    public T substr(String col, T data, String params);

    /**
     * 列拆分
     * @param col
     * @param params
     * @return
     */
    public T split(String col, T data, String params);
    
    
	default <E> E convertObj(String json, Class<E> c) {
    	try {
            return JSONObject.parseObject(json, c);
        } catch (Exception e) {
            throw new SjException("处理器对象转换失败",e);
        }
    }
}
