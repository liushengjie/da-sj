package cn.bocom.r_service.datasource.origin;

import cn.bocom.entity.DataSource;
import cn.bocom.r_entity.datasource.OriginEntity;

/**
 * 插件接口
 * @author liushengjie
 * @version $Id: OriginPlugin.java, v 0.1 2019年1月11日 下午2:22:06 liushengjie Exp $
 */
public interface OriginPlugin <T extends OriginEntity>{
    public DataSource convertDataSource(T originObj, int typeCode);
}
