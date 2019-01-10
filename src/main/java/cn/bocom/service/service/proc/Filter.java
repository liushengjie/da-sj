package cn.bocom.service.service.proc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.stringtemplate.v4.ST;

import cn.bocom.entity.Edge;
import cn.bocom.entity.Node;
import cn.bocom.other.common.SQLTemplet;
import cn.bocom.other.util.RandomUtil;

@Component
public class Filter extends BaseSQLProc {

	@Override
	public String process(List<Node> nodes, List<Edge> edges, List<Node> sourceNodes, Node targetNode) throws Exception {
		ST _inter = new ST(SQLTemplet.FILTER);
		Node fn = sourceNodes.get(0);
//		_table_alias_M.put(fn.getId(), "A_"+RandomUtil.getRandomId(6));
		
		_inter.add("ft", generateTbName(nodes, edges, fn));
//		_inter.add("fa", _table_alias_M.get(fn.getId()));
		_inter.add("col", genCol(targetNode));
		_inter.add("filter", genFilter(targetNode));
		
		return _inter.render();
	}
	
	public String genCol(Node targetNode){
		StringBuffer sb = new StringBuffer();
		List<Map<String, String>> cols = targetNode.getCol();
		for(Map<String, String> col:cols){
			sb.append(col.get("id")).append(",");//.append(_table_alias_M.get(col.get("sourceId"))).append(".")
		}		
		String colStr = sb.toString();		
		return colStr.substring(0, colStr.length()-1);
	}
	
	@SuppressWarnings("unchecked")
	public String genFilter(Node targetNode){
		StringBuffer sb = new StringBuffer();
		Map<String, Object> param = targetNode.getParam();
		Map<String, Object> filter = (Map<String, Object>) param.get("filter");
		List<Map<String, Object>> groups = (List<Map<String, Object>>) filter.get("group");
		for(Map<String, Object> group:groups){
			//大条件关联
			String relations = (String) group.get("relation");
			sb.append(" ").append(relations).append(" ");
			
			//小条件关联
			sb.append("(");
			List<Map<String, Object>> conditions = (List<Map<String, Object>>) group.get("condition");
			for(Map<String, Object> condition:conditions){
				String relation = (String) condition.get("relation");
				String col = (String) condition.get("col");
				String symbol = (String) condition.get("symbol");
				String value = (String) condition.get("value");
				String type = (String) condition.get("type");
				if("varchar".equals(type)){
					value = "'"+value+"'";
				}
				sb.append(" ").append(relation).append(" ").append(col).append(symbol).append(value);
			}
			sb.append(")");	
		}

		return sb.toString();
	}

}
