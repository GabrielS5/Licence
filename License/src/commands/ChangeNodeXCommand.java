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
		int divizor = duration / 10;

		double xIncrement = (x - node.getX()) / divizor;

		Thread taskThread = new Thread(() -> {
			try {
				for (int i = 0; i < divizor; i++) {
					Thread.sleep(duration / divizor);
					node.setX(xIncrement);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		taskThread.start();
	}
}
