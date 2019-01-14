package cn.bocom.r_entity.datasource;

import java.io.Serializable;

/**
 * 列信息
 * 
 * @author liushengjie
 * @version $Id: ColInfo.java, v 0.1 2019年1月12日 下午5:07:42 liushengjie Exp $
 */
public class ColInfo implements Serializable{
    
    /**  */
    private static final long serialVersionUID = 1L;
    
    /**  列名*/
    private String col;
    /**  列类型*/
    private String type;
    /**  列别名*/
    private String alias;
    /**  长度*/
    private int length;
    /**  是否为空*/
    private String nullable;
    /**  主键*/
    private String pk;
    /**  索引*/
    private String idx;

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
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


}
