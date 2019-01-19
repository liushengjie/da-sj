package cn.bocom.r_service.process.impl;

import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import com.google.common.collect.Lists;
import cn.bocom.r_entity.process.proc.ContentProc;
import cn.bocom.r_entity.process.proc.DateProc;
import cn.bocom.r_entity.process.proc.SplitProc;
import cn.bocom.r_entity.process.proc.SubstrProc;
import cn.bocom.r_service.process.IProcess;
import cn.bocom.r_service.process.ProcessUtil;

/**
 * MYSQL 数据处理器
 * @author liushengjie
 * @version $Id: MySQLProcess.java, v 0.1 2019年1月17日 上午9:35:31 liushengjie Exp $
 */
public class MySQLProcess implements IProcess<String>{
    
    /** 可用功能名称*/
    public static List<String> availableFunction = Lists.newArrayList("content","date","notnull","split","substr"); 

    private static String NOTNULL = "<col> is not null and <col> \\<> ''";
    private static String SUBSTR = "substr(<col><if(flag)>,<origin><endif>,<len>)";
    private static String DATE = "<if(flag)><col> between STR_TO_DATE(<time1>, '%Y-%m-%d %H:%i:%S') and STR_TO_DATE(<time2>, '%Y-%m-%d %H:%i:%S')<else><col> <oper> STR_TO_DATE(<time1>, '%Y-%m-%d %H:%i:%S')<endif>";
    private static String SPLIT = "replace(replace(substring_index(<col>,'<symbol>', <index>),substring_index(<col>,'<symbol>', <leftIndex>),''),'<symbol>','')";
    private static String CONTENT = "<col> <oper> <content>";
    
    @Override
    public List<Map<String, Object>> availableFunction(String procType) {
        return ProcessUtil.availableFunc(availableFunction, procType);
    }

    //测试主函数
    public static void main(String[] q) {
    	//MySQLProcess m = new MySQLProcess();
    	
    	//System.out.println(m.notNull("name", null, ""));
    	
    	/*System.out.println(m.substr("name", null, "{\"subType\":\"2\",\"startIndex\":\"2\",\"endIndex\":\"5\"}"));
    	System.out.println(m.substr("name", null, "{\"subType\":\"1\",\"len\":\"3\"}"));
    	System.out.println(m.substr("name", null, "{\"subType\":\"0\",\"len\":\"5\"}"));*/
    	
    	/*System.out.println(m.date("name", null, "{\"oper\":\"between\",\"time1\":\"2019-01-01 12:12:12\",\"\":\"2019-01-15 23:23:23\"}"));
    	System.out.println(m.date("name", null, "{\"oper\":\"<\",\"time1\":\"2019-01-01 12:12:12\"}"));
    	System.out.println(m.date("name", null, "{\"oper\":\"<=\",\"time1\":\"2019-01-01 12:12:12\"}"));
    	System.out.println(m.date("name", null, "{\"oper\":\">\",\"time1\":\"2019-01-01 12:12:12\"}"));
    	System.out.println(m.date("name", null, "{\"col\":\"name\",\"oper\":\">=\",\"time1\":\"2019-01-01 12:12:12\"}"));*/
    	
    	//System.out.println(m.split("name", null, "{\"symbol\":\",\",\"index\":\"10\"}"));
    	
    	/*System.out.println(m.content("name", null, "{\"type\":\"0\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"1\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"2\",\"data\":[\"abcd\",\"1234\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"3\",\"data\":[\"abcd\",\"1234\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"4\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"5\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"6\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"7\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"8\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"9\",\"data\":[\"abcd\"]}"));
    	System.out.println(m.content("name", null, "{\"type\":\"10\",\"data\":[\"abcd\"]}"));*/
    	
    	/*System.out.println(m.changeType("name", null, "varchar"));
    	System.out.println(m.changeType("name", null, "int"));
    	System.out.println(m.changeType("name", null, "float"));
    	System.out.println(m.changeType("name", null, "date"));
    	System.out.println(m.changeType("name", null, "datetime"));*/
    }
    
    @Override
    public String notNull(String col, String data, String params) {
    	ST st = new ST(NOTNULL);
    	st.add("col", "(" + col + ")");
        return st.render();
    }


    @Override
    public String date(String col, String data, String params) {
    	DateProc d = convertObj(params, DateProc.class);
    	if(d==null) {
    		return null;
    	}
    	
    	String time1 = d.getTime1();
    	String time2 = d.getTime2();
    	String oper = d.getOper();
    	ST st = new ST(DATE);
    	st.add("col", "(" + col + ")");
    	st.add("oper", oper);
    	st.add("flag", oper.trim().equalsIgnoreCase("between"));
    	st.add("time1", time1);
    	st.add("time2", time2);
        return st.render();
    }


