package cn.bocom.r_entity.resource;

import java.io.Serializable;

/**
 * 資源基類
 * @author liushengjie
 * @version $Id: Resource.java, v 0.1 2019年1月12日 下午4:32:15 liushengjie Exp $
 */
public class Resource implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;
    
    private ResourceBody resourceBody;
    private ResourceCol resourceCol;
    private ResourceData resourceData;
    
    public ResourceBody getResourceBody() {
        return resourceBody;
    }
    public void setResourceBody(ResourceBody resourceBody) {
        this.resourceBody = resourceBody;
    }
    public ResourceCol getResourceCol() {
        return resourceCol;
    }
    public void setResourceCol(ResourceCol resourceCol) {
        this.resourceCol = resourceCol;
    }
    public ResourceData getResourceData() {
        return resourceData;
    }
    public void setResourceData(ResourceData resourceData) {
        this.resourceData = resourceData;
    }
}
