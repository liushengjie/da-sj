package cn.bocom.r_entity.resource;

import java.io.Serializable;
import java.util.Date;

/**
 * 資源基類
 * @author liushengjie
 * @version $Id: Resource.java, v 0.1 2019年1月12日 下午4:32:15 liushengjie Exp $
 */
public class ResourceView implements Serializable{
    /**  */
    private static final long serialVersionUID = 1L;
    
    private String id;
	private String name;
	private String category;
	private String category_name;
	private String connect_type;
	private String connect_type_name;
	private String num;
	private String create_user;
	private Date create_time;
	private String create_time_str;
	private String status;
	private String status_name;
	private String ds_id;
	private String ds_name;
	
	
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	public void setCategoryName(String category_name) {
		this.category_name = category_name;
	}
	public String getConnect_type() {
		return connect_type;
	}
	public void setConnect_type(String connect_type) {
		this.connect_type = connect_type;
	}
	public void setConnectType(String connect_type) {
		this.connect_type = connect_type;
	}
	public String getConnect_type_name() {
		return connect_type_name;
	}
	public void setConnect_type_name(String connect_type_name) {
		this.connect_type_name = connect_type_name;
	}
	public void setConnectTypeName(String connect_type_name) {
		this.connect_type_name = connect_type_name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public void setCreateUser(String create_user) {
		this.create_user = create_user;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public void setCreateTime(Date create_time) {
		this.create_time = create_time;
	}
	public String getCreate_time_str() {
		return create_time_str;
	}
	public void setCreate_time_str(String create_time_str) {
		this.create_time_str = create_time_str;
	}
	public void setCreateTimeStr(String create_time_str) {
		this.create_time_str = create_time_str;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus_name() {
		return status_name;
	}
	public void setStatus_name(String status_name) {
		this.status_name = status_name;
	}
	public void setStatusName(String status_name) {
		this.status_name = status_name;
	}
	public String getDs_id() {
		return ds_id;
	}
	public void setDs_id(String ds_id) {
		this.ds_id = ds_id;
	}
	public void setDsId(String ds_id) {
		this.ds_id = ds_id;
	}
	public String getDs_name() {
		return ds_name;
	}
	public void setDs_name(String ds_name) {
		this.ds_name = ds_name;
	}
	public void setDsName(String ds_name) {
		this.ds_name = ds_name;
	}
	
}