    @Override
    public String content(String col, String data, String params) {
    	ContentProc c = convertObj(params, ContentProc.class);
    	if(c==null) {
    		return null;
    	}
    	List<String> contentList = c.getData();
    	if(contentList==null||contentList.size()==0) {
    		return null;
    	}
    	String oper = null;
    	String content = null;
    	int compareType = c.getType();
    	//0：=；1：<>；2：in；3：not in；
    	//4：like(like %val%)；5：not like(not like %val%)；
    	//6：leftlike(like %val)；7：not leftlike(not like %val)；
    	//8：rightlike(like val%)；9：not rightlike(not like val%)
    	switch (compareType) {
		case 0://=
			oper = "=";
			content = "'" + contentList.get(0) + "'";
			break;
		case 1://<>
			oper = "<>";
			content = "'" + contentList.get(0) + "'";
    		break;
		case 2://in
			oper = "in";
			StringBuffer sb = new StringBuffer("");
			for(int i=0;i<contentList.size();i++) {
				sb.append(",'" + contentList.get(i) + "'");
			}
			content = "(" + sb.toString().substring(1) + ")";
    		break;
		case 3://not in
			oper = "not in";
			StringBuffer sb1 = new StringBuffer("");
			for(int i=0;i<contentList.size();i++) {
				sb1.append(",'" + contentList.get(i) + "'");
			}
			content = "(" + sb1.toString().substring(1) + ")";
    		break;
		case 4://like(like %val%)
			oper = "like";
			content = "'%" + contentList.get(0) + "%'";
    		break;
		case 5://not like(not like %val%)
			oper = "not like";
			content = "'%" + contentList.get(0) + "%'";
    		break;
		case 6://leftlike(like %val)
			oper = "like";
			content = "'%" + contentList.get(0) + "'";
    		break;
		case 7://not leftlike(not like %val)
			oper = "not like";
			content = "'%" + contentList.get(0) + "'";
    		break;
		case 8://rightlike(like val%)
			oper = "like";
			content = "'" + contentList.get(0) + "%'";
    		break;
		case 9://not rightlike(not like val%)
			oper = "not like";
			content = "'" + contentList.get(0) + "%'";
    		break;
    	default://默认为=
    		oper = "=";
			content = "'" + contentList.get(0) + "'";
    		break;
    	}
    	ST st = new ST(CONTENT);
    	st.add("col", "(" + col + ")");
    	st.add("oper", oper);
    	st.add("content", content);
        return st.render();
    }


    @Override
    public String substr(String col, String data, String params) {
    	SubstrProc s = convertObj(params, SubstrProc.class);
    	if(s==null) {
    		return null;
    	}
    	
    	int origin = 1;//起始位置，默认1
    	int len = 0;//长度
    	String subType = s.getSubType();
    	if(subType.equals("0")) {//0左截取：从最左侧开始按照长度截取
    		len = s.getLen();
    	} else if(subType.equals("1")) {//1右截取：从最右侧开始按照长度截取
    		len = -s.getLen();
    	} else if(subType.equals("2")) {//2范围截取：按照起始位置和结束位置截取
    		int startIndex = s.getStartIndex();
        	int endIndex = s.getEndIndex();
    		origin = startIndex;
    		len = endIndex - startIndex + 1;
    	} else {
    		return "(" + col + ")";
    	}
    	
    	ST st = new ST(SUBSTR);
    	st.add("col", "(" + col + ")");
    	st.add("flag", (subType.equals("0")||subType.equals("2")));
    	st.add("origin", origin);
    	st.add("len", len);
        return st.render();
    }


    @Override
    public String split(String col, String data, String params) {
    	SplitProc s = convertObj(params, SplitProc.class);
    	if(s==null) {
    		return null;
    	}
    	
    	String symbol = s.getSymbol();//分割符号
    	int index = Integer.parseInt(s.getIndex());//要取第几列
    	int leftIndex = index - 1;
    	
    	ST st = new ST(SPLIT);
    	st.add("col", "(" + col + ")");
    	st.add("symbol", symbol);
    	st.add("index", index);
    	st.add("leftIndex", leftIndex);
        return st.render();
    }

    @Override
    public String changeType(String col, String data, String newType) {
    	switch (newType.toUpperCase()){
    	case "VARCHAR":
    		col = "cast(" + col + " as CHAR)";
    		break;
		case "INT":
			col = "cast(" + col + " as SIGNED)";
		    break;
		case "FLOAT":
			col = "cast(" + col + " as DECIMAL(7,2))";
			break;
		case "DATE":
			col = "STR_TO_DATE(" + col + ", '%Y-%m-%d')";
			break;
		case "DATETIME":
			col = "STR_TO_DATE(" + col + ", '%Y-%m-%d %H:%i:%S')";
			break;
		default:
			col = "cast(" + col + " as CHAR)";
			break;
    	}
        return col;
    }

    @Override
    public String setAlias(String col, String data, String alias) {
    	return "(" + col + ") as " + alias;
    }

}
