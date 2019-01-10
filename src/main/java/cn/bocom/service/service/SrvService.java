/**   
 * Copyright © 2018 LiuShengjie. All rights reserved.
 * 
 * @Package: cn.bocom.service.service 
 * @author: liushengjie   
 * @date: 2018年9月13日 下午2:17:39 
 */
package cn.bocom.service.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ignite.Ignite;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.stringtemplate.v4.ST;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import cn.bocom.controller.SrvController;
import cn.bocom.entity.Edge;
import cn.bocom.entity.Node;
import cn.bocom.entity.ResCol;
import cn.bocom.entity.Srv;
import cn.bocom.entity.SrvBean;
import cn.bocom.entity.SrvEdge;
import cn.bocom.entity.SrvNode;
import cn.bocom.entity.SrvParam;
import cn.bocom.mapper.main.ResColMapper;
import cn.bocom.mapper.main.SrvEdgeMapper;
import cn.bocom.mapper.main.SrvMapper;
import cn.bocom.mapper.main.SrvNodeMapper;
import cn.bocom.other.util.BeanUtil;
import cn.bocom.other.util.RandomUtil;
import cn.bocom.service.service.proc.BaseSQLProc;
import cn.sj.common.CacheServer;

@Component
public class SrvService {
	
	@Autowired
    private ResColMapper resColMapper;
	@Autowired
    private SrvMapper srvMapper;
	@Autowired
    private SrvNodeMapper SrvNodeMapper;
	@Autowired
    private SrvEdgeMapper srvEdgeMapper;
	@Autowired
    private BaseSQLProc baseSQLProc;
	@Autowired
    private Ignite ignite;
	
	private static Logger logger = LoggerFactory.getLogger(SrvService.class);

	public List<Map<String, Object>> previewData(Map<String,Object> map, String targetNode, String limit) throws Exception{
		
		String sql = generateSql(map, targetNode);
		logger.info("预览SQL:"+sql);	
		CacheServer cacheService = ignite.services().serviceProxy(CacheServer.SERVICE_NAME, CacheServer.class, false);
		List<Map<String, Object>> result = cacheService.qryCacheDatas(sql, Integer.parseInt(limit), null);
		
		return result;
	}
	
	public String previewDebug(Map<String,Object> map, String targetNode) throws Exception{
		
		String sql = generateSql(map, targetNode);
		logger.info("预览SQL:"+sql);			
		return sql;
	}

	public String buildSQL(SrvBean srvBean, String target) {
		List<SrvNode> list = srvBean.getNodes();
		Map<String, SrvNode> map = new HashMap<String, SrvNode>();
		for(SrvNode node:list){
			map.put(node.getResId(), node);
		}
		
		saveSrv(srvBean);
		String sql = analyze_buildSQL(map, target);
		
		return sql;
	}
	
	public void saveSrv(SrvBean srvBean){
		
		Srv srv = new Srv();
		String srvId = RandomUtil.getUUID32();
		srv.setId(srvId);
		srv.setName(srvBean.getName());
		srv.setCreateTime(new Date());
		srv.setUpdateTime(new Date());
		srvMapper.insert(srv);

		List<SrvNode> list1 = srvBean.getNodes();
		for(SrvNode node:list1){
			node.setSrvId(srvId);
			node.setCreateTime(new Date());
			SrvNodeMapper.insert(node);
		}
		
		List<SrvEdge> list2 = srvBean.getEdges();
		for(SrvEdge edge:list2){
			edge.setSrvId(srvId);
			edge.setCreateTime(new Date());
			srvEdgeMapper.insert(edge);
		}
	}
	
