package graph.generation.constraints.implementations;

import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.DoubleValueConstraint;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class EdgesPerNodeConstraint extends DoubleValueConstraint {

	@Override
	public ConstraintFeedback check(GraphicGraph graph) {

		if (graph.getNodes().size() == 0)
			return ConstraintFeedback.Incomplete;

		ConstraintFeedback response = ConstraintFeedback.Complete;

		for (GraphicNode node : graph.getNodes()) {
			int numberOfEdges = node.getExteriorEdges().size() + node.getInteriorEdges().size();

			if (numberOfEdges < firstValue) {
				response = ConstraintFeedback.Incomplete;
			}

			if (numberOfEdges > secondValue) {
				return ConstraintFeedback.Revert;
			}
		}

		return response;
	}
}
