package graph;

import java.util.ArrayList;

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
