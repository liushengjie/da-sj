package cn.bocom.r_service.datasource.plugin;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.bocom.entity.DataSource;
import cn.bocom.other.common.Constant;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.r_entity.datasource.form.MySQL;
import cn.bocom.r_service.datasource.OriginPlugin;

/**
 * mysql 插件类
 * @author liushengjie
 * @version $Id: MysqlPlugin.java, v 0.1 2019年1月11日 下午2:20:10 liushengjie Exp $
 */
public class MysqlPlugin implements OriginPlugin<MySQL>{
    private static Logger logger = LoggerFactory.getLogger(MysqlPlugin.class);

    @Override
    public DataSource convertDataSource(MySQL originObj, int typeCode) {
        DataSource datasource = new DataSource();
        datasource.setId(originObj.getId()!=null?originObj.getId():RandomUtil.getRandomId(6));
        datasource.setName(originObj.getName()); // 数据源名称
        datasource.setType(String.valueOf(typeCode)); // 数据源类型
        datasource.setDriver(Constant.MYSQL_DRIVER_TPL); // 数据源驱动
        datasource.setUrl(String.format(Constant.MYSQL_URL_TPL, originObj.getIp(), originObj.getPort(),
            originObj.getDatabase())); // 数据源url
        datasource.setUsername(originObj.getUsername()); // 数据源用户名
        datasource.setPwd(originObj.getPwd()); // 数据源密码
        datasource.setXa(originObj.getXa()); // 数据源是否启用分布式事务
        datasource.setCreateUser(originObj.getCreateUser()); // 数据源人
        datasource.setCreateTime(new Date()); // 数据源创建时间
        datasource.setState(originObj.getId()!=null?String.valueOf(originObj.getStatus()):"1");
        datasource.setDataMode(String.valueOf(originObj.getModel())); // 数据源使用方式

        return datasource;
    }

    @Override
    public MySQL converDataSource(DataSource datasource) {
        return null;
    }
    
    
    
        
}
