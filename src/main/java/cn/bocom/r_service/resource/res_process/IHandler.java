package cn.bocom.r_service.resource.res_process;

import java.util.List;
import java.util.Map;

import cn.bocom.r_entity.process.ProcessEntity;

/**
 * 处理器接口（jdbc、list、mq ...）
 * @author liushengjie
 * @version $Id: IHandler.java, v 0.1 2019年1月18日 下午1:05:02 liushengjie Exp $
 */
public interface IHandler<T> {
    public T handlerCol(List<ProcessEntity> processors);
    public T handlerRow(List<ProcessEntity> processors);
    public List<Map<String, Object>> readData(T pre);
}
