package graph.generation;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GenerationDialog extends Stage {

	public GenerationDialog() {
		final TextField name = new TextField() ;
        final TextField addr = new TextField() ;
        final TextField wp = new TextField() ;
        final Label labelUsername = new Label("Username");
        final Label labelAddress = new Label("Address");
        final Label labelWebPage = new Label("Web Page");
        final Button btn = new Button("Add");

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setPadding(new Insets(10));

        gridPane.add(labelUsername, 0, 0);
        gridPane.add(name, 1, 0);
        gridPane.add(labelAddress, 0, 1);
        gridPane.add(addr, 1, 1);
        gridPane.add(labelWebPage, 0, 2);
        gridPane.add(wp, 1, 2);
        gridPane.add(btn, 0, 3, 2, 1);
        GridPane.setHalignment(btn, HPos.CENTER);

        this.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(gridPane);
        this.setScene(scene);
        this.show();
	}
}
