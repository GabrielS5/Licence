package graph.generation.gui;

import graph.generation.constraints.BooleanConstraint;
import graph.generation.constraints.Constraint;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class BooleanGenerationOption extends GenerationOption {
	private CheckBox checkBox;
	private BooleanConstraint constraint;

	public BooleanGenerationOption(String name, BooleanConstraint constraint) {
		super();

		this.constraint = constraint;

		HBox body = new HBox();
		body.setMinHeight(25);

		body.setSpacing(30);
		body.setAlignment(Pos.CENTER_LEFT);

		checkBox = new CheckBox(name);
		checkBox.setMinWidth(300);
		checkBox.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));

		Separator separator = new Separator();
		separator.setMinWidth(300);
		separator.setMaxWidth(300);

		body.getChildren().addAll(checkBox);
		
		this.getChildren().add(body);
		this.getChildren().add(1,separator);

	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public Constraint getConstraint() {
		return constraint;
	}
}
