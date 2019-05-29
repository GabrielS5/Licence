package editors;

import java.util.Optional;

import graph.GraphElementValueField;
import graph.GraphIO;
import graph.GraphicEdge;
import graph.GraphicGraph;
import graph.GraphicNode;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class GraphEditor extends Editor {

	private GraphicGraph graph = new GraphicGraph();
	private GraphEditMode editMode = GraphEditMode.AddingEdges;
	private GraphicNode selectedNode = null;
	private VBox node;
	private Button addingEdgesButton;
	private Button addingNodesButton;
	private Button editingValuesButton;

	public GraphEditor(String name) {
		this.name = name;
		this.modified = false;

		init();
	}

	public void init() {
		graph.getDisplay().addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> handleGraphInteraction(event));

		HBox buttonsBox = new HBox();
		buttonsBox.setMaxHeight(50);
		buttonsBox.setMinHeight(50);

		addingEdgesButton = new Button("Add edges");
		addingNodesButton = new Button("Add nodes");
		editingValuesButton = new Button("Edit values");

		addingEdgesButton.setOnAction((event) -> setEditMode(GraphEditMode.AddingEdges));
		addingNodesButton.setOnAction((event) -> setEditMode(GraphEditMode.AddingNodes));
		editingValuesButton.setOnAction((event) -> setEditMode(GraphEditMode.EditingValues));

		buttonsBox.getChildren().addAll(addingEdgesButton, addingNodesButton, editingValuesButton);

		this.node = new VBox();
		this.graph.getDisplay().setPrefSize(2000, 2000);

		node.getChildren().addAll(graph.getDisplay(), buttonsBox);
	}

	private void makeEditable(Group group, GraphElementValueField valueField) {
		group.addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> {
			if (event.isStillSincePress() && editMode == GraphEditMode.EditingValues) {
				valueField.showInput();
				modified = true;
			}
		});
	}

	@Override
	public Node getDisplay() {
		return node;
	}

	@Override
	public void loadData(String path) {
		GraphIO graphIO = new GraphIO();

		graphIO.importGraph(path, graph);

		modified = false;
	}

	@Override
	public void saveData() {

		if (name.equals("")) {
			TextInputDialog dialog = new TextInputDialog("Graph");

			dialog.setHeaderText("Enter the graph name:");
			dialog.setContentText("Name:");

			Optional<String> dialogResult = dialog.showAndWait();
			name = dialogResult.get();
		}

		GraphIO graphIO = new GraphIO();

		graphIO.exportGraph(this.graph, "../Data/Graphs/" + name + ".graphml");

		modified = false;
	}

	public void setEditMode(GraphEditMode editMode) {
		this.editMode = editMode;
	}

	private void handleGraphInteraction(MouseEvent event) {
		if (event.isStillSincePress() && editMode == GraphEditMode.AddingNodes) {

			GraphicNode node = new GraphicNode(event.getX(), event.getY());
			makeEditable(node, node.valueField);
			graph.addNode(node);

			modified = true;
		} else if (event.isStillSincePress() && editMode == GraphEditMode.AddingEdges) {

			if (selectedNode == null) {
				selectedNode = graph.getNodeByCoordinates(event.getX(), event.getY());
				if (selectedNode != null)
					selectedNode.highlightOn();
			} else {

				GraphicNode secondNode = graph.getNodeByCoordinates(event.getX(), event.getY());
				GraphicNode firstNode = selectedNode;
				if (secondNode == firstNode) {
					selectedNode.highlightOff();
					selectedNode = null;
				} else if (secondNode != null) {
					GraphicEdge edge = new GraphicEdge(selectedNode, secondNode);
					makeEditable(edge, edge.valueField);
					graph.addEdge(edge);

					modified = true;

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
	}

	public GraphicGraph getGraph() {
		return graph;
	}

	public void disableButtons() {
		addingEdgesButton.setDisable(true);
		addingNodesButton.setDisable(true);
		editingValuesButton.setDisable(true);
	}

	public void enableButtons() {
		addingEdgesButton.setDisable(false);
		addingNodesButton.setDisable(false);
		editingValuesButton.setDisable(false);
	}

}
