package commands;

import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

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
		double yIncrement = (y - node.getY()) / 15;

		Thread taskThread = new Thread(() -> {
			try {
				for (int i = 0; i < 15; i++) {
					Thread.sleep(duration / 15);
					node.setY(yIncrement);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		taskThread.start();
	}
}
