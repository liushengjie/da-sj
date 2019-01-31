package cn.bocom.r_service.datasource.plugin;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.datasource.form.Excel;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;
import cn.bocom.r_service.datasource.DataSourcePlugin;

/**
 * excel 插件类
 * @author liushengjie
 * @version $Id: ExcelPlugin.java, v 0.1 2019年1月11日 下午2:20:25 liushengjie Exp $
 */
public class ExcelPlugin implements DataSourcePlugin<Excel>{
    private static Logger logger = LoggerFactory.getLogger(ExcelPlugin.class);
    
    private DataSource datasource ;

    public ExcelPlugin() {
        super();
    }

    public ExcelPlugin(DataSource datasource) {
        super();
        this.datasource = datasource;
    }

    @Override
    public DataSource convertDataSource(Excel originObj, int typeCode) {
        return null;
    }

    @Override
    public Excel converOrigin(DataSource datasource) {
        return null;
    }

    @Override
    public List<TableInfo> showTablesInfo(String datasourceId) {
        return null;
    }

    @Override
    public List<ColInfo> showColsInfo(String datasourceId, String table) {
        return null;
    }

    @Override
    public boolean connect(DataSource datasource) {
        return true;
    }

    @Override
    public int tableCount(String table) {
        return 0;
    }

    @Override
    public List<Map<String, Object>> loadData(String table, String limit) {
        return null;
    }

    @Override
    public ResourceData convertToResData(String resourceId, String datasourceId, TableInfo tbi) {
        return null;
    }

    @Override
    public ResourceCol convertToResCol(String resourceId, ColInfo col) {
        return null;
    }
}
