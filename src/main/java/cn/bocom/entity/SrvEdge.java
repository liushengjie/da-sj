package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_srv_edges")
@ApiModel("SrvEdge（）")
public class SrvEdge implements Serializable {
    @Id
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    @Column(name = "srv_id")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String srvId;

    @Column(name = "from_node")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String from;

    @Column(name = "to_node")
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String to;

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
    public SrvEdge setId(String id) {
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
    public SrvEdge setSrvId(String srvId) {
        this.srvId = srvId == null ? null : srvId.trim();
        return this;
    }

    /**
     * @return from
     */
    public String getFrom() {
        return from;
    }

    /**
     * @param from
     */
    public SrvEdge setFrom(String from) {
        this.from = from == null ? null : from.trim();
        return this;
    }

    /**
     * @return to
     */
    public String getTo() {
        return to;
    }

    /**
     * @param to
     */
    public SrvEdge setTo(String to) {
        this.to = to == null ? null : to.trim();
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
    public SrvEdge setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		SRV_ID("srvId","srv_id"),
		FROM("from","from_node"),
		TO("to","to_node"),
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