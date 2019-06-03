package graph.generation.steps;

import graph.graphic.GraphicGraph;

public interface IStep {

	boolean execute(GraphicGraph graph);

	void revert(GraphicGraph graph);
}
