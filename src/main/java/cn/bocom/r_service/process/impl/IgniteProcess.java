package cn.bocom.r_service.process.impl;

import java.util.List;
import java.util.Map;

import cn.bocom.r_service.process.IProcess;

/**
 * 内存处理器
 * @author liushengjie
 * @version $Id: IgniteProcess.java, v 0.1 2019年1月17日 上午11:17:55 liushengjie Exp $
 */
public class IgniteProcess implements IProcess{

    @Override
    public List<Map<String, Object>> availableFunction(String procType) {
        return null;
    }

    @Override
    public Object notNull(Object data, String params) {
        return null;
    }

    @Override
    public Object date(Object col, String params) {
        return null;
    }

    @Override
    public Object content(Object col, String params) {
        return null;
    }

    @Override
    public Object substr(Object col, String params) {
        return null;
    }

    @Override
    public Object split(Object col, String params) {
        return null;
    }
    
}
