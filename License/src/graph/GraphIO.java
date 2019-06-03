package graph;

import java.io.File;

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

import graph.graphic.GraphicEdge;
import graph.graphic.GraphicGraph;
import graph.graphic.GraphicNode;
import javafx.scene.paint.Color;

public class GraphIO {

	public void exportGraph(GraphicGraph graph, String path) {
		try {

			DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			Document document = documentBuilder.newDocument();

			Element root = document.createElement("graphml");
			root.setAttribute("xmlns", "http://graphml.graphdrawing.org/xmlns");
			setupGraphMLDocument(document, root);
			document.appendChild(root);

			Element xmlGraph = document.createElement("graph");
			xmlGraph.setAttribute("id", "G");
			xmlGraph.setAttribute("edgedefault", "directed");

			for (GraphicNode node : graph.getNodes()) {
				Element xmlNode = getXmlNode(document, node);
				xmlGraph.appendChild(xmlNode);
			}

			for (GraphicEdge edge : graph.getEdges()) {
				Element xmlEdge = getXmlEdge(document, edge);
				xmlGraph.appendChild(xmlEdge);
			}

			root.appendChild(xmlGraph);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource domSource = new DOMSource(document);
			StreamResult streamResult = new StreamResult(new File(path));
			transformer.transform(domSource, streamResult);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	public GraphicGraph importGraph(String path, GraphicGraph graph) {

		try {
			File inputFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.parse(inputFile);
			document.getDocumentElement().normalize();
			NodeList nodes = document.getElementsByTagName("node");

			for (int i = 0; i < nodes.getLength(); i++) {
				GraphicNode graphNode = parseXmlNode(nodes.item(i));
				graph.addNode(graphNode);
			}

			NodeList edges = document.getElementsByTagName("edge");

			for (int i = 0; i < edges.getLength(); i++) {
				GraphicEdge edge = parseXmlEdge(edges.item(i), graph);
				graph.addEdge(edge);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return graph;

	}

	private void setupGraphMLDocument(Document document, Element root) {
		Element xmlNodeValueKey = document.createElement("key");
		xmlNodeValueKey.setAttribute("id", "d0");
		xmlNodeValueKey.setAttribute("for", "node");
		xmlNodeValueKey.setAttribute("attr.name", "NodeValue");

		Element xmlNodeX = document.createElement("key");
		xmlNodeX.setAttribute("id", "d1");
		xmlNodeX.setAttribute("for", "node");
		xmlNodeX.setAttribute("attr.name", "NodeX");

		Element xmlNodeY = document.createElement("key");
		xmlNodeY.setAttribute("id", "d2");
		xmlNodeY.setAttribute("for", "node");
		xmlNodeY.setAttribute("attr.name", "NodeY");

		Element xmlNodeColor = document.createElement("key");
		xmlNodeColor.setAttribute("id", "d3");
		xmlNodeColor.setAttribute("for", "node");
		xmlNodeColor.setAttribute("attr.name", "NodeColor");

		Element xmlEdgeValue = document.createElement("key");
		xmlEdgeValue.setAttribute("id", "d4");
		xmlEdgeValue.setAttribute("for", "edge");
		xmlEdgeValue.setAttribute("attr.name", "EdgeValue");

		root.appendChild(xmlNodeValueKey);
		root.appendChild(xmlNodeX);
		root.appendChild(xmlNodeY);
		root.appendChild(xmlNodeColor);
		root.appendChild(xmlEdgeValue);
	}

	private Element getXmlNode(Document document, GraphicNode node) {
		Element xmlNode = document.createElement("node");
		xmlNode.setAttribute("id", Integer.toString(node.getUniqueId()));

		Element nodeValue = document.createElement("data");
		nodeValue.setAttribute("key", "d0");
		nodeValue.setTextContent(node.valueField.getText());

		Element nodeX = document.createElement("data");
		nodeX.setAttribute("key", "d1");
		nodeX.setTextContent(Double.toString(node.getX()));

		Element nodeY = document.createElement("data");
		nodeY.setAttribute("key", "d2");
		nodeY.setTextContent(Double.toString(node.getY()));

		Element nodeColor = document.createElement("data");
		nodeColor.setAttribute("key", "d3");

		Element redElement = document.createElement("red");
		redElement.setTextContent(String.valueOf(node.getColor().getRed()));
		nodeColor.appendChild(redElement);

		Element greenElement = document.createElement("green");
		greenElement.setTextContent(String.valueOf(node.getColor().getGreen()));
		nodeColor.appendChild(greenElement);

		Element blueElement = document.createElement("blue");
		blueElement.setTextContent(String.valueOf(node.getColor().getBlue()));
		nodeColor.appendChild(blueElement);

		xmlNode.appendChild(nodeValue);
		xmlNode.appendChild(nodeX);
		xmlNode.appendChild(nodeY);
		xmlNode.appendChild(nodeColor);

		return xmlNode;
	}

	private Element getXmlEdge(Document document, GraphicEdge edge) {
		Element xmlEdge = document.createElement("edge");
		xmlEdge.setAttribute("id", Integer.toString(edge.getUniqueId()));
		xmlEdge.setAttribute("source", Integer.toString(edge.getSource().getUniqueId()));
		xmlEdge.setAttribute("target", Integer.toString(edge.getDestination().getUniqueId()));
		xmlEdge.setAttribute("doubleEdged", Boolean.toString(edge.isDoubleEdged()));

		Element edgeValue = document.createElement("data");
		edgeValue.setAttribute("key", "d4");
		edgeValue.setTextContent(edge.valueField.getText());

		xmlEdge.appendChild(edgeValue);

		return xmlEdge;
	}

	private GraphicNode parseXmlNode(Node node) {
		GraphicNode graphNode = null;
		int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
		float x = 0, y = 0;
		String valueField = null;
		double red = 1, green = 1, blue = 1;

		NodeList childrens = node.getChildNodes();

		for (int i = 0; i < childrens.getLength(); i++) {
			switch (childrens.item(i).getAttributes().getNamedItem("key").getNodeValue()) {
			case "d0":
				valueField = childrens.item(i).getTextContent();
				break;
			case "d1":
				x = Float.parseFloat(childrens.item(i).getTextContent());
				break;
			case "d2":
				y = Float.parseFloat(childrens.item(i).getTextContent());
				break;
			case "d3":
				NodeList colors = childrens.item(i).getChildNodes();
				red = Double.parseDouble(colors.item(0).getTextContent());
				green = Double.parseDouble(colors.item(1).getTextContent());
				blue = Double.parseDouble(colors.item(2).getTextContent());
				break;
			default:
				break;
			}

			graphNode = new GraphicNode(x, y, valueField);
			graphNode.setColor(new Color(red, green, blue, 1));
			graphNode.setUniqueId(id);
		}
		return graphNode;
	}

	private GraphicEdge parseXmlEdge(Node node, GraphicGraph graph) {
		GraphicEdge edge = null;
		int id = Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue());
		int sourceId = Integer.parseInt(node.getAttributes().getNamedItem("source").getNodeValue());
		int targetId = Integer.parseInt(node.getAttributes().getNamedItem("target").getNodeValue());
		boolean doubleEdged = Boolean.parseBoolean(node.getAttributes().getNamedItem("doubleEdged").getNodeValue());

		String valueField = null;

		NodeList childrens = node.getChildNodes();

		for (int i = 0; i < childrens.getLength(); i++) {
			switch (childrens.item(i).getAttributes().getNamedItem("key").getNodeValue()) {
			case "d4":
				valueField = childrens.item(i).getTextContent();
				break;
			default:
				break;
			}

			GraphicNode source = getGraphNodeById(graph, sourceId);
			GraphicNode destination = getGraphNodeById(graph, targetId);

			edge = new GraphicEdge(graph, source, destination, valueField);

			if (doubleEdged)
				edge.changeToDoubleEdge();

			edge.setUniqueId(id);
		}
		return edge;
	}

	private GraphicNode getGraphNodeById(GraphicGraph graph, int id) {
		for (GraphicNode node : graph.getNodes()) {
			if (node.getUniqueId() == id)
				return node;
		}
		return null;
	}

}
