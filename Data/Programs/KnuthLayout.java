import graph.Graph;
import graph.Node;
import tools.Program;
import java.util.List;
import java.util.ArrayList;

public class KnuthLayout extends Program {
  private List<Node> visitedNodes = new ArrayList<Node>();
  private int xPosition = 1;

  private void layout(Node node, int depth) {
    visitedNodes.add(node);
    List<Node> neighbours = node.getAllNeighbours();
    neighbours.removeAll(visitedNodes);

    if (neighbours.size() >= 1) {
      layout(neighbours.get(0), depth + 1);
    }

    node.setX(xPosition*40);
    node.setY(depth*80);
    xPosition++;

    if (neighbours.size() == 2) {
      layout(neighbours.get(1), depth + 1);
    }
  }

  @Override
  public void run(Graph graph) {

for(Node node : graph.getNodes()){
	if(node.getAllNeighbours().size() < 3){
	    layout(node, 1);
return;
	}
}
  }
}
