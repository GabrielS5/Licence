package graph.generation.gui;

import graph.generation.constraints.Constraint;
import javafx.scene.layout.HBox;

public abstract class GenerationOption extends HBox {
	abstract Constraint getConstraint();

	abstract boolean isSelected();
}
