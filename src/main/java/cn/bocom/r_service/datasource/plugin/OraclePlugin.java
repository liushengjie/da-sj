package cn.bocom.r_service.datasource.plugin;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.datasource.form.Oracle;
import cn.bocom.r_service.datasource.OriginPlugin;

/**
 * oracle 插件类
 * @author liushengjie
 * @version $Id: OraclePlugin.java, v 0.1 2019年1月11日 下午2:20:36 liushengjie Exp $
 */
public class OraclePlugin implements OriginPlugin<Oracle>{
    private static Logger logger = LoggerFactory.getLogger(OraclePlugin.class);

    @Override
    public DataSource convertDataSource(Oracle originObj, int typeCode) {
        return null;
    }

    @Override
    public Oracle converOrigin(DataSource datasource) {
        return null;
    }

    @Override
    public List<TableInfo> showTablesInfo(DataSource datasource) {
        return null;
    }
    
}
