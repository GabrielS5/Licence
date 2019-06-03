package commands;

import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;

public class ChangeNodeValueCommand extends Command{

	private int id;
	private double value;
	
	public ChangeNodeValueCommand(int commandOrder, int id, double value) {
		super.commandOrder = commandOrder;
		this.id = id;
		this.value = value;
	}
	
	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicNode node = graph.getNodeById(id);
		double valueIncrement = (value - node.getValue()) / 5;

		Thread taskThread = new Thread(() -> {
			try {
				for (int i = 0; i < 5; i++) {
					Thread.sleep(duration / 5);
					node.setValue(node.getValue() + valueIncrement);
				}
				
				node.setValue(value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		taskThread.start();
	}

}
