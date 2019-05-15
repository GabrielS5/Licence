package tools;


import java.util.function.IntFunction;

import org.reactfx.value.Val;

import javafx.beans.property.IntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class LineArrowFactory implements IntFunction<Node> {

	private ObservableValue<Integer> line;

	public LineArrowFactory(IntegerProperty line) {
        this.line = line.asObject();
    }
    
	@Override
	public Node apply(int value) {
		Polygon arrow = new Polygon(0.0, 1.0, 10.0, 5.0, 0.0, 9.0);
		arrow.setFill(Color.RED);

        ObservableValue<Boolean> visibilityMap = Val.map(
                line,
                lineValue -> lineValue == value + 1);

        arrow.visibleProperty().bind(Val.flatMap(arrow.sceneProperty(), scene -> {
            return scene != null ? visibilityMap : Val.constant(false);
        }));

        return arrow;
	}

}
