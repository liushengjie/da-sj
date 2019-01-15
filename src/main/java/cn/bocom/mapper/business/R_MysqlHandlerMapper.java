/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.mapper
 * @author: liushengjie
 * @date: 2018年8月28日 下午1:23:36
 */
package cn.bocom.mapper.business;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.bocom.r_entity.datasource.TableInfo;

/**
 * MYSQL MAPPER
 * @author liushengjie
 * @version $Id: R_MysqlHandlerMapper.java, v 0.1 2019年1月12日 下午1:58:31 liushengjie Exp $
 */
public interface R_MysqlHandlerMapper {

    List<TableInfo> showTablesInfo(String database);
    
    List<Map<String,Object>> loadData(@Param("tableName")String tableName, @Param("limit")String limit);
    
    List preloadSQLData(@Param("sql")String sql, @Param("limit")String limit);
    
    int tableCount(@Param("tableName")String tableName);

}
