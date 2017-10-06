import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import root.IDoc;


public class Reciever implements IDoc{
	DocumentBuilderFactory builderFactory =
	        DocumentBuilderFactory.newInstance();
	DocumentBuilder builder = null;
	Document doc;
	DOMSource domSource;
	Transformer transformer;
	StreamResult result;
        Element root;
	public Reciever(){
		try{
                    builder = builderFactory.newDocumentBuilder();
		}catch(ParserConfigurationException e){
		    e.printStackTrace();
		}
		doc = (Document) builder.newDocument();
		Element file = doc.createElement("File");
		root = doc.createElement("AppUpdater");
		domSource = new DOMSource(root);		
                try{
                    transformer = TransformerFactory.newInstance().newTransformer();
		}catch (TransformerConfigurationException e) {
                    e.printStackTrace();
		}
	}
	public static void main(String args[]){
	}

    @Override
    public void setOutPutFile(File file) {
        result = new StreamResult(file);
    }

    @Override
    public void setApp(String name) {
        root.setAttribute("AppName", name);
    }

    @Override
    public void setExe(String name) {
        root.setAttribute("StartUpExe", name);
    }

    @Override
    public void setUpdateDir(String path) {
        root.setAttribute("UpdatePath", path);
    }

    @Override
    public void setVersion(String v) {
        root.setAttribute("Version", v);
    }

    @Override
    public void setCritical(String v) {
        root.setAttribute("Critical", v);
    }

    @Override
    public void appendFileNode(Node e) {
        root.appendChild(e);
        try{
            transformer.transform(domSource, result);
	}catch (TransformerException ex){
            ex.printStackTrace();
	}
    }

    @Override
    public Element getFileNode(String name,String path, String hash) {
        Element e = doc.createElement("File");
        e.setAttribute("Name", name);
        e.setAttribute("Path", path);
        e.setTextContent(hash);
        return e;
    }
}
