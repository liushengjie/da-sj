package cn.bocom.r_entity.datasource;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * 数据源入库实体类
 * @author liushengjie
 * @version $Id: DataSource.java, v 0.1 2019年1月11日 下午2:21:16 liushengjie Exp $
 */
@Table(name = "t_data_source")
@ApiModel("DataSource（）")
public class DataSource implements Serializable {
    
    public DataSource() {}

    public DataSource(String type) {
        this.type = type;
    }

    /**
     * 主键
     */
    @Id
    @ApiModelProperty(value = "主键", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String id;

    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String name;

    /**
     * 数据源类型
     */
    @ApiModelProperty(value = "数据源类型", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String type;


    /**
     * 数据源驱动
     */
    @ApiModelProperty(value = "数据源驱动", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String driver;

    /**
     * 数据源url
     */
    @ApiModelProperty(value = "数据源url", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String url;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String pwd;

    /**
     * 是否启用XA
     */
    @ApiModelProperty(value = "是否启用XA", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String xa;

    /**
     * 创建人
     */
    @Column(name = "create_user")
    @ApiModelProperty(value = "创建人", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String createUser;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", required = false)
    @ColumnType(jdbcType = JdbcType.TIMESTAMP)
    private Date createTime;

    /**
     * 任务状态:0-建立中;1-已使用;-1:已取消
     */
    @ApiModelProperty(value = "任务状态:0-建立中;1-已使用;-1:已取消", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String state;

    /**
     * 数据使用方式（0：接入，1：接出）
     */
    @Column(name = "data_mode")
    @ApiModelProperty(value = "数据使用方式（0：接入，1：接出）", required = false)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String dataMode;

    private static final long serialVersionUID = 1L;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public DataSource setId(String id) {
        this.id = id == null ? null : id.trim();
        return this;
    }

    /**
     * 获取数据源名称
     *
     * @return name - 数据源名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据源名称
     *
     * @param name 数据源名称
     */
    public DataSource setName(String name) {
        this.name = name == null ? null : name.trim();
        return this;
    }

    /**
     * 获取数据源类型
     *
     * @return type - 数据源类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置数据源类型
     *
     * @param type 数据源类型
     */
    public DataSource setType(String type) {
        this.type = type == null ? null : type.trim();
        return this;
    }

    /**
     * 获取数据源驱动
     *
     * @return driver - 数据源驱动
     */
    public String getDriver() {
        return driver;
    }

    /**
     * 设置数据源驱动
     *
     * @param driver 数据源驱动
     */
    public DataSource setDriver(String driver) {
        this.driver = driver == null ? null : driver.trim();
        return this;
    }

    /**
     * 获取数据源url
     *
     * @return url - 数据源url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置数据源url
     *
     * @param url 数据源url
     */
    public DataSource setUrl(String url) {
        this.url = url == null ? null : url.trim();
        return this;
    }

    /**
     * 获取用户名
     *
     * @return username - 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public DataSource setUsername(String username) {
        this.username = username == null ? null : username.trim();
        return this;
    }

    /**
     * 获取密码
     *
     * @return pwd - 密码
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * 设置密码
     *
     * @param pwd 密码
     */
    public DataSource setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
        return this;
    }

    /**
     * 获取是否启用XA
     *
     * @return xa - 是否启用XA
     */
    public String getXa() {
        return xa;
    }

    /**
     * 设置是否启用XA
     *
     * @param xa 是否启用XA
     */
    public DataSource setXa(String xa) {
        this.xa = xa == null ? null : xa.trim();
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
    public DataSource setCreateUser(String createUser) {
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
    public DataSource setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    /**
     * 获取任务状态:0-建立中;1-已使用;-1:已取消
     *
     * @return state - 任务状态:0-建立中;1-已使用;-1:已取消
     */
    public String getState() {
        return state;
    }

    /**
     * 设置任务状态:0-建立中;1-已使用;-1:已取消
     *
     * @param state 任务状态:0-建立中;1-已使用;-1:已取消
     */
    public DataSource setState(String state) {
        this.state = state == null ? null : state.trim();
        return this;
    }

    /**
     * 获取数据使用方式（0：接入，1：接出）
     *
     * @return data_mode - 数据使用方式（0：接入，1：接出）
     */
    public String getDataMode() {
        return dataMode;
    }

    /**
     * 设置数据使用方式（0：接入，1：接出）
     *
     * @param dataMode 数据使用方式（0：接入，1：接出）
     */
    public DataSource setDataMode(String dataMode) {
        this.dataMode = dataMode == null ? null : dataMode.trim();
        return this;
    }
}
