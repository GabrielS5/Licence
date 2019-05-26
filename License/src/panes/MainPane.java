package panes;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import commands.Command;
import editors.CodeEditor;
import editors.EditorType;
import graph.Graph;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.CommandsRunner;
import tools.Program;
import tools.ProgramRunner;

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
		
		runningControlPane = new RunningControlPane();

		buttonsBox = new HBox();
		buttonsBox.setMaxHeight(50);
		buttonsBox.setMinHeight(50);
		Button runButton = new Button("Run");
		
		runButton.setOnAction((event) -> runProgram());

		buttonsBox.getChildren().add(runButton);
		
		controls.getChildren().addAll(buttonsBox,runningControlPane);

		this.graphPane.setOnMouseClicked((event) -> this.hideShowEditor(event, graphPane));
		this.codePane.setOnMouseClicked((event) -> this.hideShowEditor(event, codePane));


		this.getChildren().addAll(controls, display);

	}
	
	public void save() {
		codePane.saveEditor();
		graphPane.saveEditor();
	}
	
	public void load(EditorType editorType) {
		if(editorType == EditorType.Code) {
			codePane.loadEditor();
		} else {
			graphPane.loadEditor();
		}
	}
	
	public void create(EditorType editorType) {
		if(editorType == EditorType.Code) {
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
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
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
		
		List<Command> commands = programRunner.runProgram(codePane.getEditor().name, graphPane.getEditor().getGraph());
		
		CommandsRunner commandsRunner = new CommandsRunner(commands,graphPane.getEditor().getGraph(), runningControlPane, this );
		
		startRunPreparation();
		
		commandsRunner.start();
	}
	
	private void startRunPreparation() {
		runningControlPane.setVisible(true);
		addOutputConsole();
	}

	public void endRunPreparation() {
		runningControlPane.setVisible(false);
		runningControlPane.getPauseButton().setOnAction(null);
		runningControlPane.getPlayButton().setOnAction(null);
		runningControlPane.getStepButton().setOnAction(null);
		removeOutputConsole();
	}

	private void addOutputConsole() {
		display.getItems().clear();
		VBox vBox = new VBox();
		vBox.getChildren().addAll( consoleOutputArea, graphPane);
		display.getItems().addAll(codePane, vBox);
		
	}
	
	private void removeOutputConsole() {
		display.getItems().clear();
		display.getItems().addAll(codePane, graphPane);
	}
}
