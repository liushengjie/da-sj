package cn.bocom.r_entity.datasource;

import java.util.List;
import java.util.Map;

import org.assertj.core.util.Lists;

import com.google.common.collect.ImmutableMap;

import cn.bocom.other.common.SjException;
import cn.bocom.r_entity.datasource.form.Excel;
import cn.bocom.r_entity.datasource.form.MySQL;
import cn.bocom.r_entity.datasource.form.Oracle;
import cn.bocom.r_service.datasource.DataSourcePlugin;
import cn.bocom.r_service.datasource.plugin.ExcelPlugin;
import cn.bocom.r_service.datasource.plugin.MysqlPlugin;
import cn.bocom.r_service.datasource.plugin.OraclePlugin;
import cn.bocom.r_service.process.IProcess;
import cn.bocom.r_service.process.impl.ListProcess;
import cn.bocom.r_service.process.impl.MySQLProcess;
import cn.bocom.r_service.process.impl.OracleProcess;
import cn.bocom.r_service.resource.res_process.IHandler;
import cn.bocom.r_service.resource.res_process.handler.JDBCHandler;
import cn.bocom.r_service.resource.res_process.handler.ListHandler;

/**
 * 上游数据源枚举类
 * 
 * @author liushengjie
 * @version $Id: Origins.java, v 0.1 2019年1月11日 下午1:55:25 liushengjie Exp $
 */
public class Origins {
    public enum DataSourceEnum {

        MYSQL("MYSQL", 0, MySQL.class, MysqlPlugin.class, MySQLProcess.class, "relationData",0), 
        ORACLE("ORACLE", 1, Oracle.class, OraclePlugin.class, OracleProcess.class, "relationData", 0),
        EXCEL("EXCEL", 2, Excel.class, ExcelPlugin.class, ListProcess.class, "noSQLData", 1);

        /** 源名称 */
        private String name;
        /** 编号 */
        private int code;
        /** 表单Entity */
        private Class<? extends OriginEntity> entityClass;
        /** 源插件类 */
        @SuppressWarnings("rawtypes")
        private Class<? extends DataSourcePlugin> pluginClass;
        /** 处理器类 */
        private Class<? extends IProcess<?>> processClass;
        /** 归类 */
        private String category;
        /** 连接类型 */
        private int connModel;

        @SuppressWarnings("rawtypes")
        private DataSourceEnum(String name, int code, Class<? extends OriginEntity> entityClass,
                Class<? extends DataSourcePlugin> pluginClass,
                Class<? extends IProcess<?>> processClass, String categoty, int connModel) {
            this.name = name;
            this.code = code;
            this.entityClass = entityClass;
            this.pluginClass = pluginClass;
            this.processClass = processClass;
            this.category = categoty;
            this.connModel = connModel;
        }

        public static DataSourceEnum match(int type, DataSourceEnum defaultDataSource) {
            for (DataSourceEnum item : DataSourceEnum.values()) {
                if (item.code == type) {
                    return item;
                }
            }
            return defaultDataSource;
        }

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }

        public Class<? extends OriginEntity> getEntityClass() {
            return entityClass;
        }

        public IProcess<?> getProcessClass() {
            try {
                return processClass.newInstance();
            } catch (Exception e) {
                throw new SjException(e);
            }
        }

        @SuppressWarnings("rawtypes")
        public DataSourcePlugin getPluginClass(DataSource datasource) {
            try {
                if(String.valueOf(EXCEL.code).equals(datasource.getType())) return pluginClass.getConstructor(DataSource.class).newInstance(datasource);
                else return pluginClass.newInstance();
            } catch (Exception e) {
                throw new SjException(e);
            }
        }

        public static List<Map<String, Object>> getOrigins() {
            List<Map<String, Object>> ret = Lists.newArrayList();
            for (DataSourceEnum item : DataSourceEnum.values()) {
                Map<String, Object> m =
                        ImmutableMap.<String, Object>builder().put("name", item.name)
                                .put("code", item.code).put("cat", item.category).build();
                ret.add(m);
            }
            return ret;
        }

        public int getConnModel() {
            return connModel;
        }
    }

    public enum ConnModelEnum { // 连接类型枚举
        JDBC(0, JDBCHandler.class), EXCEL(1, ListHandler.class);

        private int code;
        private Class<? extends IHandler<?>> handler;

        private ConnModelEnum(int code, Class<? extends IHandler<?>> handler) {
            this.code = code;
            this.handler = handler;
        }

        public static ConnModelEnum match(int code, ConnModelEnum defaultConnModel) {
            for (ConnModelEnum item : ConnModelEnum.values()) {
                if (item.code == code) {
                    return item;
                }
            }
            return defaultConnModel;
        }

        public IHandler<?> getHandlerClass() {
            try {
                return handler.newInstance();
            } catch (Exception e) {
                throw new SjException(e);
            }
        }

        public int getCode() {
            return code;
        }
    }
}
