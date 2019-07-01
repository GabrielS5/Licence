package panes;

import editors.EditorType;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.CommandsRunner;
import tools.ProgramRunner;
import tools.RunResult;

public class MainPane extends VBox {
	private CodePane codePane;
	private GraphPane graphPane;
	private HBox buttonsBox;
	private SplitPane display;
	private RunningControlPane runningControlPane;
	public static TextArea consoleOutputArea;

	public MainPane() {
		super();

		codePane = new CodePane();
		codePane.createEditor("");

		graphPane = new GraphPane();
		graphPane.createEditor("");

		consoleOutputArea = new TextArea();
		consoleOutputArea.setMinHeight(100);

		VBox graphDisplay = new VBox();
		graphDisplay.getChildren().addAll(graphPane);

		display = new SplitPane();
		display.getItems().addAll(codePane, graphPane);

		HBox controls = new HBox();
		controls.setMaxHeight(50);
		controls.setMinHeight(50);
		runningControlPane = new RunningControlPane();

		this.codePane.getEditor().getRunButton().setOnAction((event) -> runProgram());

		controls.getChildren().addAll(runningControlPane);

		this.graphPane.setOnMouseClicked((event) -> this.hideShowEditor(event, graphPane));
		this.codePane.setOnMouseClicked((event) -> this.hideShowEditor(event, codePane));

		this.getChildren().addAll(controls, display);
	}

	public void save() {
		codePane.saveEditor();
		graphPane.saveEditor();
	}

	public void load(EditorType editorType) {
		if (editorType == EditorType.Code) {
			codePane.loadEditor();
			this.codePane.getEditor().getRunButton().setOnAction((event) -> runProgram());
		} else {
			graphPane.loadEditor();
		}
	}

	public void create(EditorType editorType) {
		if (editorType == EditorType.Code) {
			codePane.createEditor("");
		} else {
			graphPane.createEditor("");
		}
	}

	public void exit() {
		codePane.ensureEditorSaved();
		graphPane.ensureEditorSaved();
	}

	private void hideShowEditor(MouseEvent event, EditorPane editorPane) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 3) {
			if (display.getItems().size() == 1) {
				display.getItems().clear();
				display.getItems().addAll(codePane, graphPane);
			} else {
				display.getItems().clear();
				display.getItems().add(editorPane);
			}
		}
	}

	private void runProgram() {
		ProgramRunner programRunner = new ProgramRunner();

		programRunner.runProgram(codePane.getEditor().name, graphPane.getEditor().getGraph());

		RunResult runResult = programRunner.runProgram(codePane.getEditor().name, graphPane.getEditor().getGraph());

		if (runResult.isSuccessful()) {
			CommandsRunner commandsRunner = new CommandsRunner(runResult.getCommands(), graphPane.getEditor().getGraph(),
					runningControlPane, this);

			startRunPreparation(runResult);

			commandsRunner.start();
		}
	}

	private void startRunPreparation(RunResult runResult) {
		runningControlPane.setDisable(false);
		runningControlPane.setLabelVisibility(true);
		runningControlPane.setRunningTime(runResult.getRunTime());

		graphPane.getEditor().disableButtons();
		codePane.getEditor().disableButtons();

		addOutputConsole();
	}

	public void endRunPreparation() {
		runningControlPane.setDisable(true);
		runningControlPane.setLabelVisibility(false);
		runningControlPane.getPauseButton().setOnAction(null);
		runningControlPane.getPlayButton().setOnAction(null);
		runningControlPane.getStepButton().setOnAction(null);
		runningControlPane.getExitButton().setOnAction(null);

		graphPane.getEditor().enableButtons();
		codePane.getEditor().enableButtons();

		removeOutputConsole();
	}

	private void addOutputConsole() {
		consoleOutputArea.setText("");
		display.getItems().clear();
		VBox vBox = new VBox();
		vBox.getChildren().addAll(consoleOutputArea, graphPane);
		display.getItems().addAll(codePane, vBox);

	}

	private void removeOutputConsole() {
		display.getItems().clear();
		display.getItems().addAll(codePane, graphPane);
	}

	public CodePane getCodePane() {
		return codePane;
	}

	public GraphPane getGraphPane() {
		return graphPane;
	}
}
