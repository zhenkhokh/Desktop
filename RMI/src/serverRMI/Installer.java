package serverRMI;

import clientRMI.CpFiles;
import clientRMI.ErrorMsg;
import clientRMI.Filter;
import clientRMI.Parser;
import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;

import root.IFilter;
import root.IGenerator;
import root.IHandler;
import root.IInstaller;
import root.IParser;
//TODO uncomment FileNotFoundExcerption for 1.7
public class Installer  implements IInstaller {
    private static String exeName;
    private static String params;
    private IParser parser;
    private IFilter filter;
    private Integer curVersion;
    //private final String updaterName = "UpdateApp.vshost.exe";
    
    @Override
	public ArrayList<String> getFiles() throws Exception{
		return parser.getFiles();
	}

    @Override
	public ArrayList<String> getFilteredFiles() throws Exception{
		return filter.filter(parser.getHashFiles());
	}
        String getFileName(){
            String bin = getExeName();
            if (bin.indexOf(IGenerator.ALFAHRMKEY)==-1
                    &&bin.indexOf(IGenerator.ALFASALARYKEY)==-1
                    &&bin.indexOf(IGenerator.ALFAADMINKEY)==-1)
                return getExeName().replace(".exe", "").replace(".EXE", "")+".xml";
            else if (bin.indexOf(IGenerator.ALFAHRMKEY)!=-1)
                return IGenerator.ALFAHRM+".xml";
            else if (bin.indexOf(IGenerator.ALFASALARYKEY)!=-1)
                return IGenerator.ALFASALARY+".xml";
            else if (bin.indexOf(IGenerator.ALFAADMINKEY)!=-1)
                return IGenerator.ALFAADMIN;
            return null;
        }
    @SuppressWarnings("finally")
    protected boolean setParser() {
        if (!downloadXML())
            return false;
        parser=null;
        System.out.println("try to set parser");       
        	try{
                    File file = new File(IHandler.xmlPathDownload+getFileName());
                    parser = (IParser)new Parser(file);
                    //parser = (IParser)new Parser(getExeName());
        	}catch (Exception e){
                    String msg = IHandler.xmlPathDownload+getFileName()+" is currupt or doesnt exist";
                    ErrorMsg errorMsg = new ErrorMsg(msg);
                    System.out.println(msg);
        	}finally {
        		if (parser==null)
                            return false;
                        return true;
        	}
    }
        @SuppressWarnings("finally")
	protected boolean setFilter(){
            if (setParser()){
                filter = null;
                System.out.println("set filter");
                    try{
                        if(parser.getAppName()!=null){                            
                            File file = new File(getOldXml());
                            if (file.isFile())
                                filter = (Filter)new Filter(file);
                        }
                    }catch (Exception e){
                        String msg = getFileName()+" doesnt exist or may be corrupted at app";
                        System.out.println(msg);
                        ErrorMsg errorMsg = new ErrorMsg(msg);
                        return false;
                    }finally {
                            if (filter==null)
                                return false;
                            else
                                return true;
                    }
            }
        return false;
        }
    private boolean checkingXML(String xmlPath,String msg){
        File src  = new File(xmlPath);
        if (!src.exists()&&!src.isFile()){
            ErrorMsg errorMsg = new ErrorMsg(msg);
            return false;
        }
        return true;
    }
    private String getOldXml(){
        //return IHandler.localPathRoot+parser.getAppName()+"\\"+getFileName();
        return IHandler.xmlPathDownload+getFileName().replace(".xml","")+"_old.xml";
    }
    private String getXml(){
        return IHandler.xmlPathDownload+getFileName();
    }
    private boolean downloadXML(){
        CpFiles cpFiles = CpFiles.getInstance();
        String xmlPath = IHandler.xmlStorage+getFileName();
        System.out.println("download xml:"+xmlPath);
        if (!checkingXML(xmlPath,"Приложение не зарегистрированно"))
            return false;
        //TODO uncomment for 1.7
        //try {            
            cpFiles.copySimple(xmlPath, getXml());
        //} catch (FileNotFoundException ex) {
        //    System.out.println("xml did not found at "+xmlPath);
        //    return false;
        //}        
        return true;
    }
    private void refreshXML(){
        CpFiles cpFiles = CpFiles.getInstance();
        String xmlPath = getXml();
        if (!checkingXML(xmlPath,"Неудачная попытка обновления"))
            return ;
        System.out.println("download xml:"+xmlPath);
        cpFiles.copySimple(xmlPath, getOldXml());
    }
    @Override
    public void setExeName(String name){
        exeName = name;
    }
    @Override
    public String getExeName() {
        return exeName;
    }
    public void setCurVersion(Integer curVersion_){
        curVersion = new Integer(parser.getVersion());
    }
    @Override
    public Integer getCurVersion(){
        return curVersion!=null?curVersion:(new Integer(parser.getVersion()));
    }
    public void setFilter(IFilter filter) {
        this.filter = filter;
    }    
    @Override
    public IFilter getFilter() {
        return filter;
    }
    @Override
    public boolean isInstalled(){
        return setFilter();
        //return checkingXML(getOldXml(),"");        
    }    
    @Override
    public boolean update(){
                if (setParser()){
                    System.out.println("upgrating to "+getExeName()+" "+parser.getVersion());
                    if (setFilter()){
                        if (parser.getVersion()!=null&& filter.getVersion()!=null &&
                                parser.getCritical()!=null&& filter.getCritical()!=null){
                            if ( new Integer(filter.getCritical()) < new Integer(parser.getCritical())||
                                    new Integer(filter.getVersion()) < new Integer(parser.getVersion())){
                                ArrayList<String> files=null;
                                try{
                                    if (parser.getHashFiles()!=null)
                                        files = filter.filter(parser.getHashFiles());
                                }catch(Exception e){
                                    return false;
                                } finally{ if (files==null) return false; }
                                if(!makeFiles(files))
                                    return false;
                                return true;
                            } else{
                                System.out.println("no upgating is requred. Current version is "+getCurVersion());
                                return true;
                            }
                        } else
                            new ErrorMsg("xml must have version field ");
                    } else
                        new ErrorMsg("no filter is set"); //install
                }else
                    new ErrorMsg("parser must be set");

                return false;
    }
    public void testParser(){
        try {
            //Parser parser = new Parser(IHandler.xmlPathDownload);
        	Parser parser = new Parser(new File(getFileName()));
            //makeDirSkeleton();
            System.out.println("hello "+parser.getVersion());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public boolean install(){
                System.out.println("App is installing to "+IHandler.localPathRoot);
                mkdir(new StringBuffer(IHandler.localPathRoot));
                mkdir(new StringBuffer(IHandler.xmlPathDownload));
                if (downloadXML()){
                    if (setParser()){
                        ArrayList<String> files=null;
                        try{
                            files = parser.getFiles();
                        }catch(Exception e){
                            System.out.println("App has "+e.getMessage());
                        } finally{ if (files==null) return false; }
                        if(!makeFiles(files))
                            return false;
                        return true;
                    }else
                        new ErrorMsg("parser must be set");
                }
                return false;
        }
    
    protected boolean makeFiles(ArrayList<String> files){
                                makeDirSkeleton();
                                String appPath = getAppPath();
                                if (appPath==null) return false;
                                CpFiles cpFiles = CpFiles.getInstance();
                                //TODO uncomment for 1.7
                                //try {
                                    cpFiles.copy(parser.getUpdatePath()+"/",files, appPath);
                                // need for filter if installed, and it breaking uncontinue updating
                                refreshXML();
                               // }catch (FileNotFoundException e){
                               //     System.out.println("App has "+cpFiles.getCnt()+" files unreachable");
                               //     return false;
                               //}
                                return true;
    }
    protected String getAppPath(){        
                                String appPath = null;
                                if (parser.getAppName()!=null){
                                    appPath = IHandler.localPathRoot+parser.getAppName()+"\\";
                                }else{
                                    new ErrorMsg("xml must have appname fields");
                                    return null;
                                }                                
                                String out = removeDoubleSlash(appPath);
                                System.out.println("appPath is "+out);
                                return out;
    }
    protected void mkdir(StringBuffer sb){
        File dir = new File(sb.toString());
        dir.mkdir();
    }
    //use io
    protected void makeDirSkeleton(){
        System.out.println("makeDirSkeleton");
        ArrayList<String> dirs = parser.getDirs();
        StringBuffer appPath = new StringBuffer(new StringBuffer(getAppPath()));
        mkdir(appPath);
        mkdir(new StringBuffer(IHandler.xmlPathDownload));
        for (Iterator iterator = dirs.iterator(); iterator.hasNext();) {
                String next = (String) iterator.next();
                if (!next.isEmpty()){
                    next = CpFiles.fromISO_8859_1_And_SlashConv(next);
                    File dir = new File(removeDoubleSlash(appPath.append(next).toString()));
                    dir.mkdirs();
                    appPath.delete(appPath.length()-next.length(), appPath.length());
                }
            }
    }
    @Override
    public IParser getParser() {
        return parser;
    }
    @Override
    public void execute() {
        throw new UnsupportedOperationException("Not supported"); 
    }
    @Override
    public void setParams(String s) {
        params = s;
    }
    @Override
    public String getParams() {
        return params;
    }
    /*
    * do not remove first double slashes
    */ 
    public static String removeDoubleSlash(String s){
        StringBuilder sb = new StringBuilder(s);
      	int b = sb.toString().lastIndexOf("\\\\");
        while(b!=-1){
        	if(b!=0)
        		sb.delete(b, b+1);
        	else
        		break;
        	b=sb.toString().lastIndexOf("\\\\");
        }         
        return sb.toString().replace("/", "");
    }
}