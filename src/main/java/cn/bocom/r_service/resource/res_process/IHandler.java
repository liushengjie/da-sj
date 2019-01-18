package cn.bocom.r_service.resource.res_process;

import java.util.List;
import java.util.Map;

import cn.bocom.other.common.SjException;
import cn.bocom.r_entity.process.ProcessEntity;
import cn.bocom.r_entity.resource.Resource;

/**
 * 处理器接口（jdbc、list、mq ...）
 * @author liushengjie
 * @version $Id: IHandler.java, v 0.1 2019年1月18日 下午1:05:02 liushengjie Exp $
 */
public interface IHandler<T> {
    
    /**
     * 列处理器
     * @param datasourceType
     * @param processors
     * @return
     * @throws SjException
     */
    public T handlerCol(int datasourceType, List<ProcessEntity> processors) throws SjException;
    
    /**
     * 行处理器
     * @param datasourceType
     * @param processors
     * @return
     */
    public T handlerRow(int datasourceType, List<ProcessEntity> processors);
    
    public T changeColType(String col, String type);
    
    /**
     * 预处理
     * @param resource
     * @param isCache
     * @return
     */
    public T pretreadment(Resource resource, boolean isCache);
    
    /**
     * 读取数据
     * @param resource
     * @return
     */
    public List<Map<String, Object>> readData(Resource resource);
}
