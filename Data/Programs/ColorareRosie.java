import graph.Graph;
import javafx.scene.paint.Color;
import graph.GraphNode;
import tools.Program;

public class ColorareRosie implements Program {

  @Override
  public void run(Graph graph) {
    for (GraphNode node : graph.getNodes()) {
      node.setColor(Color.RED);
    }
  }
}
