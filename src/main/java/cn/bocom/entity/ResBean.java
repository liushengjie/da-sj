/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.entity 
 * @author: liushengjie   
 * @date: 2018年9月18日 上午10:18:55 
 */
package cn.bocom.entity;

import java.util.List;

/** 
 * @ClassName: ResBean 
 * @Description: TODO
 * @author: liushengjie
 * @date: 2018年9月18日 上午10:18:55  
 */
public class ResBean {
	
	private Res res;
	private ResData resData;
	private List<ResCol> resColList;
	private List<ResFilter> resFilterList;
	
	/**  
	* @return res  
	*/
	public Res getRes() {
		return res;
	}
	/**  
	* @param res 要设置的 res  
	*/
	public void setRes(Res res) {
		this.res = res;
	}
	/**  
	* @return resData  
	*/
	public ResData getResData() {
		return resData;
	}
	/**  
	* @param resData 要设置的 resData  
	*/
	public void setResData(ResData resData) {
		this.resData = resData;
	}
	/**  
	* @return resColList  
	*/
	public List<ResCol> getResColList() {
		return resColList;
	}
	/**  
	* @param resColList 要设置的 resColList  
	*/
	public void setResColList(List<ResCol> resColList) {
		this.resColList = resColList;
	}
	
	public List<ResFilter> getResFilterList() {
		return resFilterList;
	}
	
	public void setResFilterList(List<ResFilter> resFilterList) {
		this.resFilterList = resFilterList;
	}

}
