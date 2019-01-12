package cn.bocom.r_service.datasource.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bocom.r_entity.datasource.DataSource;
import cn.bocom.r_entity.datasource.form.Excel;
import cn.bocom.r_service.datasource.OriginPlugin;

/**
 * excel 插件类
 * @author liushengjie
 * @version $Id: ExcelPlugin.java, v 0.1 2019年1月11日 下午2:20:25 liushengjie Exp $
 */
public class ExcelPlugin implements OriginPlugin<Excel>{
    private static Logger logger = LoggerFactory.getLogger(ExcelPlugin.class);

    @Override
    public DataSource convertDataSource(Excel originObj, int typeCode) {
        return null;
    }

    @Override
    public Excel converOrigin(DataSource datasource) {
        return null;
    }

    

}
