package graph.graphic;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class GraphElementValueField extends TextField {

	public GraphElementValueField(DoubleBinding xBinding, DoubleBinding yBinding) {
		this(xBinding, yBinding, "");
	}

	public GraphElementValueField(DoubleBinding xBinding, DoubleBinding yBinding, String initialValue) {
		super();
		
		this.getStyleClass().add("value-field");

		textProperty().addListener((ov, prevText, currText) -> {
			Platform.runLater(() -> {
				Text text = new Text(currText);
				text.setFont(getFont());
				double width = text.getLayoutBounds().getWidth() + getPadding().getLeft() + getPadding().getRight()
						+ 2d;
				setPrefWidth(width);
				positionCaret(getCaretPosition());
			});
		});
		DoubleBinding newX = Bindings.createDoubleBinding(() -> xBinding.get() - this.getWidth() / 2, xBinding,
				this.lengthProperty());
		DoubleBinding newY = Bindings.createDoubleBinding(() -> yBinding.get() - this.getHeight() / 2, yBinding,
				this.lengthProperty());

		translateXProperty().bind(newX);
		translateYProperty().bind(newY);
		setText(initialValue);
		if (initialValue.isEmpty() || initialValue.equals(" "))
			hideInput();
	}

	public void hideInput() {
		this.setVisible(false);
	}

	public void showInput() {
		this.setVisible(true);
		hideLater();
	}

	private void hideLater() {
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				if (getText().equals(" "))
					hideInput();
			}
		}, 10000);
	}
}
