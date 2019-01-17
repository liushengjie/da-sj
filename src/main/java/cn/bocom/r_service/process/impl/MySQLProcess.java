package cn.bocom.r_service.process.impl;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import cn.bocom.r_service.process.IProcess;
import cn.bocom.r_service.process.ProcessUtil;

/**
 * MYSQL 数据处理器
 * @author liushengjie
 * @version $Id: MySQLProcess.java, v 0.1 2019年1月17日 上午9:35:31 liushengjie Exp $
 */
public class MySQLProcess implements IProcess{
    
    /** 可用功能名称*/
    public static List<String> availableFunction = Lists.newArrayList("CONTENTPROC","DATEPROC","NOTNULLPROC","SPLITPROC","SUBSTRPROC"); 


    @Override
    public List<Map<String, Object>> availableFunction(String procType) {
        return ProcessUtil.availableFunc(availableFunction, procType);
    }

    @Override
    public String notNull(String col, String params) {
        return null;
    }

    @Override
    public String date(String col, String params) {
        return null;
    }

    @Override
    public String content(String col, String params) {
        return null;
    }

    @Override
    public String substr(String col, String params) {
        return null;
    }

    @Override
    public String split(String col, String params) {
        return null;
    }
}
