package graph.generation.constraints.implementations;

import java.util.ArrayList;
import java.util.List;

import graph.generation.constraints.ConstraintFeedback;
import graph.generation.constraints.SingleValueConstraint;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class LargestCycleConstraint extends SingleValueConstraint {

	@Override
	public ConstraintFeedback check(GraphicGraph graph) {
		if (graph.getNodes().size() == 0)
			return ConstraintFeedback.Incomplete;

		if (getLargestCycleLength(graph) <= value)
			return ConstraintFeedback.Complete;

		return ConstraintFeedback.Revert;
	}

	private int getLargestCycleLength(GraphicGraph graph) {
		int maximum = 0;

		for (GraphicNode node : graph.getNodes()) {
			for (GraphicNode neighbour : node.getAllNeighbours()) {
				int result = getCycle(node, neighbour, new ArrayList<GraphicNode>());
				if (result > maximum)
					maximum = result;
			}
		}

		if (maximum == 2)
			maximum = 1;

		return maximum;
	}

	private int getCycle(GraphicNode start, GraphicNode currentNode, List<GraphicNode> path) {
		List<GraphicNode> currentPath = new ArrayList<GraphicNode>();
		currentPath.addAll(path);

		if (currentPath.contains(currentNode))
			return 0;

		if (currentNode == start)
			return currentPath.size() + 1;
		
		currentPath.add(currentNode);

		int maximum = 0;

		for (GraphicNode node : currentNode.getAllNeighbours()) {
			int result = getCycle(start, node, currentPath);
			if (result > maximum)
				maximum = result;
		}

		return maximum;
	}
}