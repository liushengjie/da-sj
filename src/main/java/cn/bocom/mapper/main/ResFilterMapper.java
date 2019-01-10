package cn.bocom.mapper.main;

import cn.bocom.entity.ResFilter;

import java.util.List;

import tk.mybatis.mapper.common.Mapper;

public interface ResFilterMapper extends Mapper<ResFilter> {
	
	public int saveResFilter(ResFilter resFilter);
	
	public List<ResFilter> findResFilter(ResFilter resFilter);
}