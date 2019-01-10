package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.ResDictType;
import tk.mybatis.mapper.common.Mapper;

public interface ResDictTypeMapper extends Mapper<ResDictType> {
    public List<ResDictType> queryResDictType(ResDictType resDictType);

    public int addResDictType(ResDictType resDictType);

    public int updateResDictType(ResDictType resDictType);

    public int delResDictType(String id);

}
