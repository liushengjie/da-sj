package cn.bocom.r_service.datasource;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;

/**
 * 插件接口
 * @author liushengjie
 * @version $Id: OriginPlugin.java, v 0.1 2019年1月11日 下午2:22:06 liushengjie Exp $
 */
public interface OriginPlugin <T extends OriginEntity>{
    
    /**
     * 转换为DataSource对象，进行数据存储
     * @param originObj
     * @param typeCode
     * @return
     */
    public DataSource convertDataSource(T originObj, int typeCode);
    
    /**
     * 从DataSource对象转换为数据源表单对象
     * @param datasource
     * @return
     */
    public T converOrigin(DataSource datasource);
}
