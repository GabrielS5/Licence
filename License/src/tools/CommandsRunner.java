package tools;

import java.util.List;

import commands.Command;
import graph.GraphicGraph;
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
		speed.bind(runningControlPane.getSliderValue());
	}

	public void run() {
		try {
			for (Command command : commands) {
				System.out.println(runningMode);
				if (runningMode == RunningMode.Automatic) {
					Thread.sleep((long) (1000 * speed.get()));
				} else if (runningMode == RunningMode.Pause) {
					synchronized (pauseLock) {
						pauseLock.wait();
					}
				}

				if (runningMode != RunningMode.Automatic) {
					setRunningMode(RunningMode.Pause);
				}

				command.run(graph,(int) (1000 * speed.get()));
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			Thread taskThread = new Thread(() -> {
				Platform.runLater(new Thread(() -> {
					mainPane.endRunPreparation();
				}));

			});

			taskThread.start();
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
