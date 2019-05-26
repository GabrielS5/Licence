package commands;

import graph.GraphicGraph;
import graph.GraphicNode;

public class ChangeNodeYCommand extends Command {
	int id;
	double y;

	public ChangeNodeYCommand(int commandOrder, int id, double y) {
		this.id = id;
		this.y = y;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicNode node = graph.getNodeById(id);
		
		node.setY(y);

	}
}
