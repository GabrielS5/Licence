package graph.serialization;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.graphml.writer.GraphWriter;
import de.graphml.writer.model.BaseGraph;
import de.graphml.writer.yed.YedEdge;
import de.graphml.writer.yed.YedKeys;
import de.graphml.writer.yed.YedNode;
import de.graphml.writer.yed.graphics.EdgeLabel;
import de.graphml.writer.yed.graphics.PolyLineEdge;
import de.graphml.writer.yed.graphics.ShapeNode;
import graph.Edge;
import graph.Graph;
import graph.GraphNode;


public class GraphIO {
	
	public void exportGraph(Graph graph, String path) {
		GraphWriter graphWriter = null;
		try {
			graphWriter = new GraphWriter(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		graphWriter.startDocument();
		graphWriter.writeKeys(Arrays.asList(YedKeys.values()));
		graphWriter.startGraph(BaseGraph.DIRECTED);

		for (GraphNode node : graph.getNodes()) {
			YedNode<ShapeNode> yedNode = new YedNode<>(
					ShapeNode.asRectangle(node.getX(), node.getY(), 15, 15, node.valueField.getText()));

			node.id = graphWriter.getNextId();
			yedNode.nodeGraphics.fill.color = node.getColor();
			graphWriter.node(yedNode, node.id);
		}

		for (Edge edge : graph.getEdges()) {
			PolyLineEdge edgeGraphics = new PolyLineEdge();
			edgeGraphics.addLabel(EdgeLabel.centered(edge.valueField.getText()));

			YedEdge<PolyLineEdge> yedEdge = new YedEdge<>(edgeGraphics);
			graphWriter.edge(yedEdge, graphWriter.getNextId(), edge.getSource().id, edge.getDestination().id);
		}

		graphWriter.endGraph(BaseGraph.DIRECTED);
		graphWriter.endDocument();
	}
	
	public void exportGraph2(Graph graph, String path) {
		try {
			 
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // root element
            Element root = document.createElement("graphml");
            root.setAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
            
            document.appendChild(root);
            
            Element xmlGraph = document.createElement("graph");
            xmlGraph.setAttribute("id", "G");
            xmlGraph.setAttribute("edgedefault", "directed");
            
            for (GraphNode node : graph.getNodes()) {
            	Element xmlNode = document.createElement("node");
            	xmlGraph.setAttribute("id", node.id);
            	
            	xmlGraph.appendChild(xmlNode);
    		}
            
            for(Edge edge : graph.getEdges()) {
            	Element xmlEdge = document.createElement("edge");
            	xmlGraph.setAttribute("id", edge.id);
            	xmlGraph.setAttribute("source", edge.getSource().id);
            	xmlGraph.setAttribute("target", edge.getDestination().id);
            	
            	xmlGraph.appendChild(xmlEdge);
            }
            
            root.appendChild(xmlGraph);
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(path));
            transformer.transform(domSource, streamResult);
 
            System.out.println("Done creating XML File");
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
	}
	
	public Graph importGraph(String path) {
		
		try {
	         File inputFile = new File(path);
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         NodeList nList = doc.getElementsByTagName("graph");

	         for (int temp = 0; temp < nList.getLength(); temp++) {
	            Node nNode = nList.item(temp);
	            System.out.println("\nCurrent Element :" + nNode.getNodeName());
	            
	            

	            
	         }
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
		return null;

	}
}
