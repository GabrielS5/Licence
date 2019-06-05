package editors;

import java.util.List;
import java.util.Optional;

import graph.GraphIO;
import graph.generation.GraphGenerator;
import graph.generation.constraints.Constraint;
import graph.generation.gui.GenerationDialog;
import graph.graphic.GraphElementValueField;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.SwitchButton;

public class GraphEditor extends Editor {

	private GraphicGraph graph = new GraphicGraph();
	private GraphEditMode editMode = GraphEditMode.AddingEdges;
	private GraphicNode selectedNode = null;
	private VBox node;
	private Button addingEdgesButton;
	private Button addingNodesButton;
	private Button editingValuesButton;
	private TextField nameField;
	private Button saveButton;
	private SwitchButton directedSwitch;
	private Button generateButton;

	public GraphEditor(String name) {
		this.name = name;
		this.modified = false;

		init();
	}

	public void init() {
		graph.getDisplay().addEventHandler(MouseEvent.MOUSE_RELEASED, (event) -> handleGraphInteraction(event));

		HBox buttonsBox = new HBox();
		buttonsBox.setMaxHeight(40);
		buttonsBox.setMinHeight(40);
		buttonsBox.setSpacing(5);

		Image saveImage = new Image(getClass().getResourceAsStream("/resources/save.png"));
		Image addEdgeImage = new Image(getClass().getResourceAsStream("/resources/add-edge.png"));
		Image addNodeImage = new Image(getClass().getResourceAsStream("/resources/add-node.png"));
		Image editImage = new Image(getClass().getResourceAsStream("/resources/edit.png"));
		Image generateImage = new Image(getClass().getResourceAsStream("/resources/generate.png"));

		addingEdgesButton = new Button("");
		addingEdgesButton.setGraphic(new ImageView(addEdgeImage));
		addingEdgesButton.setTooltip(new Tooltip("Add edges"));

		addingNodesButton = new Button("");
		addingNodesButton.setGraphic(new ImageView(addNodeImage));
		addingNodesButton.setTooltip(new Tooltip("Add nodes"));

		editingValuesButton = new Button("");
		editingValuesButton.setGraphic(new ImageView(editImage));
		editingValuesButton.setTooltip(new Tooltip("Edit values"));

		nameField = new TextField(this.name);
		nameField.setMinWidth(160);
		nameField.setMinHeight(28);

		saveButton = new Button("");
		saveButton.setGraphic(new ImageView(saveImage));
		saveButton.setTooltip(new Tooltip("Save"));

		generateButton = new Button();
		generateButton.setGraphic(new ImageView(generateImage));
		generateButton.setTooltip(new Tooltip("Generate a graph"));

		directedSwitch = new SwitchButton("Directed", "Undirected");
		directedSwitch.getButton().setOnAction((event) -> handleDirectedSwitch());

		addingEdgesButton.setOnAction((event) -> setEditMode(GraphEditMode.AddingEdges));
		addingNodesButton.setOnAction((event) -> setEditMode(GraphEditMode.AddingNodes));
		editingValuesButton.setOnAction((event) -> setEditMode(GraphEditMode.EditingValues));
		saveButton.setOnAction((event) -> saveData());
		generateButton.setOnAction((event) -> new GenerationDialog(this));

		buttonsBox.getChildren().addAll(nameField, addingEdgesButton, addingNodesButton, editingValuesButton,
				directedSwitch, generateButton, saveButton);

		this.node = new VBox();
		this.graph.getDisplay().setPrefSize(2000, 2000);

		buttonsBox.setAlignment(Pos.CENTER_LEFT);
		buttonsBox.setPadding(new Insets(0, 20, 0, 20));
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
		directedSwitch.resetSwitch();
		clearGraph();

		GraphIO graphIO = new GraphIO();

		graphIO.importGraph(path, graph);

		for (GraphicNode node : graph.getNodes()) {
			makeEditable(node, node.valueField);
		}

		for (GraphicEdge edge : graph.getEdges()) {
			makeEditable(edge, edge.valueField);
		}

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
			replaceName(name);
		} else if (!nameField.getText().equals(name)) {
			replaceName(nameField.getText());
		}

		GraphIO graphIO = new GraphIO();

		graphIO.exportGraph(this.graph, "../Data/Graphs/" + name + ".graphml");

		modified = false;
	}

	public void generateGraph(List<Constraint> constraints) {
		directedSwitch.resetSwitch();
		clearGraph();

		GraphGenerator graphGenerator = new GraphGenerator();

		graphGenerator.generate(graph, constraints);

		for (GraphicNode node : graph.getNodes()) {
			makeEditable(node, node.valueField);
		}

		for (GraphicEdge edge : graph.getEdges()) {
			makeEditable(edge, edge.valueField);
		}

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
			node.valueField.showInput();

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

					GraphicEdge edge = graph.createEdge(firstNode, secondNode);

					if (edge != null) {
						if (!edge.isDoubleEdged())
							makeEditable(edge, edge.valueField);
						edge.valueField.showInput();
						modified = true;
						secondNode.highlightOn();
					}

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
		saveButton.setDisable(true);
		directedSwitch.setDisable(true);
		generateButton.setDisable(true);
	}

	public void enableButtons() {
		addingEdgesButton.setDisable(false);
		addingNodesButton.setDisable(false);
		editingValuesButton.setDisable(false);
		saveButton.setDisable(false);
		directedSwitch.setDisable(false);
		generateButton.setDisable(false);
	}

	private void replaceName(String name) {
		this.name = name;
		this.nameField.setText(name);
	}

	private void handleDirectedSwitch() {
		this.graph.setDirected(!this.graph.isDirected());
		this.directedSwitch.changeSwitch();
	}

	private void clearGraph() {
		this.graph.getNodes().clear();
		this.graph.getEdges().clear();
		this.graph.resetCanvas();
	}
}
