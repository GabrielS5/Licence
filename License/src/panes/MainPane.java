package panes;

import editors.EditorType;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.GraphWrapper;
import tools.ProgramRunner;

public class MainPane extends VBox {
	private CodePane codePane;
	private GraphPane graphPane;
	private HBox buttonsBox;
	private SplitPane display;

	public MainPane() {
		super();

		codePane = new CodePane();
		codePane.createEditor("");
		graphPane = new GraphPane();
		graphPane.createEditor("");

		display = new SplitPane();
		display.getItems().addAll(codePane, graphPane);

		buttonsBox = new HBox();
		buttonsBox.setMaxHeight(50);
		buttonsBox.setMinHeight(50);
		Button runButton = new Button("Run");

		runButton.setOnAction((event) -> runProgram());

		buttonsBox.getChildren().add(runButton);

		this.graphPane.setOnMouseClicked((event) -> this.hideShowEditor(event, graphPane));
		this.codePane.setOnMouseClicked((event) -> this.hideShowEditor(event, codePane));

		this.getChildren().addAll(buttonsBox, display);

	}

	public void save() {
		codePane.saveEditor();
		graphPane.saveEditor();
	}

	public void load(EditorType editorType) {
		if (editorType == EditorType.Code) {
			codePane.loadEditor();
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
		
		GraphWrapper graphWrapper = new GraphWrapper(graphPane.getEditor().getGraph());

		programRunner.runProgram(codePane.getEditor().name, graphPane.getEditor().getGraph());
	}
}
