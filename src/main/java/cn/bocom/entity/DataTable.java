package cn.bocom.entity;

/**
 * 
 * @author Administrator 表对象实体类
 */
public class DataTable {
    private String ID;
    private String TABLE_NAME; // 表名
    private String TABLE_ROWS;// 行数
    private String UPDATE_TIME;// 更新时间
    private String TABLE_COMMENT;// 表注释
    private String OBJ_TYPE; // 对象类型（表，视图，同义词等）

    public String getID() {
        return ID;
    }

    public void setID(String iD) {
        ID = iD;
    }

    public String getTABLE_NAME() {
        return TABLE_NAME;
    }

    public void setTABLE_NAME(String tABLE_NAME) {
        TABLE_NAME = tABLE_NAME;
    }

    public String getTABLE_ROWS() {
        return TABLE_ROWS;
    }

    public void setTABLE_ROWS(String tABLE_ROWS) {
        TABLE_ROWS = tABLE_ROWS;
    }

    public String getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(String uPDATE_TIME) {
        UPDATE_TIME = uPDATE_TIME;
    }

    public String getTABLE_COMMENT() {
        return TABLE_COMMENT;
    }

    public void setTABLE_COMMENT(String tABLE_COMMENT) {
        TABLE_COMMENT = tABLE_COMMENT;
    }

    public String getOBJ_TYPE() {
        return OBJ_TYPE;
    }

    public void setOBJ_TYPE(String oBJ_TYPE) {
        OBJ_TYPE = oBJ_TYPE;
    }



}
