package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.ResDict;
import tk.mybatis.mapper.common.Mapper;

public interface ResDictMapper extends Mapper<ResDict> {
    public List<ResDict> queryResDict(ResDict resDict);

    public int addResDict(ResDict resDict);

    public int updateResDict(ResDict resDict);

    public int delResDict(String id);

}
