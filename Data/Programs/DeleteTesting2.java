import graph.Graph;
import graph.Edge;
import tools.Program;
import java.util.List;

public class DeleteTesting2 extends Program {

  @Override
  public void run(Graph graph) {
	List<Edge> edges = graph.getEdges();
	
    for (int i = edges.size() - 1;i>=0;i--) {
      graph.removeEdge(edges.get(i));
    }
  }
}
