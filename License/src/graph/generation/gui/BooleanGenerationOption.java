package graph.generation.gui;

import graph.generation.constraints.BooleanConstraint;
import graph.generation.constraints.Constraint;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;

public class BooleanGenerationOption extends GenerationOption {
	private CheckBox checkBox;
	private BooleanConstraint constraint;

	public BooleanGenerationOption(String name, BooleanConstraint constraint) {
		super();

		this.constraint = constraint;

		this.setSpacing(30);
		this.setAlignment(Pos.CENTER_LEFT);

		checkBox = new CheckBox(name);
		checkBox.setMinWidth(300);

		this.getChildren().addAll(checkBox);
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public Constraint getConstraint() {
		return constraint;
	}
}
