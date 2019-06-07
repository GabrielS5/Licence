import graph.Graph;
import tools.Program;

public class Program extends Program {
	
	@Override
	public void run(Graph graph) {
		graph.createEdge(graph.getNodes().get(0), graph.getNodes().get(1));
	}
}