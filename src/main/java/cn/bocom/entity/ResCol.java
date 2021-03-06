package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_col")
@ApiModel("ResCol（）")
public class ResCol implements Serializable {
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
     * 别名ID
     */
    @Column(name = "alias_id")
    @ApiModelProperty(value ="别名ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String aliasId;
    
    /**
     * 缓存列名
     */
    @Column(name = "col_cache")
    @ApiModelProperty(value ="缓存列名",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String colCache;

    /**
     * 列类型（varchar、num、datatime）
     */
    @ApiModelProperty(value ="列类型（varchar、num、datatime）",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String type;

    /**
     * 属性(00：维度 01：度量 10：自定义维度 11：自定义度量)
     */
    @ApiModelProperty(value ="属性(00：维度 01：度量 10：自定义维度 11：自定义度量)",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String attr;

    /**
     * 源列ID
     */
    @ApiModelProperty(value ="源列ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String col;
    
    /**
     * 源拆分列
     */
    @ApiModelProperty(value ="源拆分列",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String origin;

    /**
     * 是否主键（0：否  1：是）
     */
    @ApiModelProperty(value ="是否主键（0：否  1：是）",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String pk;

    /**
     * 是否索引（0：否 1：是）
     */
    @ApiModelProperty(value ="是否索引（0：否 1：是）",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String idx;

    /**
     * 字典ID
     */
    @ApiModelProperty(value ="字典ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String dic;

    /**
     * 状态(0:隐藏 1:可用)
     */
    @ApiModelProperty(value ="状态(0:隐藏 1:可用)",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String status;
    
    @ApiModelProperty(value ="转换后的列类型",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String changeType;
    
    /**
     * 排序
     */
    @ApiModelProperty(value ="排序",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String sort;
    
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value ="创建时间",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date createTime;
    
    @Transient
    private List<ResColProc> proc;
    
    @Transient
    private String alias;

    public List<ResColProc> getProc() {
		return proc;
	}

	public void setProc(List<ResColProc> proc) {
		this.proc = proc;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

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
    public ResCol setId(String id) {
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
    public ResCol setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
        return this;
    }



    public String getAliasId() {
		return aliasId;
	}

	public void setAliasId(String aliasId) {
		this.aliasId = aliasId;
	}

	public String getColCache() {
		return colCache;
	}

	public void setColCache(String colCache) {
		this.colCache = colCache;
	}

	/**
     * 获取列类型（varchar、num、datatime）
     *
     * @return type - 列类型（varchar、num、datatime）
     */
    public String getType() {
        return type;
    }

    /**
     * 设置列类型（varchar、num、datatime）
     *
     * @param type 列类型（varchar、num、datatime）
     */
    public ResCol setType(String type) {
        this.type = type == null ? null : type.trim();
        return this;
    }

    /**
     * 获取属性(00：维度 01：度量 10：自定义维度 11：自定义度量)
     *
     * @return attr - 属性(00：维度 01：度量 10：自定义维度 11：自定义度量)
     */
    public String getAttr() {
        return attr;
    }

    /**
     * 设置属性(00：维度 01：度量 10：自定义维度 11：自定义度量)
     *
     * @param attr 属性(00：维度 01：度量 10：自定义维度 11：自定义度量)
     */
    public ResCol setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
        return this;
    }

    /**
     * 获取源列ID
     *
     * @return col - 源列ID
     */
    public String getCol() {
        return col;
    }

    /**
     * 设置源列ID
     *
     * @param col 源列ID
     */
    public ResCol setCol(String col) {
        this.col = col == null ? null : col.trim();
        return this;
    }

    /**
     * 获取是否主键（0：否  1：是）
     *
     * @return pk - 是否主键（0：否  1：是）
     */
    public String getPk() {
        return pk;
    }

    /**
     * 设置是否主键（0：否  1：是）
     *
     * @param pk 是否主键（0：否  1：是）
     */
    public ResCol setPk(String pk) {
        this.pk = pk == null ? null : pk.trim();
        return this;
    }

    /**
     * 获取是否索引（0：否 1：是）
     *
     * @return idx - 是否索引（0：否 1：是）
     */
    public String getIdx() {
        return idx;
    }

    /**
     * 设置是否索引（0：否 1：是）
     *
     * @param idx 是否索引（0：否 1：是）
     */
    public ResCol setIdx(String idx) {
        this.idx = idx == null ? null : idx.trim();
        return this;
    }

    /**
     * 获取字典ID
     *
     * @return dic - 字典ID
     */
    public String getDic() {
        return dic;
    }

    /**
     * 设置字典ID
     *
     * @param dic 字典ID
     */
    public ResCol setDic(String dic) {
        this.dic = dic == null ? null : dic.trim();
        return this;
    }

    /**
     * 获取状态(0:隐藏 1:可用)
     *
     * @return status - 状态(0:隐藏 1:可用)
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置状态(0:隐藏 1:可用)
     *
     * @param status 状态(0:隐藏 1:可用)
     */
    public ResCol setStatus(String status) {
        this.status = status == null ? null : status.trim();
        return this;
    }

    public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public enum FieldEnum {
        ID("id","id"),
		RES_ID("resId","res_id"),
		TYPE("type","type"),
		ATTR("attr","attr"),
		COL("col","col"),
		PK("pk","pk"),
		IDX("idx","idx"),
		DIC("dic","dic"),
		STATUS("status","status"),
		ORIGIN("origin","origin"),
		CHANGETYPE("changeType","change_type"),
		COL_CACHE("colCache","col_cache"),
		ALIAS_ID("aliasId","alias_id"),
		CREATE_TIME("createTime","create_time"),
		SORT("sort","sort");
		
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