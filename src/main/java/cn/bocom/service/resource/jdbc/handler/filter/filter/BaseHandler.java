package cn.bocom.service.resource.jdbc.handler.filter.filter;

import cn.bocom.service.resource.jdbc.handler.filter.FilterHandler;

public interface BaseHandler extends FilterHandler {

	@Override
	public default String notnull(String col, String params){
	
		StringBuffer sb = new StringBuffer();
		sb.append(" and ").append(col).append(" is not null ");
		return sb.toString();
	}

}
