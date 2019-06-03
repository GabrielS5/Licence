package commands;

import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class ChangeNodeXCommand extends Command {
	int id;
	double x;

	public ChangeNodeXCommand(int commandOrder, int id, double x) {
		this.id = id;
		this.x = x;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicNode node = graph.getNodeById(id);
		double xIncrement = (x - node.getX()) / 100;

		Thread taskThread = new Thread(() -> {
			try {
				for (int i = 0; i < 100; i++) {
					Thread.sleep(duration / 100);
					node.setX(xIncrement);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		taskThread.start();
	}
}
