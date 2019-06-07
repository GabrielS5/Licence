package commands;

import graph.Edge;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
import javafx.application.Platform;

public class AddEdgeCommand extends Command {

	private int id;
	private int sourceId;
	private int destinationId;

	public AddEdgeCommand(int commandOrder, Edge edge) {
		id = edge.getUniqueId();
		sourceId = edge.getSource().getUniqueId();
		destinationId = edge.getDestination().getUniqueId();
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		Platform.runLater(new Thread(() -> {
			GraphicNode source = graph.getNodeById(sourceId);
			GraphicNode destination = graph.getNodeById(destinationId);
			GraphicEdge edge = graph.createEdge(source, destination);

			edge.setUniqueId(id);
		}));
	}
}