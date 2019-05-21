package tools;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

import graph.Graph;

public class ProgramRunner {
	public void runProgram(String program, Graph graph) {
		Class<?> programClass = null;
		
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
			method.invoke(instance,graph);
		} catch (Exception e1) {
			// something went wrong..
			e1.printStackTrace();
		}
	}
}
