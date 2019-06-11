package tools;

import java.util.List;

import commands.Command;

public class RunResult {
	private List<Command> commands;
	private double runTime;
	private boolean successful;

	public RunResult() {
	}

	public RunResult(List<Command> commands, double runTime, boolean successful) {
		this.commands = commands;
		this.runTime = runTime;
		this.successful = successful;
	}

	public List<Command> getCommands() {
		return commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public double getRunTime() {
		return runTime;
	}

	public void setRunTime(double runTime) {
		this.runTime = runTime;
	}

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}
