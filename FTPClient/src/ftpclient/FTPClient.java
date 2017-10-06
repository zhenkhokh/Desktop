/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ftpclient;

import com.google.common.io.ByteSink;
import com.google.common.io.Files;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPCmd;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author 35-khei
 */
public class FTPClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("name1.txt"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        //String fname = (String) p.get("1"); //updater do not get ascii format
        String fname = "Ð£Ð¿ÑÐ°Ð²Ð»ÐµÐ½Ð¸Ðµ Ð¿ÑÐ¾Ð¸Ð·Ð²Ð¾Ð´ÑÑÐ²Ð¾Ð¼_Plan_DSE.xml";
        System.out.println(pathConv(fname+"_Управление производством_Plan_DSE.xml -<- do not mix"));
        fname = getUcode(fname);
        System.out.println(fname);
        fname = decode(fname);
        System.out.println(fname);

        String fname_iso_8859_1 = null;
        try {
            //"ÐÐ Ð ÐÐ°Ð³ÑÑÐ·ÐºÐ¸ ÐÐ¢Ð_ARM_PRJ.xml";//"\u0420\u0430\u0441\u0446\u0435\u0445\u043e\u0432\u043a\u0430_armrasch.xml";
            byte[] iso_8859_1 = fname.getBytes("ISO-8859-1");//WINDOWS-1251 or 1252 is not accepted
            fname_iso_8859_1 = new String(iso_8859_1,"UTF-8");
            System.out.println("iso_8859_1 is "+fname_iso_8859_1);
            if (fname_iso_8859_1.equalsIgnoreCase("Управление производством_Plan_DSE.xml"))
                System.out.println("fname is resolved");
            else
                System.out.println("fname cant be resolved");
            //fname = new String(iso_8859_1,"UTF-8"); // stream closed 
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
                
//"Расцеховка_armrasch.xml";//"Список сх"installer.cmd";ем.xls";// "installer.cmd";
        org.apache.commons.net.ftp.FTPClient ftp = new org.apache.commons.net.ftp.FTPClient();
        try {
            //ftp.connect("10.32.4.167");            
            ftp.connect("glassfish.vniia.net");
            ftp.login("anonymous", "anonymous@glassfish.vniia.net");
            if (!ftp.changeWorkingDirectory("updater"))
                System.out.println("cant change dir-------------------------");
            //FTPClientConfig conf = new FTPClientConfig();
            //conf.setServerLanguageCode("ru");
            //ftp.configure(conf);
            //ftp.setAutodetectUTF8(true);
            //ftp.enterLocalPassiveMode();
            //ftp.setFileType(FTP.BINARY_FILE_TYPE);
            //ftp.enterRemotePassiveMode();
            FTPFile[] files = ftp.listFiles();
            for (int i=0;i<files.length;i++){
                System.out.println(pathConv(ftp.printWorkingDirectory()+"\\"+files[i].getName()));
                System.out.println(ftp.printWorkingDirectory()+"/"+files[i].getName());
                /*if (FTPReply.isPositiveCompletion(ftp.sendCommand("size",files[i].getName()))){}
                     String s = ftp.getReplyString();
                
                    System.out.println(s);
                    System.out.println("----------------");
                //}
                */
            }
            InputStream fis = ftp.retrieveFileStream(fname);
            
                FileOutputStream fos = new FileOutputStream(fname_iso_8859_1);
                BufferedInputStream bis = new BufferedInputStream(fis);
                BufferedOutputStream bos = new BufferedOutputStream(fos); 
                System.out.println(ftp.getReplyString());
                System.out.println(ftp.getReplyCode());
                if (fis==null){
                    System.out.println("fis is null");
                }
                int b = bis.read();
                while (b!=-1) {
                    bos.write(b);
                    b = bis.read();
                }
                bos.flush();
                fis.close();
                fos.close();                
        
            /*
            // do not try File file = new File("ftp://glassfish/updater/installer.cmd");
            File fileSink = new File(fname);
            ByteSink bs = new ByteSink() {

            @Override
            public OutputStream openStream() throws IOException {
                OutputStream os = new FileOutputStream(fileSink);
                return os;
            }
            };
            Files.asByteSource(file).copyTo(bs);
            */
        }catch (IOException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    static String getUcode(String s){
        StringBuilder b = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (c >= 128)
                b.append("\\u").append(String.format("%04X", (int) c));
            else
                b.append(c);
        }
        return b.toString();
    }
    static final String decode(final String in)
{
    String working = in;
    int index;
    index = working.indexOf("\\u");
    while(index > -1)
    {
        int length = working.length();
        if(index > (length-6))break;
        int numStart = index + 2;
        int numFinish = numStart + 4;
        String substring = working.substring(numStart, numFinish);
        int number = Integer.parseInt(substring,16);
        String stringStart = working.substring(0, index);
        String stringEnd   = working.substring(numFinish);
        working = stringStart + ((char)number) + stringEnd;
        index = working.indexOf("\\u");
    }
    return working;
}
    public static String pathConv(String ftpPath){
            byte[] iso_8859_1;
        try {
            iso_8859_1 = new String(ftpPath.getBytes(),"UTF-8").getBytes("ISO-8859-1"); //WINDOWS-1251 or 1252 is not accepted
            String fname_iso_8859_1 = new String(iso_8859_1,"UTF-8");
            StringBuilder sb = new StringBuilder("\\\\fileserver");
            sb.append(fname_iso_8859_1.replace("/", "\\"));
            return sb.toString();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(FTPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
