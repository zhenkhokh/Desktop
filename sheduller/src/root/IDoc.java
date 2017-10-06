package root;

import java.io.File;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public interface IDoc {
	void setOutPutFile(File file);
	void setApp(String name);
	void setExe(String name);
	void setUpdateDir(String upDir);
	void setVersion(String v);
	void setCritical(String v);
	void appendFileNode(Node e);	
	Element getFileNode(String name, String path, String hash);
}
