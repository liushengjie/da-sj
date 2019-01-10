package cn.bocom.service.service.proc;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import cn.bocom.entity.Edge;
import cn.bocom.entity.Node;
import cn.bocom.other.common.SQLTemplet;
import cn.bocom.other.util.RandomUtil;

@Component
@Primary
public class InterSect extends BaseSQLProc {

	@Override
	public String process(List<Node> nodes, List<Edge> edges, List<Node> sourceNodes, Node targetNode) throws Exception {
		ST _inter = new ST(SQLTemplet.INTERSECT);
		Node fn = sourceNodes.get(0);
		Node sn = sourceNodes.get(1);
		_table_alias_M.put(fn.getId(), "A_"+RandomUtil.getRandomId(6));
		_table_alias_M.put(sn.getId(), "A_"+RandomUtil.getRandomId(6));
		
		_inter.add("ft", generateTbName(nodes, edges, fn));
		_inter.add("st", generateTbName(nodes, edges, sn));
		_inter.add("fa", _table_alias_M.get(fn.getId()));
		_inter.add("sa", _table_alias_M.get(sn.getId()));
		_inter.add("join", targetNode.getParam().get("connection"));
		_inter.add("col", genCol(targetNode));
		_inter.add("filter", genFilter(targetNode));
		
		return _inter.render();
	}
	
	public String genCol(Node targetNode){
		StringBuffer sb = new StringBuffer();
		List<Map<String, String>> cols = targetNode.getCol();
		for(Map<String, String> col:cols){
			sb.append(_table_alias_M.get(col.get("sourceId"))).append(".").append(col.get("id")).append(",");
		}		
		String colStr = sb.toString();		
		return colStr.substring(0, colStr.length()-1);
	}
	
	@SuppressWarnings("unchecked")
	public String genFilter(Node targetNode){
		StringBuffer sb = new StringBuffer();
		Map<String, Object> param = targetNode.getParam();
		List<Map<String, Object>> conditions = (List<Map<String, Object>>) param.get("condition");
		for(int i=0;i<conditions.size();i++){
			Map<String, Object> condition = conditions.get(i);
			String symbol = (String) condition.get("symbol");
			Map<String, Object> s_c = (Map<String, Object>) condition.get("s_c");
			Map<String, Object> t_c = (Map<String, Object>) condition.get("t_c");
			Map<String, Object> s_t = (Map<String, Object>) condition.get("s_t");
			Map<String, Object> t_t = (Map<String, Object>) condition.get("t_t");
			sb.append(_table_alias_M.get(s_t.get("id"))).append(".").append(s_c.get("id")).append(symbol).append(_table_alias_M.get(t_t.get("id"))).append(".").append(t_c.get("id"));
			if(i<conditions.size()-1){
				sb.append(" and");
			}
		}
		return sb.toString();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
