package tools.http.browse;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import editors.EditorType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import tools.http.models.ApiResponse;

public class BrowseItem extends HBox {
	private ApiResponse apiResponse;
	private EditorType editorType;

	public BrowseItem(EditorType editorType, ApiResponse apiResponse) {
		this.editorType = editorType;
		this.apiResponse = apiResponse;

		Label label = new Label(apiResponse.getName());
		label.setMinWidth(300);
		label.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
		label.getStyleClass().add("transparent-background");
		label.getStyleClass().add("round-corners");
		label.setPadding(new Insets(5, 5, 5, 5));

		Image downloadImage = new Image(getClass().getResourceAsStream("/resources/download.png"));

		Button downloadButton = new Button("");
		downloadButton.setGraphic(new ImageView(downloadImage));

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER_LEFT);
		hBox.setSpacing(100);
		hBox.getChildren().addAll(label, downloadButton);
		hBox.setPadding(new Insets(5, 5, 5, 5));

		downloadButton.setOnAction((event) -> downloadItem());
		this.getChildren().add(hBox);

	}

	private void downloadItem() {
		File file;
		
		if(editorType == EditorType.Code) {
			file = new File("../Data/Programs/" + apiResponse.getName() + ".java");
		} else {
			file = new File("../Data/Graphs/" + apiResponse.getName() + ".graphml");
		}
		
		try (FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {
			String text = apiResponse.getText();
			bos.write(text.getBytes());
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
