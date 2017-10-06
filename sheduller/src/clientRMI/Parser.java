/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRMI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import root.IParser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author 35-khei
 */
public class Parser extends DefaultHandler implements IParser{
    protected XMLReader xMLReader;
    protected String updatePath;
    protected String startUpExe;
    protected String appName;
    protected String version;
    protected String critical;
    final private String[] ATTRIBUTES_APP={"UPDATEPATH","STARTUPEXE","APPNAME","VERSION","CRITICAL"};

    public ArrayList<String> getDirs() {
        return filePaths;
    }

    public void setStartUpExe(String s) {
        startUpExe = s;
    }

    public void setAppName(String s) {
        appName = s;
    }
    public enum ATTRIBUTES_APP_  {UPDATEPATH,STARTUPEXE,APPNAME,VERSION,CRITICAL, FOR_INIT};
    //final private String[] ATTRIBUTES_FILE={"NAME","PATH"};
    //public enum ATTRIBUTES_FILE_  {NAME,PATH, FOR_INIT};
    protected ArrayList<String> fileNames = new ArrayList<String>();      
    protected ArrayList<String> filePaths = new ArrayList<String>();
    protected Map<String,String> hashFiles = new HashMap();
    protected boolean fnode = false;
    
    public Parser(File file) throws FileNotFoundException{
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setNamespaceAware(true);            
            SAXParser sAXParser = spf.newSAXParser();
            xMLReader = sAXParser.getXMLReader();
            FileInputStream fis = new FileInputStream(file);
            InputSource is = new InputSource(fis);
                 
            //xMLReader.parse(is);
            sAXParser.parse(is, this);
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            System.err.println("У Вас нет доступа к папке");
        } catch (IOException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    public Map getHashFiles() {
        return hashFiles;
    }
    public String getUpdatePath() {
		return updatePath;
    }

    public String getStartUpExe() {
    	return startUpExe;
    }

    public String getAppName() {
        return appName;
    }

    public String getVersion() {
        return version;
    }

    public String getCritical() {
        return critical;
    }

    public ArrayList<String> getFiles() throws Exception {
    	if (fileNames.size()!=filePaths.size())
    		throw new Exception("fileNames.size()!=filePaths.size()");
    	ArrayList<String> files = new ArrayList<String>();
    	for (int i=0;i<fileNames.size();i++){
                String fileName = fileNames.get(i);
                StringBuffer filePath = new StringBuffer(filePaths.get(i));
                filePath.append("/"+fileName);
                String s = filePath.toString();
                //String s = toISO_8859_1(filePath.toString());                
                files.add(removeDoubleSlash(s));
        }
        return files;
    }

    public ArrayList<String> getFiles(String prefix) throws Exception {
    	ArrayList<String> files_ = getFiles();
    	for (Iterator iterator = files_.iterator(); iterator.hasNext();) {
			String file = (String) iterator.next();
			files_.add(prefix+file);			
		}
    	return files_;
    }
    // get attributes
	public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
		//System.out.println("start element:"+qName);
		if (qName.equalsIgnoreCase("appupdater")){
			ATTRIBUTES_APP_[] attrs = ATTRIBUTES_APP_.values();
			ATTRIBUTES_APP_ attr = ATTRIBUTES_APP_.FOR_INIT;
			for (int i=0;i<attributes.getLength();i++){
				String aName = attributes.getLocalName(i);
				for (int j=0;j<ATTRIBUTES_APP.length;j++) 
					if (aName.equalsIgnoreCase(ATTRIBUTES_APP[j]))
						attr = attrs[j];
				switch (attr){
					case UPDATEPATH:{updatePath = attributes.getValue(i);break;}
					case STARTUPEXE:{startUpExe = attributes.getValue(i);break;}
					case APPNAME:	{appName = attributes.getValue(i);break;}
					case VERSION:	{version = attributes.getValue(i);break;}
					case CRITICAL: 	{critical = attributes.getValue(i);break;}
				default: {System.out.println("nothing attribute is found ");}
				}
			}
		}
		if (qName.equalsIgnoreCase("file")){
//			ATTRIBUTES_FILE_[] attrs = ATTRIBUTES_FILE_.values();
//			ATTRIBUTES_FILE_ attr = ATTRIBUTES_FILE_.FOR_INIT;
			for (int i=0;i<attributes.getLength();i++){
				String aName = attributes.getLocalName(i);
				if (aName.equalsIgnoreCase("name"))
					fileNames.add(attributes.getValue(i));
				else if(aName.equalsIgnoreCase("path"))
					filePaths.add(attributes.getValue(i));
				else
					System.out.println("such attribute name "+aName+ " is not allowed");
			}
			fnode = true;
		}
	}
	public void characters(char ch[], int start, int length) throws SAXException {
		if (fnode){
                        String s = new String (ch,start,length);
                        int last = fileNames.size()-1;
                        hashFiles.put(s,removeDoubleSlash(filePaths.get(last)+"/"+fileNames.get(last)));
                        fnode = false;
                        //hashFile.add(new String (ch,start,length));
                        //System.out.println("file value:"+new String (ch,start,length));
                }
	}

	public void endElement(String uri, String localName, 
			String qName) throws SAXException {
		//System.out.println("end element:"+qName);
	}

	public ArrayList<String> getFileNames() {
		return fileNames;
	}
        public static String removeDoubleSlash(String s){
        // use sb.toString().lastindexOf("\\\\"); for ignore first double slashes
         StringBuffer sb = new StringBuffer(s);
         int b = sb.toString().indexOf("//");
                while(b!=-1){
                    if(b!=0)
                            sb.delete(b, b+1);
                    else
                            break;
                    b=sb.toString().indexOf("//");
        }
        return sb.toString();
    }
}
