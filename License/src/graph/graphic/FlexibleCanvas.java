package graph.graphic;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;

public class FlexibleCanvas extends Pane {
	private double xStart, yStart;

	public FlexibleCanvas() {
		super();
		setOnMousePressed((event) -> handleMousePressed(event));
		setOnMouseDragged((event) -> handleMouseDragged(event));
		this.setMaxWidth(Double.MAX_VALUE);
	}

	private void handleMousePressed(MouseEvent event) {
		xStart = event.getX();
		yStart = event.getY();
	}

	private void handleMouseDragged(MouseEvent event) {
		double xDistance = event.getX() - xStart;
		double yDistance = event.getY() - yStart;
		xStart = event.getX();
		yStart = event.getY();

		for (Node node : getChildren()) {
			if (node instanceof GraphicNode) {
				GraphicNode gNode = ((GraphicNode) node);

				gNode.setX(xDistance);
				gNode.setY(yDistance);
			}
		}
		event.consume();
	}
}
