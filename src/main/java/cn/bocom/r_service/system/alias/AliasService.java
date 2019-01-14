package cn.bocom.r_service.system.alias;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import cn.bocom.entity.Alias;
import cn.bocom.mapper.main.AliasMapper;

/**
 * 别名服务
 * @author liushengjie
 * @version $Id: AliasService.java, v 0.1 2019年1月14日 下午3:26:14 liushengjie Exp $
 */
@Component
public class AliasService {
    
    @Autowired
    private AliasMapper AliasMapper;
    
    public List<Map<String, Object>> getColAlias(String dataSourceId, String tableName) {
        Alias alias = new Alias();
        alias.setDatasource(dataSourceId);
        alias.setTablename(tableName);
        alias.setType("2");
        return queryAlias(alias);
    }
    
    public List<Map<String, Object>> getTableAlias(String dataSourceId) {
        Alias alias = new Alias();
        alias.setDatasource(dataSourceId);
        alias.setType("1");
        return queryAlias(alias);
    }
    

    public List<Map<String, Object>> queryAlias(Alias alias) {
        return AliasMapper.queryAlias(alias);
    }

    public boolean addAlias(Alias alias) {
        boolean ret = false;
        if (AliasMapper.addAlias(alias) > 0) ret = true;
        return ret;
    }

    public boolean updateAlias(Alias alias) {
        boolean ret = false;
        if (AliasMapper.updateAlias(alias) > 0) ret = true;
        return ret;
    }

    public boolean delAlias(String id) {
        boolean ret = false;
        if (AliasMapper.delAlias(id) > 0) ret = true;
        return ret;
    }

}
