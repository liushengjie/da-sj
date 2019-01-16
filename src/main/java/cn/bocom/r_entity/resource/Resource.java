package cn.bocom.r_entity.resource;

import java.io.Serializable;
import java.util.List;

import cn.bocom.r_entity.process.ProcessEntity;

/**
 * 資源基類
 * @author liushengjie
 * @version $Id: Resource.java, v 0.1 2019年1月12日 下午4:32:15 liushengjie Exp $
 */
public class Resource implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;
    
    private ResourceBody resourceBody;
    private List<ResourceCol> resourceCols;
    private ResourceData resourceData;
    private List<? extends ProcessEntity> processors;
    
    public ResourceBody getResourceBody() {
        return resourceBody;
    }
    public void setResourceBody(ResourceBody resourceBody) {
        this.resourceBody = resourceBody;
    }

    public List<ResourceCol> getResourceCols() {
        return resourceCols;
    }
    public void setResourceCols(List<ResourceCol> resourceCols) {
        this.resourceCols = resourceCols;
    }
    public ResourceData getResourceData() {
        return resourceData;
    }
    public void setResourceData(ResourceData resourceData) {
        this.resourceData = resourceData;
    }
    public List<? extends ProcessEntity> getProcessors() {
        return processors;
    }
    public void setProcessors(List<? extends ProcessEntity> processors) {
        this.processors = processors;
    }
}
