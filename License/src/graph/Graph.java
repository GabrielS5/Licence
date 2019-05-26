package graph;

import java.util.ArrayList;
import java.util.List;

import commands.Command;

public class Graph {
	private static int commandOrder = 0;
	private List<Command> commands = new ArrayList<Command>();
	
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Edge> edges = new ArrayList<Edge>();

	public Graph(GraphicGraph graph) {
		for (GraphicNode graphicNode : graph.getNodes()) {
			if (getNodeById(graphicNode.getUniqueId()) == null) {
				Node node = new Node(graphicNode, this);
				this.addNode(node);
				node.initialize(graphicNode, this);
			}
		}

		for (GraphicEdge graphicEdge : graph.getEdges()) {
			if (getEdgeById(graphicEdge.getUniqueId()) == null) {
				Edge edge = new Edge(graphicEdge, this);
				this.addEdge(edge);
				edge.initialize(graphicEdge, this);
			}
		}
	}

	public void addNode(Node graphNode) {
		this.nodes.add(graphNode);
	}

	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public Node getNodeById(int id) {
		for (Node node : nodes) {
			if (node.getUniqueId() == id)
				return node;
		}

		return null;
	}

	public Edge getEdgeById(int id) {
		for (Edge edge : edges) {
			if (edge.getUniqueId() == id)
				return edge;
		}

		return null;
	}
	
	public List<Command> getCommands(){
		List<Command> result = new ArrayList<Command>();
		
		result.addAll(commands);
		
		for(Node node : nodes) {
			result.addAll(node.getCommands());
		}
		
		for(Edge edge : edges) {
			result.addAll(edge.getCommands());
		}
		
		return result;
	}
	
	public static int getCommandOrder() {
		commandOrder++;
		
		return commandOrder;
	}
}
