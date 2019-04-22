package panes;

import editors.CodeEditor;
import editors.Editor;
import editors.GraphEditor;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class RunningPane extends SplitPane {
	private CodeEditor codeEditor;
	private GraphEditor graphEditor;

	public RunningPane() {
		this.codeEditor = new CodeEditor("New");
		this.graphEditor = new GraphEditor("New");
		this.getItems().add(codeEditor.getDisplay());
		this.getItems().add(graphEditor.getDisplay());

		this.graphEditor.getDisplay().setOnMouseClicked((event) -> this.hideShowEditor(event, graphEditor));

		this.codeEditor.getDisplay().setOnMouseClicked((event) -> this.hideShowEditor(event, codeEditor));

	}

	private void hideShowEditor(MouseEvent event, Editor editor) {
		if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
			if (this.getItems().size() == 1) {
				this.getItems().clear();
				this.getItems().add(codeEditor.getDisplay());
				this.getItems().add(graphEditor.getDisplay());
			} else {
				this.getItems().clear();
				this.getItems().add(editor.getDisplay());
			}
		}
	}
}
