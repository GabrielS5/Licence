package graph.generation.constraints;

import graph.graphic.GraphicGraph;

public class EdgesNumberConstraint extends Constraint {
	private int number;

	public EdgesNumberConstraint(int number) {
		this.number = number;
	}

	@Override
	public boolean check(GraphicGraph graph) {
		
		boolean response = graph.getEdges().size() == number;
		
		if(response)
			super.alreadyCompleted = true;
		
		return response;
	}
}
