package cn.bocom.r_entity.resource;

import java.io.Serializable;

/**
 * 资源列表单对象
 * @author liushengjie
 * @version $Id: ResColInfo.java, v 0.1 2019年1月15日 下午2:05:25 liushengjie Exp $
 */
public class ResColInfo implements Serializable{
    
    private String id;
    private String resId;
    private String alias;
    private String colCache;
    private String type;
    private String col;
    private String origin;
    private String pk;
    private String idx;
    private String dict;
    private String status;
    private String changeType;
    private String sort;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getResId() {
        return resId;
    }
    public void setResId(String resId) {
        this.resId = resId;
    }
    
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    public String getColCache() {
        return colCache;
    }
    public void setColCache(String colCache) {
        this.colCache = colCache;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getCol() {
        return col;
    }
    public void setCol(String col) {
        this.col = col;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public String getPk() {
        return pk;
    }
    public void setPk(String pk) {
        this.pk = pk;
    }
    public String getIdx() {
        return idx;
    }
    public void setIdx(String idx) {
        this.idx = idx;
    }
    public String getDict() {
        return dict;
    }
    public void setDict(String dict) {
        this.dict = dict;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getChangeType() {
        return changeType;
    }
    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
    public String getSort() {
        return sort;
    }
    public void setSort(String sort) {
        this.sort = sort;
    }
    
    
}
