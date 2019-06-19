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
		int divizor = duration / 20;
		
		double yIncrement = (y - node.getY()) / divizor;

		Thread taskThread = new Thread(() -> {
			try {
				for (int i = 0; i < divizor; i++) {
					Thread.sleep(duration / divizor);
					node.setY(yIncrement);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		taskThread.start();
	}
}
