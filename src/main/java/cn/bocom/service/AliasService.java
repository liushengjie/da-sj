package cn.bocom.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.Alias;
import cn.bocom.mapper.main.AliasMapper;

@Component
public class AliasService {
    @Autowired
    private AliasMapper AliasMapper;

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
