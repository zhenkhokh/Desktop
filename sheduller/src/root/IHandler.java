package root;

import java.util.ArrayList;


public interface IHandler {
        final String localPathRoot="C:\\APP\\";
        final String xmlPathDownload="C:\\APP\\TMP\\";
        final String xmlStorage = "\\\\FS2\\Sheduler\\updaterTest\\"; // do not try use ftp
        final String serverName="glassfish.vniia.net";
        
        public void execute();        
        public void setExeName(String s);
        public String getExeName();
        public void setParams(String s);
        public String getParams();
        
        ArrayList<String> getFiles() throws Exception;
        ArrayList<String> getFilteredFiles() throws Exception;
}
