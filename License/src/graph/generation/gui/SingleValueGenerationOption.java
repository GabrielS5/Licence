package graph.generation.gui;

import graph.generation.constraints.Constraint;
import graph.generation.constraints.SingleValueConstraint;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class SingleValueGenerationOption extends GenerationOption {
	private CheckBox checkBox;
	private TextField userInput;
	private SingleValueConstraint constraint;
	private HBox hBox;

	public SingleValueGenerationOption(String name, SingleValueConstraint constraint) {
		super();

		this.constraint = constraint;

		HBox body = new HBox();
		body.setMinHeight(25);

		body.setSpacing(30);
		body.setAlignment(Pos.CENTER_LEFT);

		checkBox = new CheckBox(name);
		checkBox.setMinWidth(300);
		checkBox.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));

		hBox = new HBox();
		Label label = new Label("Value:");
		label.setMinWidth(40);

		userInput = new TextField();
		userInput.getStyleClass().add("value-field");
		userInput.setMaxWidth(50);
		hBox.setVisible(false);
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(3);
		hBox.getChildren().addAll(label, userInput);

		checkBox.setOnAction((event) -> changeInputsVisibility());

		Separator separator = new Separator();
		separator.setMinWidth(300);
		separator.setMaxWidth(300);

		body.getChildren().addAll(checkBox, hBox);

		this.getChildren().add(body);
		this.getChildren().add(1, separator);
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
