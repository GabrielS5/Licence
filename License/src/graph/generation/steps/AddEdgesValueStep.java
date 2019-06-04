package graph.generation.steps;

import java.util.Random;

import graph.generation.constraints.implementations.EdgesValueConstraint;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;

public class AddEdgesValueStep implements IStep {
	private EdgesValueConstraint constraint;

	public AddEdgesValueStep(EdgesValueConstraint constraint) {
		this.constraint = constraint;
	}

	@Override
	public boolean execute(GraphicGraph graph) {
		Random random = new Random();
		int limitLength = constraint.getSecondValue() - constraint.getFirstValue();

		if (limitLength < 1)
			return false;

		for (GraphicEdge edge : graph.getEdges()) {
			edge.setValue(random.nextInt(limitLength) + constraint.getFirstValue());
		}

		return true;
	}

	@Override
	public void revert(GraphicGraph graph) {
	}
}