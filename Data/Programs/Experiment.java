import graph.Graph;
import tools.Program;

public class Experiment extends Program {

  @Override
  public void run(Graph graph) {
    for (int i = 0; i < 100000; i++) {
      print(i);
    }
  }
}
