import graph.Graph;
import graph.Edge;
import tools.Program;

public class TestuAla extends Program {

  @Override
  public void run(Graph graph) {
    Edge edge = graph.createEdge(graph.getNodes().get(0), graph.getNodes().get(1));
    graph.removeEdge(edge);
  }
}
