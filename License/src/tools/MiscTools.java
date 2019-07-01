package tools;

import java.io.File;
import java.util.regex.Pattern;

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
	
	public static Pattern getCodeEditorPattern() {
		String[] keywords = new String[] { "abstract", "assert", "boolean", "break", "byte", "case",
				"catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends",
				"final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
				"long", "native", "new", "package", "private", "protected", "public", "return", "short", "static",
				"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
				"volatile", "while" };
		
		
		Pattern pattern = Pattern.compile("(?<Keyword>\\b("
				+ String.join("|", keywords) + ")\\b)"
				+ "|(?<RoundParanthesis>\\(|\\))" 
				+ "|(?<CurlyParanthesis>\\{|\\})" 
				+ "|(?<SquareParanthesis>\\[|\\])"
				+ "|(?<PointComma>\\;)" 
				+ "|(?<String>\"([^\"\\\\]|\\\\.)*\")" 
				+ "|(?<SingleLineComment>//[^\\n]*)"
				+ "|(?<MultipleLineComment>/\\*(.|\\R)*?\\*/)");
		
		
		return pattern;
	}

	public static Pair<Line, Line> createArrowHead(GraphicNode source, GraphicNode destination) {
		Line arrowLine1 = new Line(), arrowLine2 = new Line();

		DoubleBinding arrowLineXEndBinding = Bindings.createDoubleBinding(() -> {
			double distance = (source.xProperty.get() - destination.xProperty.get()) / 4;

			return destination.xProperty.get() + distance;
		}, destination.xProperty, source.xProperty);

		DoubleBinding arrowLineYEndBinding = Bindings.createDoubleBinding(() -> {
			double distance = (source.yProperty.get() - destination.yProperty.get()) / 4;

			return destination.yProperty.get() + distance;
		}, destination.yProperty, source.yProperty);

		DoubleBinding arrowLine1YBinding = Bindings.createDoubleBinding(() -> {
			return calculateArrowY(source.xProperty.get(), source.yProperty.get(), destination.xProperty.get(),
					destination.yProperty.get(), 1);
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		DoubleBinding arrowLine1XBinding = Bindings.createDoubleBinding(() -> {
			return calculateArrowX(source.xProperty.get(), source.yProperty.get(), destination.xProperty.get(),
					destination.yProperty.get(), -1);
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		DoubleBinding arrowLine2YBinding = Bindings.createDoubleBinding(() -> {
			return calculateArrowY(source.xProperty.get(), source.yProperty.get(), destination.xProperty.get(),
					destination.yProperty.get(), -1);
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		DoubleBinding arrowLine2XBinding = Bindings.createDoubleBinding(() -> {
			return calculateArrowX(source.xProperty.get(), source.yProperty.get(), destination.xProperty.get(),
					destination.yProperty.get(), 1);
		}, source.xProperty, destination.xProperty, source.yProperty, destination.yProperty);

		arrowLine1.endXProperty().bind(arrowLineXEndBinding);
		arrowLine1.endYProperty().bind(arrowLineYEndBinding);
		arrowLine2.endXProperty().bind(arrowLineXEndBinding);
		arrowLine2.endYProperty().bind(arrowLineYEndBinding);

		arrowLine1.startXProperty().bind(arrowLine1XBinding);
		arrowLine1.startYProperty().bind(arrowLine1YBinding);
		arrowLine2.startXProperty().bind(arrowLine2XBinding);
		arrowLine2.startYProperty().bind(arrowLine2YBinding);

		return new Pair<Line, Line>(arrowLine1, arrowLine2);
	}

	private static double calculateArrowX(double sourceX, double sourceY, double destinationX, double destinationY,
			double orientation) {
		double lineHypot = Math.hypot(sourceX - destinationX, sourceY - destinationY);
		destinationX = destinationX + (sourceX - destinationX) / 4;
		destinationY = destinationY + (sourceY - destinationY) / 4;

		double dx = (sourceX - destinationX) * 10 / lineHypot;
		double oy = (sourceY - destinationY) * 5 / lineHypot;

		return destinationX + dx + oy * orientation;
	}

	private static double calculateArrowY(double sourceX, double sourceY, double destinationX, double destinationY,
			double orientation) {
		double lineHypot = Math.hypot(sourceX - destinationX, sourceY - destinationY);
		destinationX = destinationX + (sourceX - destinationX) / 4;
		destinationY = destinationY + (sourceY - destinationY) / 4;

		double dy = (sourceY - destinationY) * 10 / lineHypot;
		double ox = (sourceX - destinationX) * 5 / lineHypot;

		return destinationY + dy + ox * orientation;
	}
}
