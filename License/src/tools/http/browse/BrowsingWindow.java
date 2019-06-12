package tools.http.browse;

import java.util.List;

import editors.EditorType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import tools.http.ApiClient;
import tools.http.models.ApiResponse;

public class BrowsingWindow extends Stage {
	private EditorType editorType;
	private ApiClient apiClient;
	private List<ApiResponse> items;
	private VBox itemsBox;

	public BrowsingWindow(EditorType editorType) {
		this.editorType = editorType;
		this.apiClient = new ApiClient();

		new Thread(() -> {
			fetchEntities();
		}).start();

		HBox titleBox = new HBox();

		titleBox.setAlignment(Pos.TOP_CENTER);
		titleBox.setMinHeight(100);
		Label title = new Label("Browse");
		title.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		title.setPadding(new Insets(5, 5, 5, 20));

		titleBox.getChildren().add(title);
		title.setPadding(new Insets(20, 0, 30, 0));
		
		itemsBox = new VBox();
		itemsBox.setSpacing(3);
		ScrollPane scrollPane = new ScrollPane(itemsBox);
		scrollPane.setMinSize(450, 300);
		scrollPane.setMaxSize(450, 300);
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(title,scrollPane);
		vBox.setSpacing(3);
		vBox.setPadding(new Insets(0, 0, 50, 20));
		vBox.setAlignment(Pos.CENTER);
		
		this.setTitle("Browser");
		this.initStyle(StageStyle.UTILITY);
		Scene scene = new Scene(vBox, 550, 400);
		scene.getStylesheets().add(this.getClass().getResource("/resources/java-keywords.css").toExternalForm());
		this.setScene(scene);
		this.show();
	}

	private void displayEntities() {
		itemsBox.getChildren().clear();
		
		for(ApiResponse item : items) {
			itemsBox.getChildren().add(new BrowseItem(editorType,item));
		}
	}

	private void fetchEntities() {
		if (editorType == EditorType.Code) {
			items = apiClient.getPrograms();
		} else {
			items = apiClient.getGraphs();
		}

		Platform.runLater(new Thread(() -> {
			displayEntities();
		}));
	}
}