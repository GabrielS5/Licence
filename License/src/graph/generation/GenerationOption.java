package graph.generation;

import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class GenerationOption extends HBox {
	private CheckBox checkBox;
	private TextField userInput;

	public GenerationOption(String name) {
		super();

		this.setSpacing(15);
		this.setAlignment(Pos.CENTER_LEFT);

		checkBox = new CheckBox(name);
		checkBox.setMinWidth(300);
		userInput = new TextField();
		userInput.setMaxWidth(50);
		userInput.setVisible(false);
		
		checkBox.setOnAction((event) -> userInput.setVisible(!userInput.isVisible()));

		this.getChildren().addAll(checkBox, userInput);
	}

	public boolean isSelected() {
		return checkBox.isSelected();
	}

	public String getType() {
		return checkBox.getText();
	}

	public int getValue() {
		try {
			return Integer.parseInt(userInput.getText());
		} catch (Exception e) {
			return 0;
		}
	}
}
