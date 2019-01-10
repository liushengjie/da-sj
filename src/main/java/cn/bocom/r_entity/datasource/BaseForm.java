package cn.bocom.r_entity.datasource;

import java.io.Serializable;

public class BaseForm implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;           //数据源ID
    private String name;         //数据源名称
    private ConnStyleEnum conn_style;   //数据源连接类型 (jdbc、excel)
    private DataSourceEnum ds_type;      //数据源类型(MYSQL、ORACLE)
    private String createUser;    //创建人
    private String createTime;    //创建日期
    private int status;           //当前状态
    private int model;            //数据使用方式（0：接入，1：接出）
    
    public enum DataSourceEnum {
        MYSQL("MYSQL", 0),
        ORACLE("ORACLE", 1);
        
        private String name;
        private int code;
        private DataSourceEnum(String name, int code) {
            this.name = name;
            this.code = code;
        }
        
        public String getName() {
            return name;
        }
        public int getCode() {
            return code;
        }
    }
    
    public enum ConnStyleEnum {      //连接类型枚举
        JDBC(0),
        EXCEL(1);
        
        private int code;
        private ConnStyleEnum(int code) {
            this.code = code;
        }
        public int getCode() {
            return code;
        }
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ConnStyleEnum getConn_style() {
        return conn_style;
    }
    public void setConn_style(ConnStyleEnum conn_style) {
        this.conn_style = conn_style;
    }
    public DataSourceEnum getDs_type() {
        return ds_type;
    }
    public void setDs_type(DataSourceEnum ds_type) {
        this.ds_type = ds_type;
    }
    public String getCreateUser() {
        return createUser;
    }
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
    public String getCreateTime() {
        return createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public int getModel() {
        return model;
    }
    public void setModel(int model) {
        this.model = model;
    }
}
