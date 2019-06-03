package commands;

import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
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

		FillTransition fill = new FillTransition(Duration.millis(duration), node.getShape(), node.getColor(), color);
		
		node.setColor(color);
		
		fill.play();

	}
}
