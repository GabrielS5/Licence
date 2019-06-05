package graph.generation.gui;

import graph.generation.constraints.Constraint;
import graph.generation.constraints.SingleValueConstraint;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class SingleValueGenerationOption extends GenerationOption {
	private CheckBox checkBox;
	private TextField userInput;
	private SingleValueConstraint constraint;
	private HBox hBox;

	public SingleValueGenerationOption(String name, SingleValueConstraint constraint) {
		super();

		this.constraint = constraint;

		this.setSpacing(30);
		this.setAlignment(Pos.CENTER_LEFT);

		checkBox = new CheckBox(name);
		checkBox.setMinWidth(300);

		hBox = new HBox();
		Label label = new Label("Value:");
		userInput = new TextField();
		userInput.setMaxWidth(50);
		hBox.setVisible(false);
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(3);
		hBox.getChildren().addAll(label, userInput);

		checkBox.setOnAction((event) -> changeInputsVisibility());

		this.getChildren().addAll(checkBox, hBox);
	}

	private void changeInputsVisibility() {
		hBox.setVisible(!hBox.isVisible());
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public Constraint getConstraint() {

		try {
			int value = Integer.parseInt(userInput.getText());
			constraint.setValue(value);
		} catch (Exception e) {
		}

		return constraint;
	}
}
