package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_filter")
@ApiModel("ResFilter（）")
public class ResFilter implements Serializable {
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
     * 列名称
     */
    @ApiModelProperty(value ="列名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String col;
    
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
    private List<ResFilterProc> proc;

    public List<ResFilterProc> getProc() {
		return proc;
	}

	public void setProc(List<ResFilterProc> proc) {
		this.proc = proc;
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
    public ResFilter setId(String id) {
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
    public ResFilter setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
        return this;
    }

    /**
     * @return col
     */
    public String getCol() {
        return col;
    }

    /**
     * @param col
     */
    public ResFilter setCol(String col) {
        this.col = col == null ? null : col.trim();
        return this;
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
		COL("col","col"),
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