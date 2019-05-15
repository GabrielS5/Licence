package tools;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

public class ProgramCompiler {
	private JavaCompiler javaCompiler;

	public ProgramCompiler() {
		javaCompiler = ToolProvider.getSystemJavaCompiler();
	}

	public List<Diagnostic<? extends JavaFileObject>> compile(File file) throws Exception {
		DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
		StandardJavaFileManager fileManager = javaCompiler.getStandardFileManager(diagnostics, Locale.getDefault(),
				null);

		JavaFileObject javaObject = getJavaObject(file, fileManager);

		CompilationTask compilerTask = javaCompiler.getTask(null, fileManager, diagnostics, null, null,
				Arrays.asList(javaObject));

		compilerTask.call();
		
		return diagnostics.getDiagnostics();
	}

	private JavaFileObject getJavaObject(File file, StandardJavaFileManager fileManager) {
		return fileManager.getJavaFileObjects(file).iterator().next();
	}
}
