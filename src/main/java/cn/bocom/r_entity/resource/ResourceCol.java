package cn.bocom.r_entity.resource;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import cn.bocom.r_entity.process.ProcessEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_col")
@ApiModel("ResCol（）")
public class ResourceCol implements Serializable {
    private static final long serialVersionUID = 1L;
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
     * 别名
     */
    @Column(name = "alias")
    @ApiModelProperty(value ="别名",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String alias;
    
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
    private String dict;

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
    
    private List<ProcessEntity> colProcessor;

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

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
    public ResourceCol setId(String id) {
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
    public ResourceCol setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
        return this;
    }

	public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
    public ResourceCol setType(String type) {
        this.type = type == null ? null : type.trim();
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
    public ResourceCol setCol(String col) {
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
    public ResourceCol setPk(String pk) {
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
    public ResourceCol setIdx(String idx) {
        this.idx = idx == null ? null : idx.trim();
        return this;
    }

    public String getDict() {
        return dict;
    }

    public void setDict(String dict) {
        this.dict = dict;
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
    public ResourceCol setStatus(String status) {
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

    public List<ProcessEntity> getColProcessor() {
        return colProcessor;
    }

    public void setColProcessor(List<ProcessEntity> colProcessor) {
        this.colProcessor = colProcessor;
    }
}