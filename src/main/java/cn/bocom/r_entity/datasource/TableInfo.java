package cn.bocom.r_entity.datasource;

/**
 * 表信息
 * @author liushengjie
 * @version $Id: TableInfo.java, v 0.1 2019年1月12日 下午1:53:32 liushengjie Exp $
 */
public class TableInfo {
    /** id */
    private String id;
    /** 表名 */
    private String tableName;
    /** 单表数据量 */
    private int tableRows;
    /** 最后更新时间 */
    private String updateTime;
    /** 表备注 */
    private String tableComment;
    /** 类型（'TABLE'表  'VIEW' 视图） */
    private String objType;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTableName() {
        return tableName;
    }
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
    public int getTableRows() {
        return tableRows;
    }
    public void setTableRows(int tableRows) {
        this.tableRows = tableRows;
    }
    public String getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public String getTableComment() {
        return tableComment;
    }
    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }
    public String getObjType() {
        return objType;
    }
    public void setObjType(String objType) {
        this.objType = objType;
    }
    
    
}
