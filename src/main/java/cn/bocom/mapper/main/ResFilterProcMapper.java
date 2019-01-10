package cn.bocom.mapper.main;

import java.util.List;

import cn.bocom.entity.ResFilterProc;

import tk.mybatis.mapper.common.Mapper;

public interface ResFilterProcMapper extends Mapper<ResFilterProc> {
	
	public int saveResFilterProc(ResFilterProc resFilterProc);
	
	public List<ResFilterProc> findResFilterProc(ResFilterProc resFilterProc);
}