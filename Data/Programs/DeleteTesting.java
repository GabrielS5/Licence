import graph.Graph;
import graph.Node;
import tools.Program;
import java.util.List;

public class DeleteTesting extends Program {

  @Override
  public void run(Graph graph) {
	List<Node> nodes = graph.getNodes();
	
    for (int i = nodes.size() - 1;i>=0;i--) {
      graph.removeNode(nodes.get(i));
    }
  }
}
