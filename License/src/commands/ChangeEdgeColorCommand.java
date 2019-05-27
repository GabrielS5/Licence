package commands;

import graph.GraphicEdge;
import graph.GraphicGraph;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ChangeEdgeColorCommand extends Command {
	int id;
	Color color;

	public ChangeEdgeColorCommand(int commandOrder, int id, Color color) {
		this.id = id;
		this.color = color;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicEdge edge = graph.getEdgeById(id);

		FillTransition fill = new FillTransition(Duration.millis(duration), edge.getShape(), edge.getColor(), color);

		edge.setColor(color);

		fill.play();

	}
}
