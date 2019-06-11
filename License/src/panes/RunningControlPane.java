package panes;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class RunningControlPane extends Pane {

	private Slider slider;
	private Button playButton;
	private Button pauseButton;
	private Button stepButton;
	private Button exitButton;
	private Label runningTimeLabel;
	private Label commandsNumberLabel;

	private int commandsNumber;
	private int currentCommandsNumber;

	public RunningControlPane() {

		runningTimeLabel = new Label();
		runningTimeLabel.getStyleClass().add("transparent-background");
		runningTimeLabel.getStyleClass().add("round-corners");
		runningTimeLabel.setMinWidth(150);
		
		commandsNumberLabel = new Label();
		commandsNumberLabel.getStyleClass().add("transparent-background");
		commandsNumberLabel.getStyleClass().add("round-corners");
		commandsNumberLabel.setMinWidth(75);
		
		runningTimeLabel.setMinHeight(30);
		runningTimeLabel.setFont(Font.font("Verdana", FontWeight.NORMAL, 13));
		runningTimeLabel.setAlignment(Pos.CENTER);
		
		commandsNumberLabel.setMinHeight(30);
		commandsNumberLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 15));
		commandsNumberLabel.setAlignment(Pos.CENTER);
		
		this.setLabelVisibility(false);

		HBox box = new HBox();
		box.setSpacing(10);
		slider = new Slider(0.15, 2, 1);
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

		box.setMinSize(700, 50);
		box.setMaxSize(700, 50);
		box.getChildren().addAll(playButton, pauseButton, stepButton, exitButton, slider, commandsNumberLabel,
				runningTimeLabel);
		box.setAlignment(Pos.CENTER_RIGHT);

		this.getChildren().add(box);
		this.setDisable(true);
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

	public void setLabelVisibility(boolean visible) {
		this.runningTimeLabel.setVisible(visible);
		this.commandsNumberLabel.setVisible(visible);
	}

	public void setRunningTime(double runningTime) {
		this.runningTimeLabel.setText("Running time: " + runningTime);
	}

	public void initializeCommandsNumber(int number) {
		this.commandsNumber = number;
		this.currentCommandsNumber = 0;
		updateCommandsNumberLabel();
	}

	public void incrementCommandsNumber() {
		currentCommandsNumber++;
		updateCommandsNumberLabel();
	}

	private void updateCommandsNumberLabel() {
		Platform.runLater(new Thread(() -> {
			commandsNumberLabel.setText(currentCommandsNumber + "/" + commandsNumber);
		}));
	}
}
