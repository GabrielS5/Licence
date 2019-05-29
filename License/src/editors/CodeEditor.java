package editors;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tools.LineArrowFactory;
import tools.Program;
import tools.ProgramCompiler;

public class CodeEditor extends Editor {
	private VBox node;
	public CodeArea codeArea;
	private IntegerProperty errorLine = new SimpleIntegerProperty(0);
	private Button compileButton;
	private Button formatButton;

	public CodeEditor(String name) {
		this.name = name;
		this.modified = false;

		init();
	}

	private void init() {
		codeArea = new CodeArea();
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));

		codeArea.multiPlainChanges().successionEnds(Duration.ofMillis(500))
				.subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));

		this.codeArea.onKeyReleasedProperty().set((event) -> this.setModified(true));

		IntFunction<Node> lineNumbers = LineNumberFactory.get(codeArea);
		IntFunction<Node> errorLines = new LineArrowFactory(errorLine);

		IntFunction<Node> graphics = line -> {
			HBox hbox = new HBox(lineNumbers.apply(line), errorLines.apply(line));
			hbox.setAlignment(Pos.CENTER_LEFT);
			return hbox;
		};

		codeArea.setParagraphGraphicFactory(graphics);

		node = new VBox();

		HBox buttonsBox = new HBox();
		buttonsBox.setMaxHeight(50);
		buttonsBox.setMinHeight(50);

		compileButton = new Button("Compile");
		formatButton = new Button("Format");

		compileButton.setOnAction((event) -> compileCode());
		formatButton.setOnAction((event) -> formatCode());

		buttonsBox.getChildren().addAll(compileButton, formatButton);

		VirtualizedScrollPane<CodeArea> pane = new VirtualizedScrollPane<>(codeArea);
		pane.setPrefHeight(2000);
		node.getChildren().addAll(pane, buttonsBox);
		initializeProgram();
	}

	public Node getDisplay() {
		return node;
	}

	public void setText(String text) {
		codeArea.replaceText(text);
	}

	public String getText() {
		return codeArea.getText();
	}

	private StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
					: matcher.group("PAREN") != null ? "paren"
							: matcher.group("BRACE") != null ? "brace"
									: matcher.group("BRACKET") != null ? "bracket"
											: matcher.group("SEMICOLON") != null ? "semicolon"
													: matcher.group("STRING") != null ? "string"
															: matcher.group("COMMENT") != null ? "comment" : null;
			/* never happens */ assert styleClass != null;
			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

	private static final String[] KEYWORDS = new String[] { "abstract", "assert", "boolean", "break", "byte", "case",
			"catch", "char", "class", "const", "continue", "default", "do", "double", "else", "enum", "extends",
			"final", "finally", "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface",
			"long", "native", "new", "package", "private", "protected", "public", "return", "short", "static",
			"strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try", "void",
			"volatile", "while" };

	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	private static final String PAREN_PATTERN = "\\(|\\)";
	private static final String BRACE_PATTERN = "\\{|\\}";
	private static final String BRACKET_PATTERN = "\\[|\\]";
	private static final String SEMICOLON_PATTERN = "\\;";
	private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private static final Pattern PATTERN = Pattern.compile(
			"(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN
					+ ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
					+ "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");

	@Override
	public void loadData(String path) {
		StringBuffer stringBuffer = new StringBuffer();
		try (FileInputStream fileInputStream = new FileInputStream(path);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
			while (bufferedInputStream.available() > 0) {
				stringBuffer.append((char) bufferedInputStream.read());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setText(stringBuffer.toString());
		this.setModified(false);
	}

	@Override
	public void saveData() {
		File file = null;

		if (name.equals("")) {
			TextInputDialog dialog = new TextInputDialog("Program");

			dialog.setHeaderText("Enter the program name:");
			dialog.setContentText("Name:");

			Optional<String> dialogResult = dialog.showAndWait();
			name = dialogResult.get();
			setText(getText().replace("ClassName", name));
		}
		file = new File("../Data/Programs/" + name + ".java");

		try (FileOutputStream fos = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(fos)) {
			String text = getText();
			bos.write(text.getBytes());
			bos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setModified(false);
	}

	private void initializeProgram() {
		this.loadData("../Data/Programs/DefaultProgram.txt");
	}

	public void compileCode() {
		ProgramCompiler compiler = new ProgramCompiler();
		try {
			List<Diagnostic<? extends JavaFileObject>> result = compiler
					.compile(new File("../Data/Programs/" + name + ".java"));
			if (result.size() == 0) {
				return;
			}

			for (Diagnostic<?> diagnostic : result) {
				System.out.println(diagnostic.getLineNumber());
				this.errorLine.set((int) diagnostic.getLineNumber());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void runCode() {
		Class<?> programClass = null;
		String file = "../Data/Programs/" + name + ".java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, file);

		File directory = new File("../Data/Programs");
		try {
			URLClassLoader classLoader = new URLClassLoader(new URL[] { directory.toURL() });
			programClass = classLoader.loadClass(name);
			classLoader.close();
		} catch (Exception e1) {
			// something went wrong..
			e1.printStackTrace();
		}

		try {
			Object instance = programClass.newInstance();
			System.out.println(instance instanceof Program);

			Method method = programClass.getDeclaredMethod("run");
			method.setAccessible(true);
			method.invoke(instance);
		} catch (Exception e1) {
			// something went wrong..
			e1.printStackTrace();
		}
	}

	public void formatCode() {
		Formatter formatter = new Formatter();

		try {
			this.setText(formatter.formatSource(getText()));
		} catch (FormatterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setModified(boolean modified) {
		this.errorLine.set(0);
		this.modified = modified;
	}
	
	public void disableButtons() {
		compileButton.setDisable(true);
		formatButton.setDisable(true);
	}
	
	public void enableButtons() {
		compileButton.setDisable(false);
		formatButton.setDisable(false);
	}

}
