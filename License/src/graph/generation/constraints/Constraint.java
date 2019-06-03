package graph.generation.constraints;

import graph.graphic.GraphicGraph;

public abstract class Constraint {
	protected boolean alreadyCompleted = false;

	public abstract boolean check(GraphicGraph graph);

	public boolean isAlreadyCompleted() {
		return alreadyCompleted;
	}
}
