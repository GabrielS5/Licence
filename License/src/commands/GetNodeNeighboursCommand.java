package commands;

import java.util.List;
import java.util.stream.Collectors;

import graph.Node;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GetNodeNeighboursCommand extends Command {

	private List<Integer> neighboursIds;
	private int nodeId;

	public GetNodeNeighboursCommand(int commandOrder, List<Node> neighbours, int nodeId) {
		neighboursIds = neighbours.stream().map((neighbour) -> neighbour.getUniqueId()).collect(Collectors.toList());
		this.nodeId = nodeId;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {

		GraphicNode node = graph.getNodeById(nodeId);
		FillTransition fillTransition = new FillTransition(Duration.millis(duration / 2), node.getShape(), Color.RED,
				node.getColor());

		fillTransition.play();
		
		for (int id : neighboursIds) {
			GraphicNode neighbour = graph.getNodeById(id);

			fillTransition = new FillTransition(Duration.millis(duration / 2), neighbour.getShape(), Color.YELLOW,
					neighbour.getColor());

			fillTransition.play();
		}
	}

}
