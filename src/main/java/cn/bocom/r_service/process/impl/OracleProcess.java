package cn.bocom.r_service.process.impl;

import java.util.List;
import java.util.Map;

import cn.bocom.r_service.process.IProcess;

/**
 * Oracle处理器
 * @author liushengjie
 * @version $Id: OracleProcess.java, v 0.1 2019年1月17日 上午11:17:19 liushengjie Exp $
 */
public class OracleProcess implements IProcess<String>{
    
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
    public List<Map<String, Object>> availableFunction(String procType) {
        return null;
    }
}
