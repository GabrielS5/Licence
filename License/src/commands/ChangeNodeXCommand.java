package commands;

import graph.GraphicGraph;
import graph.GraphicNode;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;

public class ChangeNodeXCommand extends Command {
	int id;
	double x;

	public ChangeNodeXCommand(int commandOrder, int id, double x) {
		this.id = id;
		this.x = x;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		GraphicNode node = graph.getNodeById(id);

		//node.setX(x);
	     TranslateTransition tt = new TranslateTransition(Duration.millis(duration), node.getShape());
	     tt.setByX(x - node.getX());
	     tt.setCycleCount(10);
	 
	     tt.play();
	}
}
