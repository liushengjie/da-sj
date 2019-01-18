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
    private String processCol;
    private String params;
    
    public String getProcessName() {
        return processName;
    }
    public void setProcessName(String processName) {
        this.processName = processName;
    }
    public String getProcessCol() {
        return processCol;
    }
    public void setProcessCol(String processCol) {
        this.processCol = processCol;
    }
    public String getParams() {
        return params;
    }
    public void setParams(String params) {
        this.params = params;
    }
}
