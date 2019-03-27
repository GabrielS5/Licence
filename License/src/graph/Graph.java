package graph;

import java.util.ArrayList;

import graph.serialization.EdgeSerialization;
import graph.serialization.GraphNodeSerialization;
import graph.serialization.GraphSerialization;

public class Graph {
	private FlexibleCanvas canvas = new FlexibleCanvas();
	private ArrayList<GraphNode> nodes;
	private ArrayList<Edge> edges;

	public Graph() {
		this.nodes = new ArrayList<GraphNode>();
		this.edges = new ArrayList<Edge>();	
	}

	public void addGraphNode(GraphNode graphNode) {
		this.makeDraggable(graphNode);
		this.canvas.getChildren().add(graphNode);
		this.nodes.add(graphNode);
	}

	public void addEdge(Edge edge) {
		this.canvas.getChildren().add(edge);
		this.edges.add(edge);
	}

	public FlexibleCanvas getDisplay() {
		return canvas;
	}

	public void makeDraggable(GraphNode graphNode) {
		graphNode.setOnMouseDragged(event -> {
			graphNode.setX(event.getX());
			graphNode.setY(event.getY());
			event.consume();
		});
	}
	
	public void load(GraphSerialization serialization) {
		this.nodes.clear();
		this.edges.clear();
		
		for (GraphNodeSerialization node : serialization.nodes) {
			addGraphNode(new GraphNode(node.x, node.y, node.value));
		}

		for (EdgeSerialization edge : serialization.edges) {
			addEdge(
					new Edge(nodes.get(edge.sourceIndex), nodes.get(edge.destinationIndex), edge.value));
		}
	}

	public GraphNode getNodeByCoordinates(double x, double y) {

		for (int i = 0; i < nodes.size(); i++) {
			double radius = 20;
			double distance = Math
					.sqrt(Math.pow((x - nodes.get(i).getX()), 2) + Math.pow((y - nodes.get(i).getY()), 2));

			if (distance <= radius) {
				return nodes.get(i);
			}
		}

		return null;
	}

	public ArrayList<GraphNode> getNodes() {
		return nodes;
	}

	public ArrayList<Edge> getEdges() {
		return edges;
	}
	
}
