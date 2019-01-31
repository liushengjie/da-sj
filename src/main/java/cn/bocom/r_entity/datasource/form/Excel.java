package cn.bocom.r_entity.datasource.form;

import cn.bocom.r_entity.datasource.OriginEntity;

public class Excel extends OriginEntity{

    /**  */
    private static final long serialVersionUID = 1L;

    private String database;
    private String xa;
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getXa() {
		return xa;
	}
	public void setXa(String xa) {
		this.xa = xa;
	}
    
}
