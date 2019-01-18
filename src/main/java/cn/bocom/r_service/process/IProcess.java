package cn.bocom.r_service.process;

import java.util.List;
import java.util.Map;

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
    public T notNull(String col, String params);

    /**
     * 行日期选择
     * @param col
     * @param params
     * @return
     */
    public T date(String col, String params);
    
    /**
     * 行内容选择
     * @param col
     * @param params
     * @return
     */
    public T content(String col, String params);
    
    /**
     * 列截取
     * @param col
     * @param params
     * @return
     */
    public T substr(String col, String params);

    /**
     * 列拆分
     * @param col
     * @param params
     * @return
     */
    public T split(String col, String params);
}
