package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.ResData;
import tk.mybatis.mapper.common.Mapper;

public interface ResDataMapper extends Mapper<ResData> {
	
	public void saveResData(ResData resData);
	
	public List<ResData> findResData(ResData resData);
}