package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_srv_nodes")
@ApiModel("SrvNode（）")
public class SrvNode implements Serializable {
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    @Column(name = "srv_id")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String srvId;

    @Column(name = "res_id")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String resId;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String label;

    @Column(name = "table_name")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String tableName;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String type;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String level;

    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String param;

    @Column(name = "create_time")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.TIMESTAMP)
    private Date createTime;

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
    public SrvNode setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * @return srv_id
     */
    public String getSrvId() {
        return srvId;
    }

    /**
     * @param srvId
     */
    public SrvNode setSrvId(String srvId) {
        this.srvId = srvId == null ? null : srvId.trim();
        return this;
    }

    /**
     * @return res_id
     */
    public String getResId() {
        return resId;
    }

    /**
     * @param resId
     */
    public SrvNode setResId(String resId) {
        this.resId = resId == null ? null : resId.trim();
        return this;
    }

    /**
     * @return label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label
     */
    public SrvNode setLabel(String label) {
        this.label = label == null ? null : label.trim();
        return this;
    }

    /**
     * @return table_name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName
     */
    public SrvNode setTableName(String tableName) {
        this.tableName = tableName == null ? null : tableName.trim();
        return this;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type
     */
    public SrvNode setType(String type) {
        this.type = type == null ? null : type.trim();
        return this;
    }

    /**
     * @return level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public SrvNode setLevel(String level) {
        this.level = level == null ? null : level.trim();
        return this;
    }

    /**
     * @return param
     */
    public String getParam() {
        return param;
    }

    /**
     * @param param
     */
    public SrvNode setParam(String param) {
        this.param = param == null ? null : param.trim();
        return this;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public SrvNode setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		SRV_ID("srvId","srv_id"),
		RES_ID("resId","res_id"),
		LABEL("label","label"),
		TABLE_NAME("tableName","table_name"),
		TYPE("type","type"),
		LEVEL("level","level"),
		PARAM("param","param"),
		CREATE_TIME("createTime","create_time");

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