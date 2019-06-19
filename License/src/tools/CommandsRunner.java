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
	private RunningControlPane runningControlPane;

	public CommandsRunner(List<Command> commands, GraphicGraph graph, RunningControlPane runningControlPane,
			MainPane mainPane) {
		this.commands = commands;
		this.graph = graph;
		this.mainPane = mainPane;

		this.runningControlPane = runningControlPane;
		runningControlPane.getPauseButton().setOnAction((event) -> setRunningMode(RunningMode.Pause));
		runningControlPane.getPlayButton().setOnAction((event) -> setRunningMode(RunningMode.Automatic));
		runningControlPane.getStepButton().setOnAction((event) -> setRunningMode(RunningMode.Manual));
		runningControlPane.getExitButton().setOnAction((event) -> setRunningMode(RunningMode.Exit));

		runningControlPane.initializeCommandsNumber(commands.size());
		speed.bind(runningControlPane.getSliderValue());
	}

	public void run() {
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
				} else if (runningMode == RunningMode.Manual) {
					setRunningMode(RunningMode.Pause);
				}

				runningControlPane.incrementCommandsNumber();
				command.run(graph, (int) (1000 * speed.get()));
			}

			synchronized (pauseLock) {
				while (runningMode != RunningMode.Exit)
					pauseLock.wait();
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
