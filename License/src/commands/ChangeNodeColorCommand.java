package commands;

import graph.GraphicGraph;
import graph.GraphicNode;
import javafx.animation.FillTransition;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ChangeNodeColorCommand extends Command {
	int id;
	Color color;

	public ChangeNodeColorCommand(int commandOrder, int id, Color color) {
		this.id = id;
		this.color = color;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicNode node = graph.getNodeById(id);

		// node.setColor(color);

		FillTransition fill = new FillTransition(Duration.millis(duration), node.getShape(), node.getColor(), color);

		fill.play();

	}
}
