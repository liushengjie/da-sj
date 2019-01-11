package cn.bocom.r_entity.datasource;

import java.io.Serializable;

/**
 * 上游数据源表单父类
 * @author liushengjie
 * @version $Id: OriginEntity.java, v 0.1 2019年1月11日 下午2:21:27 liushengjie Exp $
 */
public class OriginEntity implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;           //数据源ID
    private String name;         //数据源名称
    private String typeName;     //类型名称
    private String createUser;    //创建人
    private String createTime;    //创建日期
    private int status;           //当前状态
    private int model;            //数据使用方式（0：接入，1：接出）
    
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
    public String getTypeName() {
        return typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
