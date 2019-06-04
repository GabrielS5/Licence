package graph.generation.constraints.implementations;

import graph.generation.constraints.BooleanConstraint;
import graph.generation.constraints.ConstraintFeedback;
import graph.graphic.GraphicGraph;

public class TreeGraphConstraint extends BooleanConstraint {
	@Override
	public ConstraintFeedback check(GraphicGraph graph) {
		return ConstraintFeedback.Complete;
	}
}
