package commands;

import java.util.List;
import java.util.stream.Collectors;

import graph.Edge;
import graph.GraphicEdge;
import graph.GraphicGraph;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GetGraphEdgesCommand extends Command {

	private List<Integer> edgesIds;

	public GetGraphEdgesCommand(int commandOrder, List<Edge> edges) {
		edgesIds = edges.stream().map((neighbour) -> neighbour.getUniqueId()).collect(Collectors.toList());
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {

		for (int id : edgesIds) {
			GraphicEdge edge = graph.getEdgeById(id);

			FillTransition fill = new FillTransition(Duration.millis(duration / 2), edge.getShape(), Color.YELLOW,
					edge.getColor());

			fill.play();
		}
	}
}
