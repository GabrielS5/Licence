package graph.generation.constraints.implementations;

import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.DoubleValueConstraint;
import graph.graphic.GraphicGraph;

public class EdgesNumberConstraint extends DoubleValueConstraint {

	@Override
	public ConstraintFeedback check(GraphicGraph graph) {

		if (graph.getEdges().size() < firstValue)
			return ConstraintFeedback.Incomplete;

		if (graph.getEdges().size() > secondValue)
			return ConstraintFeedback.Revert;

		return ConstraintFeedback.Complete;
	}
}
