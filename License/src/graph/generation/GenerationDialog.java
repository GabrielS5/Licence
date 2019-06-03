package graph.generation;

import java.util.ArrayList;
import java.util.List;

import editors.GraphEditor;
import graph.generation.constraints.Constraint;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.MiscTools;

public class GenerationDialog extends Stage {

	private GraphEditor graphEditor;
	private List<GenerationOption> options;

	public GenerationDialog(GraphEditor graphEditor) {

		this.graphEditor = graphEditor;

		options = new ArrayList<GenerationOption>();

		options.add(new GenerationOption("Number of Nodes"));
		options.add(new GenerationOption("Number of Edges"));
		options.add(new GenerationOption("Maximum number of Edges per Node"));

		Button generateButton = new Button("Generate");

		generateButton.setOnAction((event) -> startGeneration());

		VBox vBox = new VBox();
		vBox.getChildren().addAll(options);
		vBox.getChildren().add(generateButton);
		vBox.setSpacing(2);

		this.initStyle(StageStyle.UTILITY);
		Scene scene = new Scene(vBox, 600, 400);
		this.setScene(scene);
		this.show();
	}

	private void startGeneration() {
		List<Constraint> constraints = new ArrayList<Constraint>();

		for (GenerationOption option : options) {
			if (option.isSelected())
				constraints.add(MiscTools.getConstraintFromInput(option.getType(), option.getValue()));
		}
		
		this.close();
		graphEditor.generateGraph(constraints);
	}
}
