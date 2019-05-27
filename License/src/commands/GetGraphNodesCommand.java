package commands;

import java.util.List;
import java.util.stream.Collectors;

import graph.GraphicGraph;
import graph.GraphicNode;
import graph.Node;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GetGraphNodesCommand extends Command {

	private List<Integer> nodesIds;

	public GetGraphNodesCommand(int commandOrder, List<Node> nodes) {
		nodesIds = nodes.stream().map((neighbour) -> neighbour.getUniqueId()).collect(Collectors.toList());
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {

		for (int id : nodesIds) {
			GraphicNode node = graph.getNodeById(id);

			FillTransition fill = new FillTransition(Duration.millis(duration / 2), node.getShape(), Color.YELLOW,
					node.getColor());

			fill.play();
		}
	}

}
