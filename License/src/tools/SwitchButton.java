package tools;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class SwitchButton extends HBox {
	private TextField currentValue;
	private Button button;
	private String firstValue;
	private String secondValue;

	public SwitchButton(String firstValue, String secondValue) {
		super();

		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-padding-left: 10;");
		this.firstValue = firstValue;
		this.secondValue = secondValue;
		
		Image switchImage = new Image(getClass().getResourceAsStream("/resources/switch.png"));

		currentValue = new TextField(firstValue);
		currentValue.setEditable(false);
		currentValue.setMaxWidth(80);
		currentValue.setMinWidth(80);
		currentValue.setMinHeight(28);
		button = new Button("");
		button.setGraphic(new ImageView(switchImage));
		button.setTooltip(new Tooltip("Change"));

		this.getChildren().addAll(currentValue, button);
	}

	public Button getButton() {
		return button;
	}

	public void changeSwitch() {
		if (currentValue.getText().equals(firstValue))
			currentValue.setText(secondValue);
		else
			currentValue.setText(firstValue);
	}

	public void resetSwitch() {
		currentValue.setText(firstValue);
	}
}
