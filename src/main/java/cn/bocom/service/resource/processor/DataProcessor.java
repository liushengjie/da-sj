/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.resource.processor 
 * @author: liushengjie   
 * @date: 2018年9月17日 下午2:08:35 
 */
package cn.bocom.service.resource.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.bocom.entity.ResData;
import cn.bocom.other.util.RandomUtil;

/** 
 * @ClassName: DataProcessor 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月17日 下午2:08:35  
 */
@Component
public class DataProcessor{
protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public String generateTbStr(ResData resData) {
		String tbStr = "";
		if("1".equals(resData.getDiy())){
			String tbTemp = resData.getDsId()+"_diy_"+RandomUtil.getRandomId(6);
			resData.setTableName(tbTemp);
			tbStr = new StringBuffer().append(" (").append(resData.getDiySql()).append(") ").append(resData.getTableName()).toString();
		}else{		
			tbStr = resData.getTableName();
		}
		
		logger.info("TbStr 生成：" + tbStr);
		return tbStr;
	}
}
