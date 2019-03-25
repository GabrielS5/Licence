package editors;

import graph.Edge;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.App;

public class GraphEditor extends Editor {

	private App app;
	private Graph graph = new Graph();
	private GraphEditMode editMode = GraphEditMode.AddingEdges;
	private GraphNode selectedNode = null;
	private VBox node;

	public GraphEditor(App app, String name) {
		this.app = app;
		this.name = name;
		init();
	}

	public void init() {
		graph.getDisplay().addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
			if (event.isStillSincePress() && editMode == GraphEditMode.AddingNodes) {
				GraphNode node = new GraphNode(event.getX(), event.getY());
				graph.addGraphNode(node);
			} else if (event.isStillSincePress() && editMode == GraphEditMode.AddingEdges) {
				if (selectedNode == null) {
					selectedNode = graph.getNodeByCoordinates(event.getX(), event.getY());
					if (selectedNode != null)
						selectedNode.highlightOn();
				} else {
					GraphNode secondNode = graph.getNodeByCoordinates(event.getX(), event.getY());
					GraphNode firstNode = selectedNode;

					if (secondNode == firstNode) {
						selectedNode.highlightOff();
						selectedNode = null;
					} else if (secondNode != null) {
						graph.addEdge(new Edge(selectedNode, secondNode));
						secondNode.highlightOn();
						selectedNode = null;

						new java.util.Timer().schedule(new java.util.TimerTask() {
							@Override
							public void run() {
								firstNode.highlightOff();
								secondNode.highlightOff();
							}
						}, 500);
					}
				}
			}
		});
		
		HBox hbox = new HBox();
		hbox.setMaxHeight(100);
		hbox.setMinHeight(100);
		
		Button button1 = new Button("Add edges");
		Button button2 = new Button("Add nodes");
		hbox.getChildren().addAll(button1, button2);
		
		this.node = new VBox();
		node.getChildren().addAll(graph.getDisplay(), hbox);

	}

	@Override
	public Node getDisplay() {
		return node;
	}

	@Override
	public void loadData(String path) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveData() {
		// TODO Auto-generated method stub

	}

}
