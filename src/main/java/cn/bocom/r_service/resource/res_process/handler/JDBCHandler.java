package cn.bocom.r_service.resource.res_process.handler;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.stringtemplate.v4.ST;

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

    public static final String SQL = "select <col> from <table> where  1=1 <where>";

    @Autowired
    private DataSourceOrigin datasourceOrigin;

    @Override
    public String handlerCol(int datasourceType, String col, List<ProcessEntity> processors)
            throws SjException {
        IProcess<?> processorObj = ProcessUtil.processObj(datasourceType);
        String result = "";
        for (ProcessEntity p : processors) {
            try {
                Method m = processorObj.getClass().getDeclaredMethod(p.getProcessName(),
                        String.class, String.class, String.class);
                result = (String) m.invoke(processorObj, col, result, p.getParams());
            } catch (Exception e) {
                logger.error("handler cols str throws Exception ===> ", e);
                throw new SjException("handler cols str throws Exception");
            }
        }

        return result;
    }

    @Override
    public String handlerRow(int datasourceType, List<ProcessEntity> processors) {
        IProcess<?> processorObj = ProcessUtil.processObj(datasourceType);
        String result = processors.stream().map(value -> {
            try {
                Method m = processorObj.getClass().getDeclaredMethod(value.getProcessName(),
                        String.class, String.class);
                return (String) m.invoke(processorObj, value.getProcessCol(), "",
                        value.getParams());
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
    @SuppressWarnings("unchecked")
    public String pretreadment(Resource resource, boolean isCache) {

        DataSource ds = datasourceOrigin.selectDataSourceById(resource.getResourceData().getDsId());
        IProcess<String> processorObj =
                (IProcess<String>) ProcessUtil.processObj(Integer.valueOf(ds.getType()));

        List<ResourceCol> res_col = resource.getResourceCols();
        String col_str = res_col.stream().map(c -> {
            String col = c.getCol();

            if (c.getColProcessor() != null && c.getColProcessor().size() > 0) {
                col = handlerCol(Integer.valueOf(ds.getType()), col, c.getColProcessor());
            } else {
                col = c.getCol();
            }
            col = processorObj.changeType(col, "", c.getChangeType());
            if (isCache) col = processorObj.setAlias(col, "", c.getColCache());
            return col;
            
        }).reduce((s1, s2) -> {
            return s1.concat(" , ").concat(s2);
        }).orElse("");

        List<ProcessEntity> rowProcessors = resource.getResourceData().getDataProcessor();
        String where_str = "";
        if (rowProcessors != null && rowProcessors.size() > 0) {
            where_str = handlerRow(Integer.valueOf(ds.getType()), rowProcessors);
        }

        ST sql = new ST(SQL);
        sql.add("col", col_str);
        sql.add("table",
                isCache
                        ? resource.getResourceData().getTableName()
                        : resource.getResourceData().getTableName() + " as "
                        + resource.getResourceBody().getCacheTable());
        sql.add("where", where_str);

        return sql.toString();
    }

    @Override
    public List<Map<String, Object>> readData(Resource resource) {

        return null;
    }

    public static void main(String[] args) {
        JDBCHandler jdbc = new JDBCHandler();

    }



}
