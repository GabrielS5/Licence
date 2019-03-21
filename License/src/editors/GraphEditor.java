package editors;

import graph.Edge;
import graph.Graph;
import graph.GraphNode;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import main.App;

public class GraphEditor extends Editor {

	private App app;
	private Graph graph = new Graph();
	private GraphEditMode editMode = GraphEditMode.AddingEdges;
	private GraphNode selectedNode = null;

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

	}

	@Override
	public Node getDisplay() {
		return this.graph.getDisplay();
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
