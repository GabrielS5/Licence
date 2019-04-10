package editors;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import graph.Edge;
import graph.Graph;
import graph.GraphElementValueField;
import graph.GraphNode;
import graph.serialization.GraphSerialization;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
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
		this.modified = false;

		init();
	}

	public void init() {
		graph.getDisplay().addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> handleGraphInteraction(event));

		HBox buttonsBox = new HBox();
		buttonsBox.setMaxHeight(100);
		buttonsBox.setMinHeight(100);

		Button addingEdgesButton = new Button("Add edges");
		Button addingNodesButton = new Button("Add nodes");
		Button editingValuesButton = new Button("Edit values");

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
			}
		});
	}

	@Override
	public Node getDisplay() {
		return node;
	}

	@Override
	public void loadData(String path) {
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			graph.load((GraphSerialization) in.readObject());
			for (GraphNode node : graph.getNodes()) {
				this.makeEditable(node, node.valueField);
			}

			for (Edge edge : graph.getEdges()) {
				this.makeEditable(edge, edge.valueField);
			}

			in.close();
			fileIn.close();
		} catch (IOException | ClassNotFoundException i) {
			i.printStackTrace();
			return;
		}
	}

	@Override
	public void saveData() {
		File file = null;

		if (name.equals("")) {
			TextInputDialog dialog = new TextInputDialog("Graph");

			dialog.setHeaderText("Enter the graph name:");
			dialog.setContentText("Name:");

			Optional<String> dialogResult = dialog.showAndWait();
			name = dialogResult.get();
		}
		file = new File("../Data/Graphs/" + name + ".graph");

		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(new GraphSerialization(graph));
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		}

		modified = false;
	}

	public void setEditMode(GraphEditMode editMode) {
		this.editMode = editMode;
	}

	private void handleGraphInteraction(MouseEvent event ) {
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
	}

}
