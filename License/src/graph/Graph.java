package graph;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Graph {
	private Pane canvas = new Pane();
	
	public Graph() {
		addGraphNode(new GraphNode(100,100));
		addGraphNode(new GraphNode(200,100));
		GraphNode graphNode= new GraphNode(100,300);
		this.makeDraggable(graphNode);
		addGraphNode(graphNode);
	}
	
	public void addGraphNode(GraphNode graphNode) {
		this.canvas.getChildren().add(graphNode);
	}
	
	public Node getDisplay() {
		return canvas;
	}
	
	public void makeDraggable(GraphNode graphNode) {
		graphNode.setOnMouseDragged(event -> {
			graphNode.setLayoutX(graphNode.getLayoutX() + event.getX());
			graphNode.setLayoutY(graphNode.getLayoutY() + event.getY());
        });
	}
}
