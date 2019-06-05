package graph.generation.constraints.implementations;

import java.util.ArrayList;
import java.util.List;

import graph.generation.constraints.BooleanConstraint;
import graph.generation.constraints.ConstraintFeedback;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class ConexGraphConstraint extends BooleanConstraint {
	@Override
	public ConstraintFeedback check(GraphicGraph graph) {
		List<GraphicNode> component = new ArrayList<GraphicNode>();

		if (graph.getNodes().size() == 0)
			return ConstraintFeedback.Incomplete;

		computeComponent(component, graph.getNodes().get(0));

		if (component.size() == graph.getNodes().size())
			return ConstraintFeedback.Complete;

		return ConstraintFeedback.Incomplete;
	}

	private void computeComponent(List<GraphicNode> visitedNodes, GraphicNode node) {
		if (visitedNodes.contains(node))
			return;

		visitedNodes.add(node);

		for (GraphicNode neighbour : node.getAllNeighbours()) {
			computeComponent(visitedNodes, neighbour);
		}
	}
}
