package editors;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import com.google.googlejavaformat.java.Formatter;
import com.google.googlejavaformat.java.FormatterException;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import tools.LineArrowFactory;
import tools.MiscTools;
import tools.ProgramCompiler;
import tools.http.ApiClient;
import tools.http.models.ApiEntity;

public class CodeEditor extends Editor {
	private ApiClient apiClient;
	private VBox node;
	public CodeArea codeArea;
	private IntegerProperty errorLine = new SimpleIntegerProperty(0);
	private Button compileButton;
	private Button formatButton;
	private Button saveButton;
	private Button runButton;
	private Button uploadButton;
	private TextField nameField;

	public CodeEditor(String name) {
		this.name = name;
		this.modified = false;
		apiClient = new ApiClient();

		init();
	}

	private void init() {
		
		codeArea = new CodeArea();
		codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
		codeArea.multiPlainChanges().successionEnds(Duration.ofMillis(500))
				.subscribe(ignore -> codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText())));

		codeArea.onKeyReleasedProperty().set((event) -> this.setModified(true));

		IntFunction<Node> lineNumbers = LineNumberFactory.get(codeArea);
		IntFunction<Node> errorLines = new LineArrowFactory(errorLine);

		IntFunction<Node> graphics = line -> {
			HBox hbox = new HBox(lineNumbers.apply(line), errorLines.apply(line));
			hbox.setAlignment(Pos.CENTER_LEFT);
			return hbox;
		};
		codeArea.setParagraphGraphicFactory(graphics);

		node = new VBox();

		Image compileImage = new Image(getClass().getResourceAsStream("/resources/compile.png"));
		Image saveImage = new Image(getClass().getResourceAsStream("/resources/save.png"));
		Image formatImage = new Image(getClass().getResourceAsStream("/resources/format.png"));
		Image runImage = new Image(getClass().getResourceAsStream("/resources/run.png"));
		Image uploadImage = new Image(getClass().getResourceAsStream("/resources/upload.png"));

		HBox buttonsBox = new HBox();
		buttonsBox.setMaxHeight(40);
		buttonsBox.setMinHeight(40);
		buttonsBox.setSpacing(5);

		compileButton = new Button("");
		compileButton.setGraphic(new ImageView(compileImage));
		compileButton.setTooltip(new Tooltip("Compile"));

		runButton = new Button("");
		runButton.setGraphic(new ImageView(runImage));

		formatButton = new Button("");
		formatButton.setGraphic(new ImageView(formatImage));
		formatButton.setTooltip(new Tooltip("Format code"));

		saveButton = new Button("");
		saveButton.setGraphic(new ImageView(saveImage));
		saveButton.setTooltip(new Tooltip("Save"));

		uploadButton = new Button("");
		uploadButton.setGraphic(new ImageView(uploadImage));
		uploadButton.setTooltip(new Tooltip("Upload your program"));

		nameField = new TextField(this.name);
		nameField.setMinWidth(150);
		nameField.setMinHeight(28);

		compileButton.setOnAction((event) -> compileCode());
		formatButton.setOnAction((event) -> formatCode());
		saveButton.setOnAction((event) -> saveData());
		uploadButton.setOnAction((event) -> exportData());

		buttonsBox.setAlignment(Pos.CENTER_LEFT);
		buttonsBox.setPadding(new Insets(0, 20, 0, 20));
		buttonsBox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		buttonsBox.setBorder(new Border(new BorderStroke(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK,
				BorderStrokeStyle.SOLID, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE, BorderStrokeStyle.NONE,
				CornerRadii.EMPTY, BorderStroke.THIN, Insets.EMPTY)));
		buttonsBox.getChildren().addAll(nameField, compileButton, formatButton, runButton, uploadButton, saveButton);

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
		Matcher matcher = MiscTools.getCodeEditorPattern().matcher(text);
		int lastEnd = 0;
		StyleSpansBuilder<Collection<String>> styleSpansBuilder = new StyleSpansBuilder<>();
		
		while (matcher.find()) {
			String style = null;
			
			if(matcher.group("Keyword") != null)
				style = "keyword";
			else if(matcher.group("RoundParanthesis") != null)
				style = "round-paranthesis";
			else if(matcher.group("CurlyParanthesis") != null)
				style = "curly-paranthesis";
			else if(matcher.group("SquareParanthesis") != null)
				style = "square-paranthesis";
			else if(matcher.group("PointComma") != null)
				style = "bold";
			else if(matcher.group("String") != null)
				style = "string";
			else if(matcher.group("SingleLineComment") != null 
					|| matcher.group("MultipleLineComment") != null)
				style = "comment";

			assert style != null;
			styleSpansBuilder.add(Collections.emptyList(), matcher.start() - lastEnd);
			styleSpansBuilder.add(Collections.singleton(style), matcher.end() - matcher.start());
			lastEnd = matcher.end();
		}
		styleSpansBuilder.add(Collections.emptyList(), text.length() - lastEnd);
		return styleSpansBuilder.create();
	}

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
			try {
				name = dialogResult.get();
			} catch (Exception e) {
				return;
			}

			replaceName(name);
		} else if (!nameField.getText().equals(name)) {
			replaceName(nameField.getText());
		}

		file = new File("../Data/Programs/" + name + ".java");

		try (FileOutputStream fileOutputStream = new FileOutputStream(file);
				BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream)) {
			String text = getText();
			bufferedOutputStream.write(text.getBytes());
			bufferedOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		this.setModified(false);
	}

	@Override
	public void exportData() {
		saveData();
		new Thread(() -> {
			apiClient.postProgram(new ApiEntity(name, codeArea.getText()));
		}).start();
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
				this.errorLine.set((int) diagnostic.getLineNumber());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void formatCode() {

		try {
			
			Formatter formatter = new Formatter();
			String formattedText = formatter.formatSource(getText());
			this.setText(formattedText);
			
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
		saveButton.setDisable(true);
		runButton.setDisable(true);
		uploadButton.setDisable(true);
	}

	public void enableButtons() {
		compileButton.setDisable(false);
		formatButton.setDisable(false);
		saveButton.setDisable(false);
		runButton.setDisable(false);
		uploadButton.setDisable(false);
	}

	public Button getRunButton() {
		return runButton;
	}

	private void replaceName(String name) {

		String[] words = getText().split(" ");
		String currentClassName = null;

		for (int i = 0; i < words.length; i++) {
			if (words[i].equals("class")) {
				currentClassName = words[i + 1];
				break;
			}
		}

		this.name = name;
		this.nameField.setText(name);
		setText(getText().replace(currentClassName, name));
	}
}
