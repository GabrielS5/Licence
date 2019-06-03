package graph.generation.constraints;

import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class MaximumEdgesPerNodeConstraint extends Constraint {

	private int number;

	public MaximumEdgesPerNodeConstraint(int number) {
		this.number = number;
	}

	@Override
	public boolean check(GraphicGraph graph) {

		boolean response = true;

		for (GraphicNode node : graph.getNodes()) {
			if (node.getExteriorEdges().size() + node.getInteriorEdges().size() > number)
				return false;
		}

		if (response)
			super.alreadyCompleted = true;

		return response;
	}
}
