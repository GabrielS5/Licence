package tools;

import java.util.ArrayList;
import java.util.List;

import commands.Command;
import commands.PrintCommand;
import graph.Graph;

public abstract class Program {
	private List<Command> commands = new ArrayList<Command>();

	public abstract void run(Graph graph);

	public void print(int number) {
		print(String.valueOf(number));
	}

	public void print(double number) {
		print(String.valueOf(number));
	}

	public void print(float number) {
		print(String.valueOf(number));
	}

	public void print(boolean number) {
		print(String.valueOf(number));
	}

	public void print(char number) {
		print(String.valueOf(number));
	}

	public void print(Object object) {
		print(object.toString());
	}

	public void println(int number) {
		println(String.valueOf(number));
	}

	public void println(double number) {
		println(String.valueOf(number));
	}

	public void println(float number) {
		println(String.valueOf(number));
	}

	public void println(boolean number) {
		println(String.valueOf(number));
	}

	public void println(char number) {
		println(String.valueOf(number));
	}

	public void println(Object object) {
		println(object.toString());
	}

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
