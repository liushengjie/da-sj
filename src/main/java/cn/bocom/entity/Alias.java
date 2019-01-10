package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_alias")
@ApiModel("Alias（）")
public class Alias implements Serializable {
    @Id
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    /**
     * 类型：表(1),字段(2)
     */
    @ApiModelProperty(value ="类型：表(1),字段(2)",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String type;

    /**
     * 来源ID
     */
    @ApiModelProperty(value ="来源ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String datasource;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String tablename;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String colname;

    /**
     * 别名
     */
    @ApiModelProperty(value ="别名",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String alias;

    /**
     * 备注
     */
    @ApiModelProperty(value ="备注",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String remark;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public Alias setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取类型：表(1),字段(2)
     *
     * @return type - 类型：表(1),字段(2)
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型：表(1),字段(2)
     *
     * @param type 类型：表(1),字段(2)
     */
    public Alias setType(String type) {
        this.type = type == null ? null : type.trim();
        return this;
    }

    /**
     * 获取来源ID
     *
     * @return datasource - 来源ID
     */
    public String getDatasource() {
        return datasource;
    }

    /**
     * 设置来源ID
     *
     * @param datasource 来源ID
     */
    public Alias setDatasource(String datasource) {
        this.datasource = datasource == null ? null : datasource.trim();
        return this;
    }

    /**
     * @return tablename
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * @param tablename
     */
    public Alias setTablename(String tablename) {
        this.tablename = tablename == null ? null : tablename.trim();
        return this;
    }

    /**
     * @return colname
     */
    public String getColname() {
        return colname;
    }

    /**
     * @param colname
     */
    public Alias setColname(String colname) {
        this.colname = colname == null ? null : colname.trim();
        return this;
    }

    /**
     * 获取别名
     *
     * @return alias - 别名
     */
    public String getAlias() {
        return alias;
    }

    /**
     * 设置别名
     *
     * @param alias 别名
     */
    public Alias setAlias(String alias) {
        this.alias = alias == null ? null : alias.trim();
        return this;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public Alias setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		TYPE("type","type"),
		DATASOURCE("datasource","datasource"),
		TABLENAME("tablename","tablename"),
		COLNAME("colname","colname"),
		ALIAS("alias","alias"),
		REMARK("remark","remark");

        private String javaFieldName;

        private String dbFieldName;

        FieldEnum(String javaFieldName, String dbFieldName) {
            this.javaFieldName = javaFieldName;
            this.dbFieldName = dbFieldName;
        }

        public String javaFieldName() {
            return javaFieldName;
        }

        public String dbFieldName() {
            return dbFieldName;
        }
    }
}