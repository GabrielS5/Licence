import graph.Graph;
import tools.Program;
import graph.Node;
import java.util.List;

public class CircleLayout extends Program {

  @Override
  public void run(Graph graph) {
    List<Node> nodes = graph.getNodes();
    double radius = 200, centerX = 250, centerY = 250;

    for (int i = 0; i < nodes.size(); i++) {
      double angle = (Math.PI * (float) i * 2.0) / (float) nodes.size();

      nodes.get(i).setX(centerX + radius * Math.cos(angle));


      nodes.get(i).setY(centerY + radius * Math.sin(angle));
    }
  }
}
