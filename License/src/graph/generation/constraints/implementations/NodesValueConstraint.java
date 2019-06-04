package graph.generation.constraints.implementations;

import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.DoubleValueConstraint;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class NodesValueConstraint extends DoubleValueConstraint {

	@Override
	public ConstraintFeedback check(GraphicGraph graph) {

		for (GraphicNode node : graph.getNodes()) {
			if (node.getValue() == 0)
				return ConstraintFeedback.Incomplete;

			if (node.getValue() < firstValue || node.getValue() > secondValue)
				return ConstraintFeedback.Revert;
		}

		return ConstraintFeedback.Complete;
	}
}