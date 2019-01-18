package cn.bocom.r_entity.resource;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;

import cn.bocom.r_entity.process.ProcessEntity;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_res_data")
@ApiModel("ResData（）")
public class ResourceData implements Serializable {
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


    /**  连接方式*/
    private String connModel;
    
    private List<ProcessEntity> dataProcessor;
    
    

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
    public ResourceData setId(String id) {
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
    public ResourceData setResId(String resId) {
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
    public ResourceData setDsId(String dsId) {
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
    public ResourceData setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
        return this;
    }

    public String getConnModel() {
        return connModel;
    }

    public void setConnModel(String connModel) {
        this.connModel = connModel;
    }

    public List<ProcessEntity> getDataProcessor() {
        return dataProcessor;
    }

    public void setDataProcessor(List<ProcessEntity> dataProcessor) {
        this.dataProcessor = dataProcessor;
    }
}