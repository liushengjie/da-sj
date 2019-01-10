package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res")
@ApiModel("Resource（）")
public class Res implements Serializable {
    /**
     * ID
     */
    @Id
    @ApiModelProperty(value ="ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    /**
     * 资源名称
     */
    @ApiModelProperty(value ="资源名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

    /**
     * 资源类别
     */
    @ApiModelProperty(value ="资源类别",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String category;

    /**
     * 连接方式（0：实时 1：数据提取）
     */
    @Column(name = "connect_type")
    @ApiModelProperty(value ="连接方式（0：实时 1：数据提取）",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String connectType;

    /**
     * 缓存表名
     */
    @Column(name = "cache_table")
    @ApiModelProperty(value ="缓存表名",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String cacheTable;

    /**
     * 数据量
     */
    @ApiModelProperty(value ="数据量",required = false)
    @ColumnType(jdbcType=JdbcType.INTEGER)
    private Integer num;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    @ApiModelProperty(value ="创建人",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value ="创建时间",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date createTime;
    
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    @ApiModelProperty(value ="更新时间",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date updateTime;

    /**
     * 缓存表结构是否建立(0：未建立 1：已建立)
     */
    @Column(name = "schema_flag")
    @ApiModelProperty(value ="缓存表结构是否建立(0：未建立 1：已建立)",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String schemaFlag;

    /**
     * 状态(0:未缓存 1:已缓存)
     */
    @ApiModelProperty(value ="状态(0:未缓存 1:已缓存)",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String status;
    
    @Transient
    private String categoryName;

    public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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
    public Res setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取资源名称
     *
     * @return name - 资源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置资源名称
     *
     * @param name 资源名称
     */
    public Res setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    /**
     * 获取资源类别
     *
     * @return category - 资源类别
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置资源类别
     *
     * @param category 资源类别
     */
    public Res setCategory(String category) {
        this.category = category == null ? null : category.trim();
        return this;
    }

    /**
     * 获取连接方式（0：实时 1：数据提取）
     *
     * @return connect_type - 连接方式（0：实时 1：数据提取）
     */
    public String getConnectType() {
        return connectType;
    }

    /**
     * 设置连接方式（0：实时 1：数据提取）
     *
     * @param connectType 连接方式（0：实时 1：数据提取）
     */
    public Res setConnectType(String connectType) {
        this.connectType = connectType == null ? null : connectType.trim();
        return this;
    }

    /**
     * 获取缓存表名
     *
     * @return cache_table - 缓存表名
     */
    public String getCacheTable() {
        return cacheTable;
    }

    /**
     * 设置缓存表名
     *
     * @param cacheName 缓存表名
     */
    public Res setCacheTable(String cacheTable) {
        this.cacheTable = cacheTable == null ? null : cacheTable.trim();
        return this;
    }

    /**
     * 获取数据量
     *
     * @return num - 数据量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数据量
     *
     * @param num 数据量
     */
    public Res setNum(Integer num) {
        this.num = num;
        return this;
    }

    /**
     * 获取创建人
     *
     * @return create_user - 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * 设置创建人
     *
     * @param createUser 创建人
     */
    public Res setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
        return this;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public Res setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 获取状态(0:未缓存 1:已缓存)
     *
     * @return status - 状态(0:未缓存 1:已缓存)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(0:未缓存 1:已缓存)
     *
     * @param status 状态(0:未缓存 1:已缓存)
     */
    public Res setStatus(String status) {
        this.status = status == null ? null : status.trim();
        return this;
    }

    public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSchemaFlag() {
		return schemaFlag;
	}

	public void setSchemaFlag(String schemaFlag) {
		this.schemaFlag = schemaFlag;
	}



	public enum FieldEnum {
        ID("id","id"),
		NAME("name","name"),
		CATEGORY("category","category"),
		CONNECT_TYPE("connectType","connect_type"),
		CACHE_NAME("cacheTable","cache_table"),
		NUM("num","num"),
		CREATE_USER("createUser","create_user"),
		CREATE_TIME("createTime","create_time"),
		UPDATE_TIME("updateTime","update_time"),
		STATUS("status","status"),
		SCHEMA("schemaFlag","schema_flag");

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