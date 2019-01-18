package cn.bocom.r_service.process.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.stringtemplate.v4.ST;

import com.google.common.collect.Lists;

import cn.bocom.r_entity.process.proc.DateProc;
import cn.bocom.r_entity.process.proc.NotNullProc;
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
    private static String SUBSTR = "substr(<str>,<origin><if(flag)>,<len><endif>)";
    private static String DATE = "<if(flag)><col> between STR_TO_DATE(<time1>, '%Y-%m-%d %H:%i:%S') and STR_TO_DATE(<time2>, '%Y-%m-%d %H:%i:%S')<else><col> <oper> STR_TO_DATE(<time1>, '%Y-%m-%d %H:%i:%S')<endif>";
    
    @Override
    public List<Map<String, Object>> availableFunction(String procType) {
        return ProcessUtil.availableFunc(availableFunction, procType);
    }

    //测试主函数
    public static void main(String[] q) {
    	//System.out.println(new MySQLProcess().notNull(null, "{\"col\":\"name\"}"));
    	//System.out.println(new MySQLProcess().substr(null, "{\"str\":\"name\",\"origin\":\"2\",\"len\":\"3\"}"));
    	System.out.println(new MySQLProcess().date(null, "{\"col\":\"name\",\"oper\":\"between\",\"time1\":\""+new Date().getTime()+"\",\"\":\""+new Date().getTime()+"\"}"));
    }
    
    @Override
    public String notNull(String data, String params) {
<<<<<<< HEAD
    	NotNullProc n = convertObj(params, NotNullProc.class);
    	if(n==null) {
    		return null;
    	}
    	if(data==null||data.equals("")) {
    		data = n.getCol();
    	} else {
    		data = "(" + data + ")";
    	}
    	
    	ST st = new ST(NOTNULL);
    	st.add("col", data);
        return st.render();
=======
        return "abc";
>>>>>>> branch 'master' of http://192.168.124.240/liushengjie/da-sj.git
    }


    @Override
    public String date(String data, String params) {
    	DateProc d = convertObj(params, DateProc.class);
    	if(d==null) {
    		return null;
    	}
    	if(data==null||data.equals("")) {
    		data = d.getCol();
    	} else {
    		data = "(" + data + ")";
    	}
    	
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	String time1 = d.getTime1()==null?"":sdf.format(new Date(d.getTime1()));
    	String time2 = d.getTime1()==null?"":sdf.format(new Date(d.getTime2()));
    	String oper = d.getOper();
    	ST st = new ST(DATE);
    	st.add("col", data);
    	st.add("oper", oper);
    	st.add("flag", oper.trim().equalsIgnoreCase("between"));
    	st.add("time1", time1);
    	st.add("time2", time2);
        return st.render();
    }


    @Override
    public String content(String col, String params) {
        return null;
    }


    @Override
    public String substr(String data, String params) {
    	SubstrProc s = convertObj(params, SubstrProc.class);
    	if(s==null) {
    		return null;
    	}
    	if(data==null||data.equals("")) {
    		data = s.getStr();
    	} else {
    		data = "(" + data + ")";
    	}
    	
    	String origin = s.getOrigin();
    	String len = s.getLen();
    	ST st = new ST(SUBSTR);
    	st.add("str", data);
    	st.add("origin", origin);
    	st.add("flag", (len!=null&&!len.equals("")));
    	st.add("len", len);
        return st.render();
    }


    @Override
    public String split(String col, String params) {
        return null;
    }


}
