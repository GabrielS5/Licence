package tools;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.common.base.Stopwatch;

import commands.Command;
import graph.Graph;
import graph.graphic.GraphicGraph;

public class ProgramRunner {

	@SuppressWarnings({ "unchecked", "deprecation" })
	public RunResult runProgram(String program, GraphicGraph graphicGraph) {
		File directory = new File("../Data/Programs");
		
		Class<?> programClass = null;
		List<Command> commands = new ArrayList<Command>();
		Graph graph = new Graph(graphicGraph);
		RunResult result = new RunResult();
		double runTime;
		
		try {
			URLClassLoader classLoader = new URLClassLoader(
												new URL[] {directory.toURL() });
			programClass = classLoader.loadClass(program);
			classLoader.close();

			Object instance = programClass.newInstance();

			if (!(instance instanceof Program))
				throw new Exception();

			Method method = programClass.getDeclaredMethod("run", graph.getClass());
			method.setAccessible(true);

			Stopwatch stopwatch = Stopwatch.createStarted();

			method.invoke(instance, graph);

			runTime = stopwatch.elapsed().toMillis() / 1000.0;

			Method getCommandsMethod = programClass.getMethod("getCommands");
			getCommandsMethod.setAccessible(true);

			commands.addAll(((List<Command>) getCommandsMethod.invoke(instance)));
		} catch (Exception e1) {
			result.setSuccessful(false);
			return result;
		}
		
		commands.addAll(graph.getCommands());

		Collections.sort(commands, Comparator.comparing(Command::getCommandOrder));

		result.setSuccessful(true);
		result.setCommands(commands);
		result.setRunTime(runTime);

		return result;
	}
}
