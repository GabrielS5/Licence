import graph.Graph;
import graph.Node;
import tools.Program;
import javafx.scene.paint.Color;

public class WhiteColoring extends Program {

  @Override
  public void run(Graph graph) {
    for (Node node : graph.getNodes()) {
      node.setColor(Color.WHITE);
    }
  }
}
