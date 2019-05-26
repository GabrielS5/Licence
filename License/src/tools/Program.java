package tools;

import java.util.ArrayList;
import java.util.List;

import commands.Command;
import commands.PrintCommand;
import graph.Graph;

public abstract class Program {
	private List<Command> commands = new ArrayList<Command>();

	public abstract void run(Graph graph);

	public void print(String string) {
		commands.add(new PrintCommand(Graph.getCommandOrder(), string));
	}

	public void println(String string) {
		commands.add(new PrintCommand(Graph.getCommandOrder(), string + '\n'));
	}

	public List<Command> getCommands() {
		return commands;
	}
}
