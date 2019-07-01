package graph.generation.constraints.implementations;

import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.DoubleValueConstraint;
import graph.graphic.GraphicGraph;

public class NodesNumberConstraint extends DoubleValueConstraint {

	@Override
	public ConstraintFeedback check(GraphicGraph graph) {
		

		if (graph.getNodes().size() < firstValue)
			return ConstraintFeedback.Incomplete;

		if (graph.getNodes().size() > secondValue)
			return ConstraintFeedback.Revert;

		return ConstraintFeedback.Complete;
	}
}

