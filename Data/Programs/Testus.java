import graph.Graph;
import graph.Node;
import tools.Program;

public class Testus extends Program {
	
	@Override
	public void run(Graph graph) {
		for(Node node : graph.getNodes()){
			node.getAllNeighbours();
			node.getIncomingNeighbours();
			node.getOutgoingNeighbours();
		}
	}
}