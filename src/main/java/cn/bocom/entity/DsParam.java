/**
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.entity
 * @author: liushengjie
 * @date: 2018年8月28日 下午4:02:34
 */
package cn.bocom.entity;

import java.io.Serializable;

import org.apache.ibatis.type.JdbcType;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import tk.mybatis.mapper.annotation.ColumnType;

/**
 * @ClassName: DsParam
 * @Description: 数据源入参实体类
 * @author: liushengjie
 * @date: 2018年8月28日 下午4:02:34
 */
@ApiModel("数据源入参实体类")
public class DsParam implements Serializable {

	/**
     * 数据源id
     */
    @ApiModelProperty(value = "数据源id", required = true)
    private String id;
    
    /**
     * 数据源名称
     */
    @ApiModelProperty(value = "数据源名称", required = true)
    @ColumnType(jdbcType = JdbcType.VARCHAR)
    private String name;

    /**
     * 数据源类型
     */
    @ApiModelProperty(value = "数据源类型", required = true)
    private String type;
    
    /**
     * 数据源类型名称
     */
    @ApiModelProperty(value = "数据源类型名称", required = false)
    private String typeName;

    /**
     * 数据源驱动
     */
    @ApiModelProperty(value = "数据源驱动", required = false)
    private String driver;

    /**
     * 数据源url
     */
    @ApiModelProperty(value = "数据源ip", required = true)
    private String ip;

    /**
     * 数据源端口
     */
    @ApiModelProperty(value = "数据源端口", required = true)
    private String port;

    /**
     * 数据库名称
     */
    @ApiModelProperty(value = "数据库名称", required = true)
    private String database;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名", required = true)
    private String username;

    /**
     * 密码
     */
    @ApiModelProperty(value = "密码", required = true)
    private String pwd;

    /**
     * 是否启用XA
     */
    @ApiModelProperty(value = "是否启用XA", required = true)
    private String xa;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", required = false)
    private String createUser;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", required = false)
    private String createTime;

    /**
     * 任务状态:0-建立中;1-已使用;-1:已取消
     */
    @ApiModelProperty(value = "任务状态:0-建立中;1-已使用;-1:已取消", required = false)
    private String state;
    
    /**
     * 数据使用方式（0：接入，1：接出）
     */
    @ApiModelProperty(value = "数据使用方式（0：接入，1：接出）", required = false)
    private String dataMode;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id 要设置的 id
     */
    public void setId(String id) {
        this.id = id;
    }
    
    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 要设置的 name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type 要设置的 type
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * @return typeName
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * @param typeName 要设置的 typeName
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
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
    public void setDriver(String driver) {
        this.driver = driver == null ? null : driver.trim();
    }

    /**
     * @return ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip 要设置的 ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port 要设置的 port
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username 要设置的 username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd 要设置的 pwd
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return xa
     */
    public String getXa() {
        return xa;
    }

    /**
     * @param xa 要设置的 xa
     */
    public void setXa(String xa) {
        this.xa = xa;
    }

    /**
     * @return createUser
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser 要设置的 createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public String getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }
    
    /**
     * @return dataMode
     */
    public String getDataMode() {
        return dataMode;
    }

    /**
     * @param dataMode 要设置的 dataMode
     */
    public void setDataMode(String dataMode) {
        this.dataMode = dataMode;
    }

    /**
     * @return database
     */
    public String getDatabase() {
        return database;
    }

    /**
     * @param database 要设置的 database
     */
    public void setDatabase(String database) {
        this.database = database;
    }
}
