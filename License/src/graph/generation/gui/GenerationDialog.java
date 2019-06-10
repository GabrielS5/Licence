package graph.generation.gui;

import java.util.ArrayList;
import java.util.List;

import editors.GraphEditor;
import graph.generation.constraints.Constraint;
import graph.generation.constraints.implementations.DirectedConexGraphConstraint;
import graph.generation.constraints.implementations.EdgesNumberConstraint;
import graph.generation.constraints.implementations.EdgesPerNodeConstraint;
import graph.generation.constraints.implementations.EdgesValueConstraint;
import graph.generation.constraints.implementations.LargestCycleConstraint;
import graph.generation.constraints.implementations.NodesNumberConstraint;
import graph.generation.constraints.implementations.NodesValueConstraint;
import graph.generation.constraints.implementations.UndirectedConexGraphConstraint;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GenerationDialog extends Stage {

	private GraphEditor graphEditor;
	private List<GenerationOption> options;

	public GenerationDialog(GraphEditor graphEditor) {

		this.graphEditor = graphEditor;

		HBox titleBox = new HBox();

		titleBox.setAlignment(Pos.TOP_CENTER);
		titleBox.setMinHeight(100);
		Label title = new Label("Set your constraints");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		title.setPadding(new Insets(5, 5, 5, 20));

		titleBox.getChildren().add(title);
		title.setPadding(new Insets(20, 0, 30, 0));

		options = new ArrayList<GenerationOption>();

		options.add(new DoubleValueGenerationOption("Number of Nodes", new NodesNumberConstraint()));
		options.add(new DoubleValueGenerationOption("Number of Edges", new EdgesNumberConstraint()));
		options.add(new DoubleValueGenerationOption("Nodes value range", new NodesValueConstraint()));
		options.add(new DoubleValueGenerationOption("Edges value range", new EdgesValueConstraint()));
		options.add(new BooleanGenerationOption("Is undirected connected graph", new UndirectedConexGraphConstraint()));
		options.add(new BooleanGenerationOption("Is directed connected graph", new DirectedConexGraphConstraint()));
		options.add(new SingleValueGenerationOption("Maximum cycle length", new LargestCycleConstraint()));
		options.add(new DoubleValueGenerationOption("Number of Edges per Node", new EdgesPerNodeConstraint()));

		Button generateButton = new Button("Generate");
		HBox generateBox = new HBox(generateButton);
		generateBox.setAlignment(Pos.BOTTOM_CENTER);
		generateBox.setMinHeight(50);

		generateButton.setOnAction((event) -> startGeneration());

		VBox vBox = new VBox();
		vBox.getChildren().add(title);
		vBox.getChildren().addAll(options);
		vBox.getChildren().add(generateBox);
		vBox.setSpacing(3);
		vBox.setPadding(new Insets(0, 0, 50, 20));

		this.setTitle("Graph Generator");
		this.initStyle(StageStyle.UTILITY);
		Scene scene = new Scene(vBox, 600, 400);
		scene.getStylesheets().add(this.getClass().getResource("/resources/java-keywords.css").toExternalForm());
		this.setScene(scene);
		this.show();
	}

	private void startGeneration() {
		List<Constraint> constraints = new ArrayList<Constraint>();

		for (GenerationOption option : options) {
			if (option.isSelected()) {
				constraints.add(option.getConstraint());
			}
		}

		this.close();
		graphEditor.generateGraph(constraints);
	}
}
