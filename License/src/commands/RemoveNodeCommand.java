package commands;

import graph.Node;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
import javafx.application.Platform;

public class RemoveNodeCommand extends Command {

	private int id;

	public RemoveNodeCommand(int commandOrder, Node node) {
		id = node.getUniqueId();
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicNode node = graph.getNodeById(id);

		Platform.runLater(new Thread(() -> {
			graph.removeNode(node);
		}));
	}
}
