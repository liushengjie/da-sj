package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_data")
@ApiModel("ResData（）")
public class ResData implements Serializable {
    /**
     * ID
     */
    @Id
    @ApiModelProperty(value ="ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    /**
     * 资源ID
     */
    @Column(name = "res_id")
    @ApiModelProperty(value ="资源ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String resId;

    /**
     * 数据源ID
     */
    @Column(name = "ds_id")
    @ApiModelProperty(value ="数据源ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String dsId;

    /**
     * 数据表
     */
    @Column(name = "table_name")
    @ApiModelProperty(value ="数据表",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String tableName;

    /**
     * 是否是自定义sql（1是 0否）
     */
    @ApiModelProperty(value ="是否是自定义sql（1是 0否）",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String diy;

    /**
     * sql
     */
    @Column(name = "diy_sql")
    @ApiModelProperty(value ="diy_sql",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String diySql;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @ApiModelProperty(value ="创建日期",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date createTime;
    
    
    /**
     * 别名ID
     */
    @Column(name = "alias_id")
    @ApiModelProperty(value ="别名ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String aliasId;
    
    @Transient
    private String alias;

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	private static final long serialVersionUID = 1L;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public String getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public ResData setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取资源ID
     *
     * @return res_id - 资源ID
     */
    public String getResId() {
        return resId;
    }

    /**
     * 设置资源ID
     *
     * @param resId 资源ID
     */
    public ResData setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
        return this;
    }

    /**
     * 获取数据源ID
     *
     * @return ds_id - 数据源ID
     */
    public String getDsId() {
        return dsId;
    }

    /**
     * 设置数据源ID
     *
     * @param dsId 数据源ID
     */
    public ResData setDsId(String dsId) {
        this.dsId = dsId == null ? null : dsId.trim();
        return this;
    }

    /**
     * 获取数据表
     *
     * @return table_name - 数据表
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置数据表
     *
     * @param tableName 数据表
     */
    public ResData setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
        return this;
    }

    /**
     * 获取是否是自定义sql（1是 0否）
     *
     * @return diy - 是否是自定义sql（1是 0否）
     */
    public String getDiy() {
        return diy;
    }

    /**
     * 设置是否是自定义sql（1是 0否）
     *
     * @param diy 是否是自定义sql（1是 0否）
     */
    public ResData setDiy(String diy) {
        this.diy = diy == null ? null : diy.trim();
        return this;
    }

    /**
     * 获取sql
     *
     * @return diy_sql - sql
     */
    public String getDiySql() {
        return diySql;
    }

    /**
     * 设置sql
     *
     * @param diySql sql
     */
    public ResData setDiySql(String diySql) {
        this.diySql = diySql == null ? null : diySql.trim();
        return this;
    }

    /**
     * 获取创建日期
     *
     * @return create_time - 创建日期
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建日期
     *
     * @param createTime 创建日期
     */
    public ResData setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }
    
    

    public String getAliasId() {
		return aliasId;
	}

	public void setAliasId(String aliasId) {
		this.aliasId = aliasId;
	}



	public enum FieldEnum {
        ID("id","id"),
		RES_ID("resId","res_id"),
		DS_ID("dsId","ds_id"),
		TABLE_NAME("tableName","table_name"),
		DIY("diy","diy"),
		DIY_SQL("diySql","diy_sql"),
		CREATE_TIME("createTime","create_time"),
    	ALIAS_ID("aliasId","alias_id");

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