package cn.bocom.mapper.main;

import java.util.List;
import java.util.Map;

import cn.bocom.entity.Alias;
import tk.mybatis.mapper.common.Mapper;

public interface AliasMapper extends Mapper<Alias> {

    public List<Map<String, Object>> queryAlias(Alias alias);

    public int addAlias(Alias alias);

    public int updateAlias(Alias alias);

    public int delAlias(String id);
}
