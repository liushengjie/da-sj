package cn.bocom.r_entity.datasource.form;

import cn.bocom.r_entity.datasource.OriginEntity;

public class MySQL extends OriginEntity {
    
    private static final long serialVersionUID = 1L;
    private String driver;
    private String ip;
    private String port;
    private String database;
    private String username;
    private String pwd;
    private String xa;
    
    public String getDriver() {
        return driver;
    }
    public void setDriver(String driver) {
        this.driver = driver;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPort() {
        return port;
    }
    public void setPort(String port) {
        this.port = port;
    }
    public String getDatabase() {
        return database;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getXa() {
        return xa;
    }
    public void setXa(String xa) {
        this.xa = xa;
    }
    
    
}
