package tools;

import java.io.File;

import graph.graphic.GraphicNode;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Pair;

public final class MiscTools {
	public static String getFileInput(String initialDirectory, String extension) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(initialDirectory));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Files", extension));
		File fileToOpen = fileChooser.showOpenDialog(null);

		return fileToOpen.getAbsolutePath();
	}

	public static Pair<Line, Line> createArrowHead(GraphicNode source, GraphicNode destination) {
		Line arrowLine1 = new Line(), arrowLine2 = new Line();

		DoubleBinding arrowLine1YBinding = Bindings.createDoubleBinding(() -> {
			double lineHypot = Math.hypot(source.xProperty.get() - destination.xProperty.get(),
					source.yProperty.get() - destination.yProperty.get());

			double dy = (source.yProperty.get() - destination.yProperty.get()) * 10 / lineHypot;
			double ox = (source.xProperty.get() - destination.xProperty.get()) * 5 / lineHypot;

			return destination.yProperty.get() + dy + ox;
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		DoubleBinding arrowLine1XBinding = Bindings.createDoubleBinding(() -> {
			double lineHypot = Math.hypot(source.xProperty.get() - destination.xProperty.get(),
					source.yProperty.get() - destination.yProperty.get());

			double dx = (source.xProperty.get() - destination.xProperty.get()) * 10 / lineHypot;
			double oy = (source.yProperty.get() - destination.yProperty.get()) * 5 / lineHypot;

			return destination.xProperty.get() + dx - oy;
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		DoubleBinding arrowLine2YBinding = Bindings.createDoubleBinding(() -> {
			double lineHypot = Math.hypot(source.xProperty.get() - destination.xProperty.get(),
					source.yProperty.get() - destination.yProperty.get());

			double dy = (source.yProperty.get() - destination.yProperty.get()) * 10 / lineHypot;
			double ox = (source.xProperty.get() - destination.xProperty.get()) * 5 / lineHypot;

			return destination.yProperty.get() + dy - ox;
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		DoubleBinding arrowLine2XBinding = Bindings.createDoubleBinding(() -> {
			double lineHypot = Math.hypot(source.xProperty.get() - destination.xProperty.get(),
					source.yProperty.get() - destination.yProperty.get());

			double dx = (source.xProperty.get() - destination.xProperty.get()) * 10 / lineHypot;
			double oy = (source.yProperty.get() - destination.yProperty.get()) * 5 / lineHypot;

			return destination.xProperty.get() + dx + oy;
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		arrowLine1.endXProperty().bind(destination.xProperty);
		arrowLine1.endYProperty().bind(destination.yProperty);
		arrowLine2.endXProperty().bind(destination.xProperty);
		arrowLine2.endYProperty().bind(destination.yProperty);

		arrowLine1.startXProperty().bind(arrowLine1XBinding);
		arrowLine1.startYProperty().bind(arrowLine1YBinding);
		arrowLine2.startXProperty().bind(arrowLine2XBinding);
		arrowLine2.startYProperty().bind(arrowLine2YBinding);

		return new Pair<Line, Line>(arrowLine1, arrowLine2);
	}
}
