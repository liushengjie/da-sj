package cn.bocom.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class CategoryNode implements Serializable{
	
	private static final long serialVersionUID = 7975333531496960074L;

	private Integer id;

    private String type;

    private String title;

    private Integer pid;

    private String sort;
    
    private List<CategoryNode> children;   
    
    private List<Map<String, Object>> data;
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	/**  
	* @return title  
	*/
	public String getTitle() {
		return title;
	}

	/**  
	* @param title 要设置的 title  
	*/
	public void setTitle(String title) {
		this.title = title;
	}

	/**  
	* @return children  
	*/
	public List<CategoryNode> getChildren() {
		return children;
	}

	/**  
	* @param children 要设置的 children  
	*/
	public void setChildren(List<CategoryNode> children) {
		this.children = children;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
    
}