package graph.generation.constraints;

import graph.graphic.GraphicGraph;

public class NodesNumberConstraint extends Constraint {
	private int number;

	public NodesNumberConstraint(int number) {
		this.number = number;
	}

	@Override
	public boolean check(GraphicGraph graph) {
		
		boolean response = graph.getNodes().size() == number;
		
		if(response)
			super.alreadyCompleted = true;
		
		return response;
	}
}
