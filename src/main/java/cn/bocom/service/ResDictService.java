package cn.bocom.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.ResDict;
import cn.bocom.entity.ResDictType;
import cn.bocom.mapper.main.ResDictMapper;
import cn.bocom.mapper.main.ResDictTypeMapper;
import cn.bocom.other.util.RandomUtil;

/***
 * 
 * 资源字典服务类
 *
 */
@Component
public class ResDictService {
    private static Logger logger = LoggerFactory.getLogger(SysCodeService.class);

    @Autowired
    private ResDictMapper resDictMapper;
    @Autowired
    private ResDictTypeMapper resDictTypeMapper;

    public List<ResDict> queryResDict(ResDict resDict) {
        return resDictMapper.queryResDict(resDict);
    }

    public boolean addResDict(ResDict resDict) {
        boolean ret = false;
        resDict.setId(RandomUtil.getUUID32());
        if (resDictMapper.addResDict(resDict) > 0) ret = true;
        return ret;
    }

    public boolean updateResDict(ResDict resDict) {
        boolean ret = false;
        if (resDictMapper.updateResDict(resDict) > 0) ret = true;
        return ret;
    }

    public boolean delResDict(String id) {
        boolean ret = false;
        if (resDictMapper.delResDict(id) > 0) ret = true;
        return ret;
    }

    public List<ResDictType> queryResDictType(ResDictType resDictType) {
        return resDictTypeMapper.queryResDictType(resDictType);
    }

    public boolean addResDictType(ResDictType resDictType) {
        boolean ret = false;
        resDictType.setId(RandomUtil.getUUID32());
        if (resDictTypeMapper.addResDictType(resDictType) > 0) ret = true;
        return ret;
    }

    public boolean updateResDictType(ResDictType resDictType) {
        boolean ret = false;
        if (resDictTypeMapper.updateResDictType(resDictType) > 0) ret = true;
        return ret;
    }

    public boolean delResDictType(String id) {
        boolean ret = false;
        if (resDictTypeMapper.delResDictType(id) > 0) ret = true;
        return ret;
    }

}
