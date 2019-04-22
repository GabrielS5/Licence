package panes;

import java.nio.file.Paths;

import editors.CodeEditor;
import editors.Editor;
import editors.EditorType;
import editors.GraphEditor;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import main.MiscTools;

public class EditingPane extends StackPane {
	private Editor editor = null;

	public void saveEditor() {
		if (editor != null) {
			editor.saveData();
		}
	}

	public void ensureEditorSaved() {
		if (editor != null && editor.modified) {
			Alert saveEnquiry = new Alert(AlertType.NONE, "Do you want to save your progress?", ButtonType.YES,
					ButtonType.NO);
			saveEnquiry.showAndWait();

			if (saveEnquiry.getResult() == ButtonType.YES) {
				this.saveEditor();
			}
		}
	}

	public void loadEditor(EditorType editorType) {
		if (editorType == EditorType.Graph)
			this.loadGraphEditor();
		else
			this.loadCodeEditor();
	}

	public void createEditor(EditorType editorType) {
		if (editorType == EditorType.Graph)
			this.createGraphEditor("");
		else
			this.createCodeEditor("");
	}

	private void loadCodeEditor() {
		String path = MiscTools.getFileInput("../Data/Programs", "*.java");

		if (path != null) {
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createCodeEditor(name);
			editor.loadData(path);
		}
	}

	private void loadGraphEditor() {
		String path = MiscTools.getFileInput("../Data/Graphs", "*.graphml");

		if (path != null) {
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createGraphEditor(name);
			editor.loadData(path);
		}
	}

	private void createCodeEditor(String name) {
		editor = new CodeEditor(name);
		this.getChildren().clear();
		this.getChildren().add(editor.getDisplay());
	}

	private void createGraphEditor(String name) {
		editor = new GraphEditor(name);
		this.getChildren().clear();
		this.getChildren().add(editor.getDisplay());
	}
}
