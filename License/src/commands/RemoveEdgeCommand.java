package commands;

import graph.Edge;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import javafx.application.Platform;

public class RemoveEdgeCommand extends Command {

	private int id;

	public RemoveEdgeCommand(int commandOrder, Edge edge) {
		id = edge.getUniqueId();
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicEdge edge = graph.getEdgeById(id);

		if (edge != null)
			Platform.runLater(new Thread(() -> {
				graph.removeEdge(edge);
			}));
	}
}
