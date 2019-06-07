package commands;

import graph.Edge;
import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import javafx.animation.FillTransition;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GetEdgeCommand extends Command {
	int id;

	public GetEdgeCommand(int commandOrder, Edge edge) {
		id = edge.getUniqueId();

		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		Platform.runLater(new Thread(() -> {
			GraphicEdge edge = graph.getEdgeById(id);
			edge.changeToDoubleEdge();

			FillTransition fillSource = new FillTransition(Duration.millis(duration), edge.getSource().getShape(),
					Color.YELLOW, edge.getSource().getColor());

			FillTransition fillDestination = new FillTransition(Duration.millis(duration),
					edge.getDestination().getShape(), Color.YELLOW, edge.getDestination().getColor());

			fillSource.play();
			fillDestination.play();
		}));
	}
}
