/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.datasource 
 * @author: liu   
 * @date: 2018年8月10日 下午5:38:43 
 */
package cn.bocom.other.datasource;

/** 
 * @ClassName: DBContextHolder 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月10日 下午5:41:15  
 */
public class DBContextHolder {
	/** 数据源的KEY */
    public static final String DATASOURCE_KEY = "DATASOURCE_KEY";
    /** 数据源的URL */
    public static final String DATASOURCE_URL = "DATASOURCE_URL";
    /** 数据源的驱动 */
    public static final String DATASOURCE_DRIVER = "DATASOURCE_DRIVER";
    /** 数据源的用户名 */
    public static final String DATASOURCE_USERNAME = "DATASOURCE_USERNAME";
    /** 数据源的密码 */
    public static final String DATASOURCE_PASSWORD = "DATASOURCE_PASSWORD";

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDBType(String dataSourceKeyStr) {
        contextHolder.set(dataSourceKeyStr);
    }

    public static String getDBType() {
        return contextHolder.get();
    }

    public static void clearDBType() {
        contextHolder.remove();
    }
}
