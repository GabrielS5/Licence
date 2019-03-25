package graph;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class Graph {
	private Pane canvas = new Pane();
	private ArrayList<GraphNode> nodes;
	private ArrayList<Edge> edges;

	public Graph() {
		this.nodes = new ArrayList<GraphNode>();
		this.edges = new ArrayList<Edge>();
		
		//canvas.maxHeight(600);

		addGraphNode(new GraphNode(100, 100));
		addGraphNode(new GraphNode(200, 100));

		addGraphNode(new GraphNode(100, 300));
		addGraphNode(new GraphNode(200, 500));
		addGraphNode(new GraphNode(300, 500));
		addGraphNode(new GraphNode(100, 400));
		addGraphNode(new GraphNode(200, 400));
	}

	public void addGraphNode(GraphNode graphNode) {
		this.makeDraggable(graphNode);
		//this.canvas.getChildrenUnmodifiable().add(arg0)
		this.canvas.getChildren().add(graphNode);
		this.nodes.add(graphNode);
	}

	public void addEdge(Edge edge) {
		this.canvas.getChildren().add(edge);
		this.edges.add(edge);
	}

	public Node getDisplay() {
		return canvas;
	}

	public void makeDraggable(GraphNode graphNode) {
		graphNode.setOnMouseDragged(event -> {
			graphNode.setX(event.getX());
			graphNode.setY(event.getY());
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
}
