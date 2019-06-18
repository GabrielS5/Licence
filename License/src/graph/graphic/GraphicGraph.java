package graph.graphic;

import java.util.ArrayList;
import java.util.List;

public class GraphicGraph {
	private boolean directed = true;
	private FlexibleCanvas canvas = new FlexibleCanvas();
	private List<GraphicNode> nodes;
	private List<GraphicEdge> edges;

	public GraphicGraph() {
		this.nodes = new ArrayList<GraphicNode>();
		this.edges = new ArrayList<GraphicEdge>();
	}

	public void addNode(GraphicNode node) {
		this.makeDraggable(node);
		this.canvas.getChildren().add(node);
		this.nodes.add(node);
		
		resetCanvas(); 
	}

	public void addEdge(GraphicEdge edge) {
		this.canvas.getChildren().add(edge);
		this.edges.add(edge);
		resetCanvas(); 
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

	public List<GraphicNode> getNodes() {
		return nodes;
	}

	public List<GraphicEdge> getEdges() {
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

	public void removeNode(GraphicNode node) {

		List<GraphicEdge> edges = new ArrayList<GraphicEdge>();
		edges.addAll(node.getExteriorEdges());
		edges.addAll(node.getInteriorEdges());

		for (GraphicEdge edge : edges)
			removeEdge(edge);

		this.getNodes().remove(node);
		resetCanvas();
	}

	public void removeEdge(GraphicEdge edge) {
		this.getEdges().remove(edge);

		edge.getSource().removeEdge(edge);
		edge.getDestination().removeEdge(edge);

		resetCanvas();
	}

	public GraphicEdge createEdge(GraphicNode source, GraphicNode destination) {
		if (source.equals(destination))
			return null;

		for (GraphicEdge edge : edges) {
			if (edge.getSource().equals(source) && edge.getDestination().equals(destination))
				return null;
			else if (edge.getSource().equals(destination) && edge.getDestination().equals(source)) {
				edge.changeToDoubleEdge();
				return edge;
			}
		}
		GraphicEdge edge = new GraphicEdge(this, source, destination);
		addEdge(edge);

		return edge;
	}

	public void setDirected(boolean directed) {
		this.directed = directed;

		if (directed) {
			for (GraphicEdge edge : edges) {
				edge.setArrowsVisibility(true);
			}
		} else {
			for (GraphicEdge edge : edges) {
				edge.setArrowsVisibility(false);
			}
		}
	}

	public boolean isDirected() {
		return directed;
	}

	public void resetCanvas() {
		canvas.getChildren().clear();
		canvas.getChildren().addAll(edges);
		canvas.getChildren().addAll(nodes);
	}
}
