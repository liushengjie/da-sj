package cn.bocom.service.resource.processor;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.DataSource;
import cn.bocom.entity.Processor;
import cn.bocom.entity.ResData;
import cn.bocom.entity.ResFilter;
import cn.bocom.entity.ResFilterProc;
import cn.bocom.service.ProcessorService;
import cn.bocom.service.datasource.DataSourceService;

@Component
public class FilterProcessor {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataSourceService dataSourceService;
	
	private ProcessorService processorService;
	
	public String generateFilterStr(ResData resData, List<ResFilter> resFilterList) {
		DataSource ds = dataSourceService.selectDsById(resData.getDsId());
		String dsName = ds.gettypeName();
		
		StringBuffer sb = new StringBuffer("");
		
		for(ResFilter resFilter:resFilterList){
			String colName = resData.getTableName()+"."+resFilter.getCol();
			List<ResFilterProc> filterList = resFilter.getProc();
				for(ResFilterProc resFilterProc:filterList){
					String filterStr = handleCol(colName,resFilterProc.getProcId(),resFilterProc.getParams(),dsName);
					if(filterStr != null){
						sb.append(filterStr);
				}				
			}
		}
		
		logger.info("filter 生成：" + sb.toString());
		return sb.toString();
	}
	
	private String handleCol(String col, String procId, String params, String dsName) {
		String filter = null;
		Processor processor = new Processor();
		processor.setId(procId);
		List<Processor> list = processorService.queryProcessor(processor);
		if(list!=null && list.size()>0){
			String procType = list.get(0).getType();
			String procCate = list.get(0).getCategory();		
			try {
				cn.bocom.service.resource.jdbc.handler.filter.FilterHandler handler = (cn.bocom.service.resource.jdbc.handler.filter.FilterHandler) Class.forName("cn.bocom.service.resource.jdbc.handler.filter."+procType.toLowerCase()+"."+dsName.toUpperCase()+"Handler").newInstance();
				Method method = cn.bocom.service.resource.jdbc.handler.filter.FilterHandler.class.getMethod(procCate, String.class, String.class);		
				filter =  (String) method.invoke(handler, col, params);		
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		return filter;
		
	}
	
	public List generateFilterData(ResData resData, List<ResFilter> resFilterList, List data) {
		DataSource ds = dataSourceService.selectDsById(resData.getDsId());
		String dsName = ds.gettypeName();

		for(ResFilter resFilter:resFilterList){
			String colName = resData.getTableName()+"."+resFilter.getCol();
			List<ResFilterProc> filterList = resFilter.getProc();
			for(ResFilterProc resFilterProc:filterList){
				data = handleCol(resFilter.getCol(),resFilterProc.getProcId(),resFilterProc.getParams(),dsName,data);
			}					
		}
		
		return data;
	}
	
	private List handleCol(String col, String procId, String params, String dsName, List data) {
		Processor processor = new Processor();
		processor.setId(procId);
		List<Processor> list = processorService.queryProcessor(processor);
		if(list!=null && list.size()>0){
			String procType = list.get(0).getType();
			String procCate = list.get(0).getCategory();		
			try {
				cn.bocom.service.resource.memory.handler.filter.FilterHandler handler = (cn.bocom.service.resource.memory.handler.filter.FilterHandler) Class.forName("cn.bocom.service.resource.memory.handler.filter."+procType.toLowerCase()+"."+dsName.toUpperCase()+"Handler").newInstance();
				Method method = cn.bocom.service.resource.memory.handler.filter.FilterHandler.class.getMethod(procCate, String.class, String.class, List.class);		
				data =  (List) method.invoke(handler, col, params, data);		
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}
		return data;
		
	}
	

}
