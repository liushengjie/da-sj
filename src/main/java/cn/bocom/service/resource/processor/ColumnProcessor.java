/**   
 * Copyright ? 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.resource.processor 
 * @author: liushengjie   
 * @date: 2018年9月17日 下午2:09:27 
 */
package cn.bocom.service.resource.processor;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.bocom.entity.DataSource;
import cn.bocom.entity.Processor;
import cn.bocom.entity.ResCol;
import cn.bocom.entity.ResColProc;
import cn.bocom.entity.ResData;
import cn.bocom.service.ProcessorService;
import cn.bocom.service.datasource.DataSourceService;

/** 
 * @ClassName: ColumnProcessor 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月17日 下午2:09:27  
 */
@Component
public class ColumnProcessor {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@Autowired
	private ProcessorService processorService;
	
	public String generateColStr(ResData resData, List<ResCol> resColList, boolean saveFlag) {
		DataSource ds = dataSourceService.selectDsById(resData.getDsId());
		String dsName = ds.gettypeName();
		
		StringBuffer sb = new StringBuffer("");
		
		if(resColList != null && resColList.size() > 0) {
			resColList.stream().map(m -> {
				return m.setId(resData.getDsId() + "#" + m.getCol());
			}).forEach(col -> {
					String colName = resData.getTableName()+"."+col.getOrigin();
					String colNewName = col.getCol();
					if(saveFlag){
						colNewName = col.getColCache();
					}
					//处理拆分字段
					List<ResColProc> procList = col.getProc();
					if(procList!=null && procList.size()>0){
						for(ResColProc resColProc:procList){
							String newCol = handleCol(colName,resColProc.getProcId(),resColProc.getParams(),dsName);
							if(newCol != null){
								sb.append(newCol).append(" as ").append(colNewName).append(" ,");
							}				
						}
					}else{
						sb.append(col.getCol()).append(" as ").append(colNewName).append(" ,");
					}
//					//处理数据字典
//					String dic = col.getDic();
//					if(StringUtils.isNotBlank(dic)){
//						sb.append("(select name from t_res_dict where code_type = '").append(dic).append("' and code = ").append(resData.getTableName()).append(".").append(colNewName).append(") as ").append(colNewName).append(" ,");
//					}
					
			});
		}
		
		logger.info("column 生成：" + sb.toString().substring(0, sb.length()-1));
		return sb.toString().substring(0, sb.length()-1);
	}
	
	private String handleCol(String col, String procId, String params, String dsName) {
		String newCol = null;
		Processor processor = new Processor();
		processor.setId(procId);
		List<Processor> list = processorService.queryProcessor(processor);
		if(list!=null && list.size()>0){
			String procType = list.get(0).getType();
			String procCate = list.get(0).getCategory();		
			try {
				cn.bocom.service.resource.jdbc.handler.column.ColumnHandler handler = (cn.bocom.service.resource.jdbc.handler.column.ColumnHandler) Class.forName("cn.bocom.service.resource.jdbc.handler.column."+procType.toLowerCase()+"."+dsName.toUpperCase()+"Handler").newInstance();
				Method method = cn.bocom.service.resource.jdbc.handler.column.ColumnHandler.class.getMethod(procCate, String.class, String.class);		
				newCol =  (String) method.invoke(handler, col, params);		
			} catch (Exception e) {
				e.printStackTrace();
			} 
				
		}
		return newCol;
		
	}
	
	public List generateColData(ResData resData, List<ResCol> resColList, List data) {
		DataSource ds = dataSourceService.selectDsById(resData.getDsId());
		String dsName = ds.gettypeName();
		
		for(ResCol col:resColList){
			String colName = col.getOrigin();
			String colNewName = col.getCol();
			
			//处理拆分字段
			List<ResColProc> procList = col.getProc();
			if(procList!=null && procList.size()>0){
				for(ResColProc resColProc:procList){
					data = handleCol(colName, colNewName, resColProc.getProcId(),resColProc.getParams(),dsName,data);				
				}
			}
		}

		return data;
	}	
	
	private List handleCol(String col, String colNew, String procId, String params, String dsName, List data) {
		Processor processor = new Processor();
		processor.setId(procId);
		List<Processor> list = processorService.queryProcessor(processor);
		if(list!=null && list.size()>0){
			String procType = list.get(0).getType();
			String procCate = list.get(0).getCategory();		
			try {
				cn.bocom.service.resource.memory.handler.column.ColumnHandler handler = (cn.bocom.service.resource.memory.handler.column.ColumnHandler) Class.forName("cn.bocom.service.resource.memory.handler.column."+procType.toLowerCase()+"."+dsName.toUpperCase()+"Handler").newInstance();
				Method method = cn.bocom.service.resource.memory.handler.column.ColumnHandler.class.getMethod(procCate, String.class, String.class, String.class, List.class);		
				data =  (List) method.invoke(handler, col, colNew, params, data);		
			} catch (Exception e) {
				e.printStackTrace();
			} 
				
		}
		return data;
		
	}

}
