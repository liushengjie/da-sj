/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.mapper
 * @author: liushengjie
 * @date: 2018年8月28日 下午1:23:36
 */
package cn.bocom.mapper.business;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.bocom.entity.DsParam;

/**
 * @ClassName: MysqlHandlerMapper
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月28日 下午1:23:36
 */
public interface MysqlHandlerMapper {

    List findTables(DsParam ds);
    
    List preloadData(@Param("tableName")String tableName, @Param("limit")String limit);
    
    List preloadSQLData(@Param("sql")String sql, @Param("limit")String limit);
    
    int findCountByTable(@Param("tableName")String tableName);

}
