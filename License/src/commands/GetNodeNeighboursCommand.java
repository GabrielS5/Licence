package commands;

import java.util.List;
import java.util.stream.Collectors;

import graph.GraphicGraph;
import graph.GraphicNode;
import graph.Node;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GetNodeNeighboursCommand extends Command {

	private List<Integer> neighboursIds;

	public GetNodeNeighboursCommand(int commandOrder, List<Node> neighbours) {
		neighboursIds = neighbours.stream().map((neighbour) -> neighbour.getUniqueId()).collect(Collectors.toList());
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {

		for (int id : neighboursIds) {
			GraphicNode node = graph.getNodeById(id);

			FillTransition fill = new FillTransition(Duration.millis(duration / 2), node.getShape(), Color.YELLOW,
					node.getColor());

			fill.play();
		}
	}

}
