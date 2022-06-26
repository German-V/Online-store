package by.issoft.store.XML;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

public class XMLParser {

    public static void parse() throws ParserConfigurationException, IOException, SAXException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File("store/config.xml"));
        NodeList sortList = document.getElementsByTagName("sort");
        NodeList sort = sortList.item(0).getChildNodes();
        for (int i = 0; i < sort.getLength(); i++) {
            Node node = sort.item(i);
            if(!node.getNodeName().trim().equals("#text"))
                Sort.getSortMap().put(node.getNodeName(), SortType.valueOf(node.getTextContent()));
        }
    }
}