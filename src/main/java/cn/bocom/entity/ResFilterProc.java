package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_filter_proc")
@ApiModel("ResFilterProc（）")
public class ResFilterProc implements Serializable {
    @Id
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    /**
     * 资源列ID
     */
    @Column(name = "filter_id")
    @ApiModelProperty(value ="资源列ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String filterId;

    /**
     * 处理器ID
     */
    @Column(name = "proc_id")
    @ApiModelProperty(value ="处理器ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String procId;

    /**
     * 参数
     */
    @ApiModelProperty(value ="参数",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String params;

    /**
     * 排序
     */
    @ApiModelProperty(value ="排序",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String sort;

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
    public ResFilterProc setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取资源列ID
     *
     * @return filter_id - 资源列ID
     */
    public String getFilterId() {
        return filterId;
    }

    /**
     * 设置资源列ID
     *
     * @param filterId 资源列ID
     */
    public ResFilterProc setFilterId(String filterId) {
        this.filterId = filterId == null ? null : filterId.trim();
        return this;
    }

    /**
     * 获取处理器ID
     *
     * @return proc_id - 处理器ID
     */
    public String getProcId() {
        return procId;
    }

    /**
     * 设置处理器ID
     *
     * @param procId 处理器ID
     */
    public ResFilterProc setProcId(String procId) {
        this.procId = procId == null ? null : procId.trim();
        return this;
    }

    /**
     * 获取参数
     *
     * @return params - 参数
     */
    public String getParams() {
        return params;
    }

    /**
     * 设置参数
     *
     * @param params 参数
     */
    public ResFilterProc setParams(String params) {
        this.params = params == null ? null : params.trim();
        return this;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public String getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public ResFilterProc setSort(String sort) {
        this.sort = sort == null ? null : sort.trim();
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		FILTER_ID("filterId","filter_id"),
		PROC_ID("procId","proc_id"),
		PARAMS("params","params"),
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