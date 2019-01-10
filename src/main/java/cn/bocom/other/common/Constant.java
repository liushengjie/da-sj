/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.other.common
 * @author: liushengjie
 * @date: 2018年8月28日 下午3:44:52
 */
package cn.bocom.other.common;

/**
 * @ClassName: CONST
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年8月28日 下午3:44:52
 */
public class Constant {
    public static final String MYSQL_URL_TPL =
            "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf8";

    public static final String MYSQL_DRIVER_TPL = "com.mysql.jdbc.Driver";

    public static final String ORACLE_URL_TPL = "jdbc:oracle:thin:@%s:%s:%s";

    public static final String ORACLE_DRIVER_TPL = "oracle.jdbc.OracleDriver";


}
