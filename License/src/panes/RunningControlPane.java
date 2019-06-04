package panes;

import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class RunningControlPane extends Pane {

	private Slider slider;
	private Button playButton;
	private Button pauseButton;
	private Button stepButton;
	private Button exitButton;

	public RunningControlPane() {

		HBox box = new HBox();
		box.setSpacing(10);
		slider = new Slider(0.10, 2, 1);
		slider.setShowTickLabels(true);
		slider.setMinWidth(200);
		slider.setMajorTickUnit(0.25);

		Image playImage = new Image(getClass().getResourceAsStream("/resources/play.png"));
		playButton = new Button("");
		playButton.setGraphic(new ImageView(playImage));

		Image pauseImage = new Image(getClass().getResourceAsStream("/resources/pause.png"));
		pauseButton = new Button("");
		pauseButton.setGraphic(new ImageView(pauseImage));

		Image stepImage = new Image(getClass().getResourceAsStream("/resources/step.png"));
		stepButton = new Button("");
		stepButton.setGraphic(new ImageView(stepImage));


		Image exitImage = new Image(getClass().getResourceAsStream("/resources/exit.png"));
		exitButton = new Button("");
		exitButton.setGraphic(new ImageView(exitImage));

		box.setMinSize(500, 50);
		box.setMaxSize(500, 50);
		box.getChildren().addAll(playButton, pauseButton, stepButton, exitButton, slider);
		box.setAlignment(Pos.CENTER_RIGHT);

		this.getChildren().add(box);
		this.setVisible(false);
	}

	public DoubleProperty getSliderValue() {
		return this.slider.valueProperty();
	}

	public Button getPlayButton() {
		return playButton;
	}

	public Button getPauseButton() {
		return pauseButton;
	}

	public Button getStepButton() {
		return stepButton;
	}
	
	public Button getExitButton() {
		return exitButton;
	}
}
