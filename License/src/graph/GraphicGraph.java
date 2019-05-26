package graph;

import java.util.ArrayList;

public class GraphicGraph {
	private FlexibleCanvas canvas = new FlexibleCanvas();
	private ArrayList<GraphicNode> nodes;
	private ArrayList<GraphicEdge> edges;

	public GraphicGraph() {
		this.nodes = new ArrayList<GraphicNode>();
		this.edges = new ArrayList<GraphicEdge>();
	}

	public void addNode(GraphicNode node) {
		this.makeDraggable(node);
		this.canvas.getChildren().add(node);
		this.nodes.add(node);
	}

	public void addEdge(GraphicEdge edge) {
		this.canvas.getChildren().add(edge);
		this.edges.add(edge);
	}

	public FlexibleCanvas getDisplay() {
		return canvas;
	}

	public void makeDraggable(GraphicNode node) {
		node.setOnMouseDragged(event -> {
			node.setX(event.getX());
			node.setY(event.getY());
			event.consume();
		});
	}

	public GraphicNode getNodeByCoordinates(double x, double y) {

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

	public ArrayList<GraphicNode> getNodes() {
		return nodes;
	}

	public ArrayList<GraphicEdge> getEdges() {
		return edges;
	}
	
	public GraphicNode getNodeById(int id) {
		for (GraphicNode node : nodes) {
			if (node.getUniqueId() == id)
				return node;
		}

		return null;
	}

	public GraphicEdge getEdgeById(int id) {
		for (GraphicEdge edge : edges) {
			if (edge.getUniqueId() == id)
				return edge;
		}

		return null;
	}
}
