package tools;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import commands.Command;
import graph.Graph;
import graph.GraphicGraph;

public class ProgramRunner {
	@SuppressWarnings("unchecked")
	public List<Command> runProgram(String program, GraphicGraph graphicGraph) {
		Class<?> programClass = null;		
		List<Command> commands = new ArrayList<Command>();
		Graph graph = new Graph(graphicGraph);

		File directory = new File("../Data/Programs");
		try {
			URLClassLoader classLoader = new URLClassLoader(new URL[] { directory.toURL() });
			programClass = classLoader.loadClass(program);
			classLoader.close();
		} catch (Exception e1) {
			// something went wrong..
			e1.printStackTrace();
		}

		try {
			Object instance = programClass.newInstance();

			Method method = programClass.getDeclaredMethod("run", graph.getClass());
			method.setAccessible(true);
			method.invoke(instance, graph);
			
			Method getCommandsMethod = programClass.getMethod("getCommands");
			getCommandsMethod.setAccessible(true);
			
			commands.addAll(((List<Command>)getCommandsMethod.invoke(instance)));
		} catch (Exception e1) {
			// something went wrong..
			e1.printStackTrace();
		}
		commands.addAll(graph.getCommands());
		
		Collections.sort(commands, Comparator.comparing(Command::getCommandOrder));
		
		return commands;
	}
}
