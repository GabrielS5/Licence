package graph.generation.steps;

import java.util.Random;

import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class AddEdgeStep implements IStep {
	private Random random;
	private int id;

	public AddEdgeStep() {
		this.random = new Random();
	}

	@Override
	public boolean execute(GraphicGraph graph) {
		int tries = 0;
		
		if(graph.getNodes().size() < 2)
			return false;

		while (tries < 10) {
			GraphicNode source = graph.getNodes().get(random.nextInt(graph.getNodes().size()));
			GraphicNode destination = graph.getNodes().get(random.nextInt(graph.getNodes().size()));

			GraphicEdge edge = graph.createEdge(source, destination);
			if (edge != null) {
				id = edge.getUniqueId();
				break;
			}

			tries++;
		}

		return tries != 10;
	}

	@Override
	public void revert(GraphicGraph graph) {
		GraphicEdge edge = graph.getEdgeById(id);

		graph.getEdges().remove(edge);
	}
}