	public String analyze_buildSQL(Map<String, SrvNode> map, String target){
		
		SrvNode node = map.get(target);		
		String type = node.getType();
		
		if("res".equals(type)){
			
			return node.getTableName();
			
		}else if("intersection".equals(type)){
			
			StringBuffer sb = new StringBuffer("select * from ");
			SrvParam param = JSONObject.parseObject(node.getParam(), new TypeReference<SrvParam>() {});
			String strRes = param.getRes();
			String[] res = strRes.split("-");
			String table1 = map.get(res[0]).getTableName();//父表1
			String table2 = map.get(res[1]).getTableName();//父表2
			
			//处理表字段
			String strCol = param.getCol();
			String[] col = strCol.split("-");
			String[] col1 = col[0].split(",");
			String[] col2 = col[1].split(",");
			List<String> colList1 = new ArrayList<String>();
			List<String> colList2 = new ArrayList<String>();
			for(int i=0;i<col1.length;i++){
				if(Arrays.asList(col2).contains(col1[i])){//如果两个表有字段重名则重命名
					colList1.add(table1+"."+col1[i]+" as "+col1[i]+"_1");
				}else{
					colList1.add(table1+"."+col1[i]);
				}
			}
			for(int i=0;i<col2.length;i++){
				colList2.add(table2+"."+col2[i]);
			}			
			sb.append(" (select ").append(StringUtils.join(colList1.toArray(), ",")).append(",").append(StringUtils.join(colList2.toArray(), ",")).append(" from ");

			//处理表名
			sb.append(analyze_buildSQL(map,res[0])).append(" join ").append(analyze_buildSQL(map,res[1]));
			
			//处理关联条件
			String strEqual = param.getEqual();
			if(StringUtils.isNotBlank(strEqual)){
				sb.append(" on ");
				String[] equals = strEqual.split(",");
				for(int i=0;i<equals.length;i++){
					String[] equal = equals[i].split("-");
					sb.append(table1).append(".").append(equal[0]).append(" = ").append(table2).append(".").append(equal[1]);
					if(i<equals.length-1){
						sb.append(" and");
					}			
				}
			}
			
			sb.append(") as ").append(node.getTableName());
			
			return sb.toString();
			
		}else if("union".equals(type)){
			
			StringBuffer sb = new StringBuffer();
			SrvParam param = JSONObject.parseObject(node.getParam(), new TypeReference<SrvParam>() {});
			String strRes = param.getRes();
			String[] res = strRes.split("-");
			
			
			//处理表字段
			String strCol = param.getCol();
			String[] col = strCol.split("-");
			String[] col1 = col[0].split(",");
			String[] col2 = col[1].split(",");
			List<String> colList1 = Arrays.asList(col1);
			List<String> colList2 = Arrays.asList(col2);
		
			sb.append(" ((select ").append(StringUtils.join(colList1.toArray(), ",")).append(" from ").append(analyze_buildSQL(map,res[0])).append(")")
			.append(" union all ")
			.append(" (select ").append(StringUtils.join(colList2.toArray(), ",")).append(" from ").append(analyze_buildSQL(map,res[1])).append(")) as ").append(node.getTableName());
			
			return sb.toString();
			
		}else if("difference".equals(type)){
			
			StringBuffer sb = new StringBuffer();
			SrvParam param = JSONObject.parseObject(node.getParam(), new TypeReference<SrvParam>() {});
			String strRes = param.getRes();
			String[] res = strRes.split("-");

			sb.append("((select * from ").append(analyze_buildSQL(map,res[0])).append(") as t ").append(" where not exists ( select 1 from ").append(analyze_buildSQL(map,res[1])).append(") where ");
			
			//处理关联条件
			String strEqual = param.getEqual();
			String[] equals = strEqual.split(",");
			for(int i=0;i<equals.length;i++){
				String[] equal = equals[i].split("-");
				sb.append(equal[0]).append(" = ").append("t.").append(equal[1]);
				if(i<equals.length-1){
					sb.append(" and");
				}			
			}
	
			sb.append(") as ").append(node.getTableName());

			
		}else if("group".equals(type)){
			
			StringBuffer sb = new StringBuffer();
			SrvParam param = JSONObject.parseObject(node.getParam(), new TypeReference<SrvParam>() {});
			String strRes = param.getRes();
			String[] res = strRes.split("-");
			
			sb.append("((select * from ").append(analyze_buildSQL(map,res[0])).append("group by ");
			
			String strGroup = param.getGroup();
			String[] groups = strGroup.split(",");
			for(int i=0;i<groups.length;i++){
				sb.append(groups[i]);
				if(i<groups.length-1){
					sb.append(" ,");
				}			
			}
	
			sb.append(") as ").append(node.getTableName());
			
		}else if("order".equals(type)){
			
		}
		
		return null;

	}
	
	public List<ResCol> findCols(SrvBean srvBean, String target){
		List<SrvNode> list = srvBean.getNodes();
		Map<String, SrvNode> map = new HashMap<String, SrvNode>();
		for(SrvNode node:list){
			map.put(node.getResId(), node);
		}
		List<ResCol> result = new ArrayList<ResCol>();
		
		result = analyze_findCols(map, target, result);
		
		return result;
	}
	
	public List<ResCol> analyze_findCols(Map<String, SrvNode> map, String target, List<ResCol> result){
		
		SrvNode node = map.get(target);	
		if(node==null){
			ResCol resCol = new ResCol();
			resCol.setResId(target);
			List<ResCol> data = resColMapper.findResCol(resCol);
			result.addAll(data);
		}else{
			String type = node.getType();
			
			if("res".equals(type)){
				
				ResCol resCol = new ResCol();
				resCol.setResId(node.getResId());
				List<ResCol> data = resColMapper.findResCol(resCol);
				result.addAll(data);
				
			}else if("intersection".equals(type) || "union".equals(type) || "difference".equals(type)){
				
				SrvParam param = JSONObject.parseObject(node.getParam(), new TypeReference<SrvParam>() {});
				String strRes = param.getRes();
				String[] res = strRes.split("-");
				
				analyze_findCols(map,res[0],result);
				analyze_findCols(map,res[1],result);
				
			}else if("group".equals(type) || "order".equals(type)){
				
			}
		}
		return result;
	}
	
	public String generateSql(Map<String,Object> map, String targetNode) throws Exception{
			
		List<Map<String, Object>> nodes_ = new ArrayList<Map<String, Object>>(); 		
		List<Map<String, Object>> nodesList = (List<Map<String, Object>>) map.get("nodes");
		for(Map<String, Object> node:nodesList){
			nodes_.add((Map<String, Object>) node.get("data"));			
		}
		List<Node> nodes = new BeanUtil().mapToObject(nodes_, Node.class);
				
		List<Edge> edges = null;
		if(map.containsKey("edges") && map.get("edges")!=null){
			List<Map<String, Object>> edges_ = new ArrayList<Map<String, Object>>(); 
			List<Map<String, Object>> edgesList = (List<Map<String, Object>>) map.get("edges");
			for(Map<String, Object> node:edgesList){
				edges_.add((Map<String, Object>) node.get("data"));
			}
			edges = new BeanUtil().mapToObject(edges_, Edge.class);
		}	
		
		Node target = null;
		for(Node node:nodes){
			if(targetNode.equals(node.getId())){
				target = node;
			}
		}
		
		return generateQrySql(nodes, edges, target);
	}
	
	public String generateQrySql(List<Node> nodes, List<Edge> edges, Node targetNode) throws Exception{
		ST _retS = new ST("select * from (<tableName>)");
		_retS.add("tableName", baseSQLProc.generateTbName(nodes, edges, targetNode));
		return _retS.render();
	}
}




































