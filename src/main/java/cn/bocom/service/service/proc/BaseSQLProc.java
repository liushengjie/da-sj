package cn.bocom.service.service.proc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import cn.bocom.entity.Edge;
import cn.bocom.entity.Node;

@Component
public abstract class BaseSQLProc {
	
	public Map<String, String> _table_alias_M = new HashMap<String, String>();
	
	public abstract String process(List<Node> nodes, List<Edge> edges, List<Node> sourceNodes, Node targetNode) throws Exception;
	
	public String generateTbName(List<Node> nodes, List<Edge> edges, Node targetNode) throws Exception {
		String t_id = targetNode.getId();
		List<Node> _sl = new ArrayList<Node>();
		if(edges != null) {
			edges.stream().forEach(e -> {
				if(e.getTarget().equals(t_id)) {
					_sl.add(nodes.stream().filter(n -> e.getSource().equals(n.getId())).collect(Collectors.toList()).get(0));
				} 
			});
		}
		
		if(_sl.size() < 1) return (String)targetNode.getParam().get("cacheTable");
		else {
			String _type = targetNode.getType();
			try {
				BaseSQLProc process = (BaseSQLProc) Class.forName("cn.bocom.service.service.proc." + _type).newInstance();
				Method method = BaseSQLProc.class.getMethod("process", List.class, List.class, List.class, Node.class);	
				String _tn = (String)method.invoke(process, nodes, edges, _sl, targetNode);
				return _tn;
			} catch (Exception e2) {
				throw new Exception("迭代生成表明失败 ： " + e2.getMessage());
			} 
		}
	}
}
