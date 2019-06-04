package graph.generation.constraints.implementations;

import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.DoubleValueConstraint;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;

public class EdgesValueConstraint extends DoubleValueConstraint {

	@Override
	public ConstraintFeedback check(GraphicGraph graph) {

		for (GraphicEdge edge : graph.getEdges()) {
			if (edge.getValue() == 0)
				return ConstraintFeedback.Incomplete;

			if (edge.getValue() < firstValue || edge.getValue() > secondValue)
				return ConstraintFeedback.Revert;
		}

		return ConstraintFeedback.Complete;
	}
}