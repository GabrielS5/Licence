import graph.Graph;
import tools.Program;

public class Test1 implements Program {
	
	@Override
	public void run(Graph graph) {
		System.out.println(graph.getNodes().size());
	}
}