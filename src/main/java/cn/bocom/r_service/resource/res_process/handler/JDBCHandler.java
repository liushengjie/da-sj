package cn.bocom.r_service.resource.res_process.handler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.bocom.other.common.SjException;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.process.ProcessEntity;
import cn.bocom.r_entity.resource.Resource;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_service.datasource.origin.DataSourceOrigin;
import cn.bocom.r_service.process.IProcess;
import cn.bocom.r_service.process.ProcessUtil;
import cn.bocom.r_service.resource.res_process.IHandler;

/**
 * JDBC处理器
 * 
 * @author liushengjie
 * @version $Id: JDBCHandler.java, v 0.1 2019年1月18日 下午1:03:33 liushengjie Exp $
 */
public class JDBCHandler implements IHandler<String> {

    private static Logger logger = LoggerFactory.getLogger(JDBCHandler.class);
    
    @Autowired
    private DataSourceOrigin datasourceOrigin;

    @Override
    public String handlerCol(int datasourceType, List<ProcessEntity> processors) throws SjException{
        IProcess<?> processorObj = ProcessUtil.processObj(datasourceType);
        String result = processors.stream().filter(p -> p.getProcessType().equals("col"))
                .collect(Collectors.groupingBy(ProcessEntity::getProcessCol)).values().stream()
                .map(value -> {
                    String retColStr = "";
                    for (ProcessEntity p : value) {
                        try {
                            Method m = processorObj.getClass().getDeclaredMethod(p.getProcessName(),
                                    String.class, String.class);
                            retColStr = (String) m.invoke(processorObj,retColStr, p.getParams());
                        } catch (Exception e) {
                            logger.error("handler cols str throws Exception ===> ", e);
                            throw new SjException("handler cols str throws Exception");
                        }
                    }
                    return retColStr;
                }).reduce((s1, s2) -> {
                    return s1.concat(",").concat(s2);
                }).orElse("");

        return result;
    }

    @Override
    public String handlerRow(int datasourceType, List<ProcessEntity> processors) {
        IProcess<?> processorObj = ProcessUtil.processObj(datasourceType);
        String result = processors.stream().filter(p -> p.getProcessType().equals("row"))
                .map(value -> {
                    try {
                        Method m = processorObj.getClass().getDeclaredMethod(value.getProcessName(),
                                String.class, String.class);
                        return  (String) m.invoke(processorObj,"", value.getParams());
                    } catch (Exception e) {
                        logger.error("handler row str throws Exception ===> ", e);
                        throw new SjException("handler row str throws Exception");
                    }
                }).reduce((s1, s2) -> {
                    return s1.concat(" and ").concat(s2);
                }).orElse("");

        return result;
    }
    

    @Override
    public String changeColType(String col, String type) {
        return null;
    }

    @Override
    public String pretreadment(Resource resource, boolean isCache) {
        DataSource ds = datasourceOrigin.selectDataSourceById(resource.getResourceData().getDsId());
        List<ResourceCol> res_col = resource.getResourceCols();
        res_col.forEach(col -> {
            StringBuffer sb = new StringBuffer();
            String colstr = col.getCol();
            if(col.getColProcessor() != null && col.getColProcessor().size() > 0) {
                sb.append(handlerCol(Integer.valueOf(ds.getType()), col.getColProcessor()));
            }
            
            //sb.append(changeColType())
        });
        return null;
    }

    @Override
    public List<Map<String, Object>> readData(Resource resource) {
        
        return null;
    }
    
    public static void main(String[] args) {
        JDBCHandler jdbc = new JDBCHandler();
        int type = 0;
        List<ProcessEntity> list = Lists.newArrayList();
        
        ProcessEntity p = new ProcessEntity();
        p.setProcessName("notNull");
        p.setProcessType("row");
        p.setProcessCol("a");
        p.setParams("{'col':'a'}");
        
        list.add(p);
        
        ProcessEntity p1 = new ProcessEntity();
        p1.setProcessName("substr");
        p1.setProcessType("col");
        p1.setProcessCol("a");
        p1.setParams("{'col':'a'}");
        
        list.add(p1);
        
        ProcessEntity p2 = new ProcessEntity();
        p2.setProcessName("notNull");
        p2.setProcessType("row");
        p2.setProcessCol("b");
        p2.setParams("{'col':'b'}");
        
        list.add(p2);
        
        logger.info(jdbc.handlerRow(type, list));
        
    }



}
