package cn.bocom.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

@Table(name = "t_srv")
@ApiModel("Srv（）")
public class Srv implements Serializable {
    @Id
    @ApiModelProperty(value ="",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String id;

    /**
     * 服务名称
     */
    @ApiModelProperty(value ="服务名称",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String name;

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
     * 创建人
     */
    @Column(name = "create_user")
    @ApiModelProperty(value ="创建人",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String createUser;

    /**
     * 服务状态
     */
    @ApiModelProperty(value ="服务状态",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String state;

    /**
     * 服务地址
     */
    @ApiModelProperty(value ="服务地址",required = false)
    @ColumnType(jdbcType=JdbcType.VARCHAR)
    private String url;

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
    public Srv setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取服务名称
     *
     * @return name - 服务名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置服务名称
     *
     * @param name 服务名称
     */
    public Srv setName(String name) {
        this.name = name == null ? null : name.trim();
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
    public Srv setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public Srv setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
    public Srv setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
        return this;
    }

    /**
     * 获取服务状态
     *
     * @return state - 服务状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置服务状态
     *
     * @param state 服务状态
     */
    public Srv setState(String state) {
        this.state = state == null ? null : state.trim();
        return this;
    }

    /**
     * 获取服务地址
     *
     * @return url - 服务地址
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置服务地址
     *
     * @param url 服务地址
     */
    public Srv setUrl(String url) {
        this.url = url == null ? null : url.trim();
        return this;
    }

    public enum FieldEnum {
        ID("id","id"),
		NAME("name","name"),
		CREATE_TIME("createTime","create_time"),
		UPDATE_TIME("updateTime","update_time"),
		CREATE_USER("createUser","create_user"),
		STATE("state","state"),
		URL("url","url");

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