package tools;

import java.util.List;

import commands.Command;
import graph.graphic.GraphicGraph;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import panes.MainPane;
import panes.RunningControlPane;

public class CommandsRunner extends Thread {
	private List<Command> commands;
	private GraphicGraph graph;
	private RunningMode runningMode = RunningMode.Pause;
	private DoubleProperty speed = new SimpleDoubleProperty();
	private final Object pauseLock = new Object();
	private MainPane mainPane;

	public CommandsRunner(List<Command> commands, GraphicGraph graph, RunningControlPane runningControlPane,
			MainPane mainPane) {
		this.commands = commands;
		this.graph = graph;
		this.mainPane = mainPane;

		runningControlPane.getPauseButton().setOnAction((event) -> setRunningMode(RunningMode.Pause));
		runningControlPane.getPlayButton().setOnAction((event) -> setRunningMode(RunningMode.Automatic));
		runningControlPane.getStepButton().setOnAction((event) -> setRunningMode(RunningMode.Manual));
		runningControlPane.getExitButton().setOnAction((event) -> setRunningMode(RunningMode.Exit));

		speed.bind(runningControlPane.getSliderValue());
	}

	public void run() {
		double dap = 3.0 * Math.cos(2.0);
		try {
			for (Command command : commands) {
				if (runningMode == RunningMode.Automatic) {
					Thread.sleep((long) (1000 * speed.get()));
				} else if (runningMode == RunningMode.Pause) {
					synchronized (pauseLock) {
						pauseLock.wait();
					}
				}

				if (runningMode == RunningMode.Exit) {
					break;
				}

				if (runningMode != RunningMode.Automatic) {
					setRunningMode(RunningMode.Pause);
				}

				command.run(graph, (int) (1000 * speed.get()));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			Platform.runLater(new Thread(() -> {
				mainPane.endRunPreparation();
			}));

		}
	}

	public void setRunningMode(RunningMode runningMode) {
		this.runningMode = runningMode;
		if (runningMode != RunningMode.Pause) {
			synchronized (pauseLock) {
				pauseLock.notify();
			}
		}
	}

}
