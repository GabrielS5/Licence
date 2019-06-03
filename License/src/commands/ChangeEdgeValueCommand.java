package commands;

import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;

public class ChangeEdgeValueCommand extends Command {
	private int id;
	private double value;
	
	public ChangeEdgeValueCommand(int commandOrder, int id, double value) {
		super.commandOrder = commandOrder;
		this.id = id;
		this.value = value;
	}
	
	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicEdge edge = graph.getEdgeById(id);
		double valueIncrement = (value - edge.getValue()) / 5;

		Thread taskThread = new Thread(() -> {
			try {
				for (int i = 0; i < 5; i++) {
					Thread.sleep(duration / 5);
					edge.setValue(edge.getValue() + valueIncrement);
				}
				
				edge.setValue(value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		});

		taskThread.start();
	}
}
