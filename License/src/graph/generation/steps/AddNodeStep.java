package graph.generation.steps;

import java.util.Random;

import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class AddNodeStep implements IStep {
	private Random random;
	private int id;
	
	public AddNodeStep() {
		this.random = new Random();
	}

	@Override
	public boolean execute(GraphicGraph graph) {
		GraphicNode node = new GraphicNode(random.nextInt(500), random.nextInt(500));
		
		id = node.getUniqueId();
		graph.addNode(node);
		
		return true;
	}

	@Override
	public void revert(GraphicGraph graph) {
		GraphicNode node = graph.getNodeById(id);
		
		graph.getNodes().remove(node);
		
	}

}
