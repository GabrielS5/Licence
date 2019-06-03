package commands;

import graph.graphic.GraphicGraph;
import panes.MainPane;

public class PrintCommand extends Command {
	String string;

	public PrintCommand(int commandOrder, String string) {
		this.string = string;
		super.commandOrder = commandOrder;
	}

	@Override
	public void run(GraphicGraph graph, int duration) {
		MainPane.consoleOutputArea.appendText(string);
	}
}
