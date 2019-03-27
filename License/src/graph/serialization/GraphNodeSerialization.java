package graph.serialization;

import java.io.Serializable;

import graph.GraphNode;

public class GraphNodeSerialization implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public double x;
	public double y;
	public String value;
	
	public GraphNodeSerialization(GraphNode graphNode) {
	this.x = graphNode.getX();
	this.y = graphNode.getY();
	this.value = graphNode.valueField.getText();
	}
}
