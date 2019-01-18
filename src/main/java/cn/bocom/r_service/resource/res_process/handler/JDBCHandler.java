package cn.bocom.r_service.resource.res_process.handler;

import java.util.List;
import java.util.Map;

import cn.bocom.r_entity.process.ProcessEntity;
import cn.bocom.r_service.resource.res_process.IHandler;

/**
 * JDBC处理器
 * @author liushengjie
 * @version $Id: JDBCHandler.java, v 0.1 2019年1月18日 下午1:03:33 liushengjie Exp $
 */
public class JDBCHandler implements IHandler<String>{

    @Override
    public String handlerCol(List<ProcessEntity> processors) {
        return null;
    }

    @Override
    public String handlerRow(List<ProcessEntity> processors) {
        return null;
    }

    @Override
    public List<Map<String, Object>> readData(String pre) {
        return null;
    }

}
