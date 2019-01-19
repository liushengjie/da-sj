package cn.bocom.r_service.process.impl;

import java.util.List;
import java.util.Map;

import cn.bocom.r_service.process.IProcess;

/**
 * 内存处理器
 * @author liushengjie
 * @version $Id: IgniteProcess.java, v 0.1 2019年1月17日 上午11:17:55 liushengjie Exp $
 */
public class IgniteProcess implements IProcess<String>{

    @Override
    public List<Map<String, Object>> availableFunction(String procType) {
        return null;
    }

    @Override
    public String notNull(String col, String data, String params) {
        return null;
    }

    @Override
    public String date(String col, String data, String params) {
        return null;
    }

    @Override
    public String content(String col, String data, String params) {
        return null;
    }

    @Override
    public String substr(String col, String data, String params) {
        return null;
    }

    @Override
    public String split(String col, String data, String params) {
        return null;
    }

    @Override
    public String changeType(String col, String data, String newType) {
        return null;
    }

    @Override
    public String setAlias(String col, String data, String alias) {
        return null;
    }
}
