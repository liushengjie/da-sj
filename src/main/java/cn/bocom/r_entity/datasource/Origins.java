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

/**
 * 上游数据源枚举类
 * 
 * @author liushengjie
 * @version $Id: Origins.java, v 0.1 2019年1月11日 下午1:55:25 liushengjie Exp $
 */
public class Origins {
    public enum DataSourceEnum {
        // 源名称、编号、entity类、插件名称
        MYSQL("MYSQL", 0, MySQL.class, MysqlPlugin.class, "relationData"), ORACLE("ORACLE", 1,
                Oracle.class, OraclePlugin.class,
                "relationData"), EXCEL("EXCEL", 2, Excel.class, ExcelPlugin.class, "noSQLData");

        private String name;
        private int code;
        private Class<? extends OriginEntity> entityClass;
        @SuppressWarnings("rawtypes")
        private Class<? extends DataSourcePlugin> pluginClass;
        private String category;

        @SuppressWarnings("rawtypes")
        private DataSourceEnum(String name, int code, Class<? extends OriginEntity> entityClass,
                Class<? extends DataSourcePlugin> pluginClass, String categoty) {
            this.name = name;
            this.code = code;
            this.entityClass = entityClass;
            this.pluginClass = pluginClass;
            this.category = categoty;
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

        @SuppressWarnings("rawtypes")
        public DataSourcePlugin getPluginClass() {
            try {
                return pluginClass.newInstance();
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

    }

    public enum ConnStyleEnum { // 连接类型枚举
        JDBC(0), EXCEL(1);

        private int code;

        private ConnStyleEnum(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}
