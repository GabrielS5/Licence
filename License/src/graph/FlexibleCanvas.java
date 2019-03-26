package graph;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class FlexibleCanvas extends Pane {
	private double xStart, yStart;

	public FlexibleCanvas() {
		super();
		setOnScroll(new EventHandler<ScrollEvent>() {
			public void handle(ScrollEvent event) {
				double zoom_fac = 1.05;
				double delta_y = event.getDeltaY();

				if (delta_y < 0) {
					zoom_fac = 2.0 - zoom_fac;
				}

				Scale newScale = new Scale();
				newScale.setPivotX(0);
				newScale.setPivotY(0);
				newScale.setX(getScaleX() * zoom_fac);
				newScale.setY(getScaleY() * zoom_fac);

				// content.getTransforms().add(newScale);

				event.consume();

			}
		});

		setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				xStart = event.getX();
				yStart = event.getY();
			}
		});

		setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				double xDistance = event.getX() - xStart;
				double yDistance = event.getY() - yStart;
				xStart = event.getX();
				yStart = event.getY();

				for (Node node : getChildren()) {
					if (node instanceof GraphNode) {
						GraphNode gNode = ((GraphNode) node);

						gNode.setX(xDistance);
						gNode.setY(yDistance);
					}
				}
				event.consume();
			}
		});

		this.setMaxWidth(Double.MAX_VALUE);

	}
}
