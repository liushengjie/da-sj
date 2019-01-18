package cn.bocom.r_entity.process;

import java.io.Serializable;

/**
 * 处理器对象
 * @author liushengjie
 * @version $Id: ProcessEntity.java, v 0.1 2019年1月16日 下午3:54:32 liushengjie Exp $
 */
public class ProcessEntity implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;
    
    private String processName;
    private String processType;
    private String datasourceType;
    private String params;
    
    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public String getProcessType() {
        return processType;
    }
    public void setProcessType(String processType) {
        this.processType = processType;
    }
    public String getDatasourceType() {
        return datasourceType;
    }
    public void setDatasourceType(String datasourceType) {
        this.datasourceType = datasourceType;
    }
    public String getParams() {
        return params;
    }
    public void setParams(String params) {
        this.params = params;
    }
    
    
    
}
