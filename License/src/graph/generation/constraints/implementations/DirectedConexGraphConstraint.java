package graph.generation.constraints.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import graph.generation.constraints.BooleanConstraint;
import graph.generation.constraints.ConstraintFeedback;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class DirectedConexGraphConstraint extends BooleanConstraint {
	@Override
	public ConstraintFeedback check(GraphicGraph graph) {
		if (graph.getNodes().size() == 0)
			return ConstraintFeedback.Incomplete;

		return computeComponent(graph) ? ConstraintFeedback.Complete : ConstraintFeedback.Incomplete;
	}

	private boolean computeComponent(GraphicGraph graph) {
		List<GraphicNode> nodes = graph.getNodes();
		Stack<GraphicNode> nodeStack = new Stack<GraphicNode>();
		List<GraphicNode> visitedNodes = new ArrayList<GraphicNode>();

		for (GraphicNode node : nodes) {
			if (!nodeStack.contains(node)) {
				normalDfsStep(node, nodeStack, visitedNodes);
				if (visitedNodes.size() != nodes.size())
					return false;
			}
		}

		visitedNodes.clear();
		transposedDfsStep(nodeStack.pop(), visitedNodes);
		return visitedNodes.size() == nodes.size();

	}

	private void normalDfsStep(GraphicNode node, Stack<GraphicNode> nodeStack, List<GraphicNode> visitedNodes) {
		if (visitedNodes.contains(node))
			return;
		visitedNodes.add(node);

		for (GraphicNode neighbour : node.getOutgoingNeighbours()) {
			normalDfsStep(neighbour, nodeStack, visitedNodes);
		}
		nodeStack.push(node);
	}

	private void transposedDfsStep(GraphicNode node, List<GraphicNode> visitedNodes) {
		if (visitedNodes.contains(node))
			return;
		visitedNodes.add(node);

		for (GraphicNode neighbour : node.getIncomingNeighbours()) {
			transposedDfsStep(neighbour, visitedNodes);
		}
	}
}
