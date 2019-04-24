package panes;

import java.nio.file.Paths;

import editors.CodeEditor;
import main.MiscTools;

public class CodePane extends EditorPane {

	public void loadEditor() {
		String path = MiscTools.getFileInput("../Data/Programs", "*.java");

		if (path != null) {
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createEditor(name);
			editor.loadData(path);
		}
	}

	public void createEditor(String name) {
		ensureEditorSaved();
		editor = new CodeEditor(name);
		this.getChildren().clear();
		this.getChildren().add(editor.getDisplay());
	}
}
