package graph.generation.gui;

import graph.generation.constraints.Constraint;
import graph.generation.constraints.DoubleValueConstraint;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DoubleValueGenerationOption extends GenerationOption {
	private CheckBox checkBox;
	private TextField firstUserInput;
	private TextField secondUserInput;
	private DoubleValueConstraint constraint;
	private HBox firstHBox;
	private HBox secondHBox;

	public DoubleValueGenerationOption(String name, DoubleValueConstraint constraint) {
		super();

		this.constraint = constraint;

		this.setSpacing(30);
		this.setAlignment(Pos.CENTER_LEFT);

		checkBox = new CheckBox(name);
		checkBox.setMinWidth(300);

		firstHBox = new HBox();
		Label firstLabel = new Label("Min:");
		firstUserInput = new TextField();
		firstUserInput.setMaxWidth(50);
		firstHBox.setVisible(false);
		firstHBox.setAlignment(Pos.CENTER);
		firstHBox.setSpacing(3);
		firstHBox.getChildren().addAll(firstLabel, firstUserInput);

		secondHBox = new HBox();
		Label secondLabel = new Label("Max:");
		secondUserInput = new TextField();
		secondUserInput.setMaxWidth(50);
		secondHBox.setVisible(false);
		secondHBox.setAlignment(Pos.CENTER);
		secondHBox.setSpacing(3);
		secondHBox.getChildren().addAll(secondLabel, secondUserInput);

		checkBox.setOnAction((event) -> changeInputsVisibility());

		this.getChildren().addAll(checkBox, firstHBox, secondHBox);
	}

	private void changeInputsVisibility() {
		firstHBox.setVisible(!firstHBox.isVisible());
		secondHBox.setVisible(!secondHBox.isVisible());
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public Constraint getConstraint() {

		try {
			int firstValue = Integer.parseInt(firstUserInput.getText());
			constraint.setFirstValue(firstValue);
		} catch (Exception e) {
		}

		try {
			int secondValue = Integer.parseInt(secondUserInput.getText());
			constraint.setSecondValue(secondValue);
		} catch (Exception e) {
		}

		return constraint;
	}

}
