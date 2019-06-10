package graph.generation.gui;

import graph.generation.constraints.Constraint;
import javafx.scene.layout.VBox;

public abstract class GenerationOption extends VBox {
	abstract Constraint getConstraint();

	abstract boolean isSelected();
}
