package commands;

import graph.Node;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
import javafx.application.Platform;

public class AddNodeCommand extends Command {

	private int id;
	private double x;
	private double y;

	public AddNodeCommand(int commandOrder, Node node) {
		id = node.getUniqueId();
		x = node.getX();
		y = node.getY();

		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		Platform.runLater(new Thread(() -> {
			GraphicNode node = new GraphicNode(x, y);
			node.setUniqueId(id);
			graph.addNode(node);
		}));
	}
}