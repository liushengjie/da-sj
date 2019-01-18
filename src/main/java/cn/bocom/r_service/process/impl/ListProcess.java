package cn.bocom.r_service.process.impl;

import java.util.List;
import java.util.Map;

import cn.bocom.r_service.process.IProcess;

/**
 * List对象处理器
 * @author liushengjie
 * @version $Id: ListProcess.java, v 0.1 2019年1月17日 上午11:17:07 liushengjie Exp $
 */
public class ListProcess implements IProcess<List<Map<String, Object>>>{
    

    @Override
    public List<Map<String, Object>> availableFunction(String procType) {
        return null;
    }

    @Override
    public List<Map<String, Object>> notNull(String col, String params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> date(String col, String params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> content(String col, String params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> substr(String col, String params) {
        return null;
    }

    @Override
    public List<Map<String, Object>> split(String col, String params) {
        return null;
    }
}
