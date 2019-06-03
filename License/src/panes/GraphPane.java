package panes;

import java.nio.file.Paths;

import editors.GraphEditor;
import tools.MiscTools;

public class GraphPane extends EditorPane {
	public void loadEditor() {
		String path = MiscTools.getFileInput("../Data/Graphs", "*.graphml");

		if (path != null) {
			String name = Paths.get(path).getFileName().toString();
			name = name.substring(0, name.lastIndexOf("."));
			createEditor(name);
			editor.loadData(path);
		}
	}

	public void createEditor(String name) {
		ensureEditorSaved();
		editor = new GraphEditor(name);
		this.getChildren().clear();
		this.getChildren().add(editor.getDisplay());
	}

	public GraphEditor getEditor() {
		return (GraphEditor) editor;
	}

	public void loadLastSave() {
		createEditor(this.editor.name);
		editor.loadData("../Data/Graphs/" + this.editor.name + ".graphml");
		this.getChildren().clear();
		this.getChildren().add(editor.getDisplay());
	}
}
