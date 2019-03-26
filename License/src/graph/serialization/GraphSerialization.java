package graph.serialization;

import java.io.Serializable;
import java.util.ArrayList;

import graph.Edge;
import graph.Graph;
import graph.GraphNode;

public class GraphSerialization implements Serializable{
	public ArrayList<GraphNodeSerialization> nodes = new ArrayList<GraphNodeSerialization>();
	public ArrayList<EdgeSerialization> edges = new ArrayList<EdgeSerialization>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GraphSerialization(Graph graph) {
		for(GraphNode node : graph.getNodes()) {
			nodes.add(new GraphNodeSerialization(node));
		}
		
		for(Edge edge : graph.getEdges()) {
			edges.add(new EdgeSerialization(edge,graph.getNodes().indexOf(edge.getSource()), graph.getNodes().indexOf(edge.getDestination())));
		}
	}


}
