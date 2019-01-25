package cn.bocom.r_entity.process;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 处理器对象
 * @author liushengjie
 * @version $Id: ProcessEntity.java, v 0.1 2019年1月16日 下午3:54:32 liushengjie Exp $
 */
public class ProcessEntity implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;
    
    /**
     * ID
     */
    @Id
    @ApiModelProperty(value ="id",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;
    /**
     * 处理器名
     */
    @Column(name = "process_name")
    @ApiModelProperty(value ="处理器名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String processName;
    /**
     * 列
     */
    @Column(name = "process_col")
    @ApiModelProperty(value ="列",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String processCol;
    /**
     * 参数
     */
    @Column(name = "params")
    @ApiModelProperty(value ="参数",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String params;
    /**
     * 列ID
     */
    @Column(name = "col_id")
    @ApiModelProperty(value ="列ID",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String colId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProcessName() {
		return processName;
	}
	public void setProcessName(String processName) {
		this.processName = processName;
	}
	public String getProcessCol() {
		return processCol;
	}
	public void setProcessCol(String processCol) {
		this.processCol = processCol;
	}
	public String getParams() {
		return params;
	}
	public void setParams(String params) {
		this.params = params;
	}
	public String getColId() {
		return colId;
	}
	public void setColId(String colId) {
		this.colId = colId;
	}
    
}
