package cn.bocom.r_service.resource.res_transform;

import cn.bocom.r_entity.datasource.ColInfo;
import cn.bocom.r_entity.datasource.TableInfo;
import cn.bocom.r_entity.resource.ResourceCol;
import cn.bocom.r_entity.resource.ResourceData;

/**
 * 资源协议层
 * @author liushengjie
 * @version $Id: ResourceProtocol.java, v 0.1 2019年1月12日 下午4:18:52 liushengjie Exp $
 */
public interface ResourceProtocol {
    /**
     * 协议：将源Table对象转换为资源数据对象
     * @param tbi
     * @return
     */
    public ResourceData convertToResData(String resourceId, String datasourceId, TableInfo tbi);
    
    /**
     * 协议：将列对象转换为资源列对象
     * @param col
     * @return
     */
    public ResourceCol convertToResCol(String resourceId, ColInfo col);
}
