package root;

import java.util.ArrayList;
import java.util.Map;

public interface IParser {
	public String  getUpdatePath();
        public String  getStartUpExe();
        public String  getAppName();
        public String getVersion();
        public String getCritical();
        public ArrayList<String> getFileNames() throws Exception;
        public ArrayList<String> getFiles() throws Exception;
        public ArrayList<String> getFiles(String prefix) throws Exception;
        public ArrayList<String> getDirs();
        public Map getHashFiles();
        //TODO remove it after Delphi will be standalone app 
        void setStartUpExe(String s);
        void setAppName(String s);
}
