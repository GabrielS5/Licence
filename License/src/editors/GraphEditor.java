package editors;

import graph.Edge;
import graph.Graph;
import graph.GraphElementValueField;
import graph.GraphNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
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
				makeEditable(node, node.valueField);
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
						Edge edge = new Edge(selectedNode, secondNode);
						makeEditable(edge, edge.valueField);

						graph.addEdge(edge);

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
		Button button3 = new Button("Edit values");

		button1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				editMode = GraphEditMode.AddingEdges;
			}
		});

		button2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				editMode = GraphEditMode.AddingNodes;
			}
		});

		button3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				editMode = GraphEditMode.EditingValues;
			}
		});

		hbox.getChildren().addAll(button1, button2, button3);

		this.node = new VBox();
		this.graph.getDisplay().setPrefSize(2000, 2000);

		node.getChildren().addAll(graph.getDisplay(), hbox);

	}

	private void makeEditable(Group group, GraphElementValueField valueField) {
		group.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
			if (event.isStillSincePress() && editMode == GraphEditMode.EditingValues) {
				valueField.showInput();
			}
		});
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
