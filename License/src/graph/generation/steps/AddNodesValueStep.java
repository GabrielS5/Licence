package graph.generation.steps;

import java.util.Random;

import graph.generation.constraints.implementations.NodesValueConstraint;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class AddNodesValueStep implements IStep {
	private NodesValueConstraint constraint;

	public AddNodesValueStep(NodesValueConstraint constraint) {
		this.constraint = constraint;
	}

	@Override
	public boolean execute(GraphicGraph graph) {
		Random random = new Random();
		int limitLength = constraint.getSecondValue() - constraint.getFirstValue();

		if (limitLength < 1)
			return false;

		for (GraphicNode node : graph.getNodes()) {
			node.setValue(random.nextInt(limitLength) + constraint.getFirstValue());
		}

		return true;
	}

	@Override
	public void revert(GraphicGraph graph) {
	}
}
