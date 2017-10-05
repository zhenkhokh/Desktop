/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRMI;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import root.IFilter;
import root.IParser;

/**
 *
 * @author 35-khei
 */
public class Filter extends Parser implements IFilter,IParser {

    public Filter(File file) throws FileNotFoundException {
        super(file);
    }
    public void startElement(String uri, String localName,String qName, 
            Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
    }
    
    public void characters(char ch[], int start, int length) throws SAXException {                
        super.characters(ch, start, length);
    }
    public void setExtension(String s) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<String> filter(Map hashMap) {
        //Collection<String> hashes = hashMap.values();
        Set hashes = hashMap.keySet();
        ArrayList<String> filesToUpdate = new ArrayList<String>();

        for (Iterator<String> iterator = hashes.iterator(); iterator.hasNext();) {
                String next = iterator.next();
                if (!super.hashFiles.containsValue(hashMap.get(next)))
                    filesToUpdate.add((String) hashMap.get(next));
                else
                    if (!super.hashFiles.containsKey(next))
                        filesToUpdate.add((String) hashMap.get(next));
            }
        return filesToUpdate;
    }

    public void setStartUpExe(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setAppName(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
