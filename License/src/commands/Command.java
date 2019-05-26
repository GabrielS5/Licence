package commands;

import graph.GraphicGraph;

public abstract class Command {
	int commandOrder = 0;

	public abstract void run(GraphicGraph graph, int duration);

	public int getCommandOrder() {
		return commandOrder;
	}
}
