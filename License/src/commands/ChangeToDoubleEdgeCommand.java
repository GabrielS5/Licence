package commands;

import graph.Edge;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import javafx.application.Platform;

public class ChangeToDoubleEdgeCommand extends Command {

	private int id;

	public ChangeToDoubleEdgeCommand(int commandOrder, Edge edge) {
		id = edge.getUniqueId();

		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		Platform.runLater(new Thread(() -> {
			GraphicEdge edge = graph.getEdgeById(id);
			edge.changeToDoubleEdge();
		}));
	}
}