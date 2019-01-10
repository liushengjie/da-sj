package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.ResCol;

import tk.mybatis.mapper.common.Mapper;

public interface ResColMapper extends Mapper<ResCol> {
	
	public int saveResCol(ResCol resCol);
	
	public List<ResCol> findResCol(ResCol resCol);
}