package main;

import java.io.File;

import javafx.stage.FileChooser;

public final class MiscTools {
	public static String getFileInput(String initialDirectory, String extension) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File("initialDirectory"));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", extension));
		File fileToOpen = fileChooser.showOpenDialog(null);

		return fileToOpen.getAbsolutePath();
	}
}
