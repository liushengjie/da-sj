package cn.bocom.entity;

import java.util.List;

public class SrvBean {

	private String name;
	private List<SrvNode> nodes;
	private List<SrvEdge> edges;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SrvNode> getNodes() {
		return nodes;
	}
	public void setNodes(List<SrvNode> nodes) {
		this.nodes = nodes;
	}
	public List<SrvEdge> getEdges() {
		return edges;
	}
	public void setEdges(List<SrvEdge> edges) {
		this.edges = edges;
	}
	
	
}
