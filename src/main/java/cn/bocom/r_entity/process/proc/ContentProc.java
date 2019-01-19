package cn.bocom.r_entity.process.proc;

import java.util.List;

public class ContentProc{
    
	//0：=；1：<>；2：in；3：not in；
	//4：like(like %val%)；5：not like(not like %val%)；
	//6：leftlike(like %val)；7：not leftlike(not like %val)；
	//8：rightlike(like val%)；9：not rightlike(not like val%)
	private int type;
	private List<String> data;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
}
