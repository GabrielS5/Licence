package graph.generation.constraints;

import graph.graphic.GraphicGraph;

public abstract class Constraint {
	public abstract ConstraintFeedback check(GraphicGraph graph);
}
