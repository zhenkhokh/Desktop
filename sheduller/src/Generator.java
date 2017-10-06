
import clientRMI.CpFiles;
import clientRMI.Parser;
import com.vniia.XmlGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.w3c.dom.Element;
import root.IDoc;
import root.IGenerator;
import root.IHandler;
import sun.misc.BASE64Encoder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 35-khei
 */
public class Generator implements IGenerator{
    IDoc reciever;
    String root;
    int recursionNum=0;    
    //FTPClient ftp = CpFiles.getInstance().ftp;
    
    public Generator(int recursionNum){
        this.recursionNum = recursionNum; 
    }
    FTPClient getFTPClient(){
            FTPClient ftp =new FTPClient();   
            System.out.println("got FTPClient");
            try {
                ftp.connect(IHandler.serverName);                
                System.out.println("set connection");
                ftp.login("anonymous", "anonymous@"+IHandler.serverName);
                System.out.println("logining is done");
                /*
                if (!FTPReply.isPositiveCompletion(ftp.getReply())){
                    ftp.disconnect();
                    System.err.println("Отказано в соединении");
                }
                */
            } catch (IOException ex) {
                System.err.println("Невозможно получить соединение");
            }
            return ftp;
    }
    void disconnect(FTPClient ftp){
                    try {
            ftp.logout();
                if (ftp.isConnected())
                    ftp.disconnect();                
            } catch (IOException ex) {
                        System.err.println("cant disconnect");
            }
    }
    @Override
    public void exe(File file, String... cmd) {
        try {
            String dir,bin,name;
            if (cmd.length>=4){
                dir = cmd[1];
                bin = cmd[2];
                name = cmd[3];
            }else
                return ;
            root = dir;
            reciever = new Reciever();
            reciever.setOutPutFile(file);
            reciever.setApp(name);
            reciever.setCritical(IGenerator.CRITICAL);
            reciever.setExe(bin);
            reciever.setUpdateDir(root);
            setVersion(file);
            //reciever.setVersion(IGenerator.VERSION);
            doProcess(root,recursionNum);
            System.out.println("root is "+root);
            //printFiles(root,recursionNum);
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName()).log(Level.SEVERE, null, ex);
        }

     }
        void doProcess(String path,int recursionNum){
        try {
            FTPClient ftp = getFTPClient();
            if(!ftp.changeWorkingDirectory(path))
                System.err.println("cant change path: "+path);
            FTPFile[] files = ftp.listFiles();
            String dir = ftp.printWorkingDirectory();
            
            for (int i = 0; i < files.length; i++) {
                FTPFile file = files[i];
                Integer rec = recursionNum;
                String fileName = file.getName();
                String fp=null;
                if (!dir.contains("techcard"))
                    fp = pathConvSrv(dir+"\\"+fileName,"fileserver");
                if (dir.contains("techcard"))
                    fp = pathConvSrv(dir+"\\"+fileName,"PDM\\IM_TechCard");
                    
                if (file.isDirectory()){
                    if ((--rec)>=0)
                        doProcess(Parser.removeDoubleSlash(dir+"/"+fileName)
                                ,rec);
                }else{
                    Element e = reciever.getFileNode(fileName
                            ,cutRoot(path)
                            ,String.valueOf(new File(fp).lastModified()) //work faster. Cant use hash from server
                            //,com.vniia.XmlGenerator.getHashString(file)
                    );
                    reciever.appendFileNode(e);
                    /*if (new File(fp).lastModified()==0){
                        System.out.println("fp:"+fp);
                        System.out.println("dir:"+dir+"/"+fileName);
                    }*/
                }
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
            //}
        //else
        //    System.err.println(path+" is invalid, see java.io.File");
    }
        //TODO use StingBuffer
        String cutRoot(String str){
            root = Parser.removeDoubleSlash(root+"/"); 
            if(str.lastIndexOf(str)<=root.length())
                return str.replace(root, "");
            return null;
        }
        void printFiles(String path,int recursionNum){  
            System.out.println("start printFiles "+recursionNum);
        try{            
        FTPClient ftp = getFTPClient();
        System.out.println("ftp is got");
            System.out.println("path is "+path);
        if(!ftp.changeWorkingDirectory(toISO_8859_1(path)))
            System.err.println("cant change path: "+toISO_8859_1(path));
        System.out.println("change dir is done");
        FTPFile[] files = ftp.listFiles();
        System.out.println("got files");
        String dir = ftp.printWorkingDirectory();
            disconnect(ftp);
            System.out.println("disconnect is done");
	for (int i = 0; i < files.length; i++) {
            System.out.println("start cycle");
                FTPFile file = files[i];
                Integer rec = recursionNum;
                String fileName = file.getName();
                String fp=null;
                if (!dir.contains("techcard"))
                    fp = pathConvSrv(dir+"\\"+fileName,"fileserver");
                else
                    fp = pathConvSrv(dir+"\\"+fileName,"PDM\\IM_TechCard");
            if (file.isDirectory()){
                if ((--rec)>=0)
                    printFiles(Parser.removeDoubleSlash(dir+"/"+fileName)
                            ,rec);
            }else{
                System.out.println("fp:"+fp);
                System.out.println(Parser.removeDoubleSlash(dir+"/"+fileName));
            }            
	}
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            ex.printStackTrace();
        }
    }
        private void setVersion(File xml) throws FileNotFoundException, IOException{
            //reciever.setVersion(String.valueOf(XmlGenerator.getVersion(xml)));
            Parser parser=null;
            if (xml.exists()&&xml.isFile()&&xml.canWrite()){
                try{
                    parser = new Parser(xml);
                    String v = parser.getVersion();
                    Integer version = null;
                    if (isNumber(v)){
                        version = new Integer(v);
                        reciever.setVersion((++version).toString());
                        return ;
                    }
                }catch (Exception ex){
                    reciever.setVersion(IGenerator.VERSION);
                    System.err.println("cant initlize Parser for "+xml.getName());
                    return;
                }
            }
            else 
                throw new FileNotFoundException();
            
        }
        //TODO 
        boolean isNumber(String s){
            return true;
        }
        public static String pathConvSrv(String ftpPath,String srv){
            byte[] iso_8859_1;
        try {
            iso_8859_1 = new String(ftpPath.getBytes(),"UTF-8").getBytes("ISO-8859-1"); //WINDOWS-1251 or 1252 is not accepted
            String fname_iso_8859_1 = new String(iso_8859_1,"UTF-8");
            StringBuilder sb = new StringBuilder("\\\\"+srv);
            sb.append(fname_iso_8859_1.replace("/", "\\"));
            return Task.removeDoubleSlashes(sb.toString());
        } catch (UnsupportedEncodingException ex) {
            System.err.println("UnsupportedEncodingException");
        }
        return null;
    }
        static String toISO_8859_1(String s){
            byte[] iso_8859_1;
            String o=null;
            try{
            iso_8859_1 = new String(s.getBytes(),"UTF-8").getBytes("ISO-8859-1"); //WINDOWS-1251 or 1252 is not accepted
            o = new String(iso_8859_1,"UTF-8");
            } catch (UnsupportedEncodingException ex) {
            System.err.println("UnsupportedEncodingException");
            }
            return  o;
        }        
}
