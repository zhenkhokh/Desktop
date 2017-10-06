/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRMI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.concurrent.ForkJoinPool;
//import java.util.concurrent.ForkJoinTask;
//import java.util.concurrent.RecursiveTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.Source;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import root.IHandler;

/**
 *
 * @author 35-khei
 */
// TODO uncomment for 1.7 join-task framwork, and generate FileExcerprtion
public class CpFiles /*extends RecursiveTask<Boolean> */{
        private int cnt = 0;
        private int index;
        private Monitor monitor;
        public FTPClient ftp  = new FTPClient();
        static CpFiles inst=null;
        
        static public CpFiles getInstance(){
            if (inst==null){
                inst = new CpFiles();
            }
            return inst;
        }        
      private CpFiles(int index){
          this.index = index;
      }
      private  CpFiles(){
    	  index = -1;
            try {
                ftp.connect(IHandler.serverName);
                if (!FTPReply.isPositiveCompletion(ftp.getReply())){
                    ftp.disconnect();
                    System.err.println("Отказано в соединении");
                }
            } catch (IOException ex) {
                System.err.println("Невозможно получить соединение");
            }
      }      

    public void copy(String sourceFTP, String distFileFTP)/* throws FileNotFoundException */{
                cnt++;
            try {
                if (!ftp.isConnected())
                    ftp.connect(IHandler.serverName);
                InputStream fis = ftp.retrieveFileStream(sourceFTP);
                FileOutputStream fos = new FileOutputStream(toISO_8859_1_And_SlashConv(distFileFTP));
                copyProc(fis, fos);
                System.out.println("File is safed on "+toISO_8859_1_And_SlashConv(distFileFTP));

                fos.close();
                fis.close();
                if (!ftp.completePendingCommand()){
                    disconnect();
                    System.err.println("File error transfer");                    
                }                    
                cnt--;
        } /*catch (FileNotFoundException ex) {
                Logger.getLogger(CpFiles.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("from CpFiles: file not found dist="+dist1+" sourceFTP="+sourceFTP1);
                cnt--;
        }*/ catch (IOException ex) {
            System.err.println("Не возможно скопировать из "+sourceFTP+" в "+distFileFTP);
        }
    }
    /*
    * distFile is not distFileFTP
    */    
    public final void copySimple(String source, String distFile){
                        cnt++;
            try {            	
                FileInputStream fis = new FileInputStream(source);
                FileOutputStream fos = new FileOutputStream(distFile);
                copyProc(fis, fos);
                System.out.println("File is safed on "+distFile);
                fos.close();
                fis.close();
                cnt--;
        } /*catch (FileNotFoundException ex) {
                Logger.getLogger(CpFiles.class.getName()).log(Level.SEVERE, null, ex);
                System.err.println("from CpFiles: file not found dist="+dist1+" sourceFTP="+sourceFTP1);
                cnt--;
        }*/ catch (IOException ex) {
            System.err.println("Не возможно скопировать из "+source+" в "+distFile);
        }
    }
    private void copyProc(InputStream fis,OutputStream fos) throws IOException{
                BufferedInputStream bis = new BufferedInputStream(fis);
                BufferedOutputStream bos = new BufferedOutputStream(fos);                
                int b = bis.read();
                while (b!=-1) {
                    bos.write(b);
                    b = bis.read();
                }
                bos.flush();
    }

    public void copy(ArrayList<String> sourceFTP, String dist)/* throws FileNotFoundException */{
        cnt=0;
        for (Iterator<String> iterator = sourceFTP.iterator(); iterator.hasNext();) {
                String next = iterator.next();
          //      try {
                    copy(next, dist+next);
          //      }catch (FileNotFoundException e){
          //          cnt++;
           //     }
            }
        disconnect();
        //if (cnt>0)
        //    throw new FileNotFoundException();
    }

    public void copy(String sourceFTPPrefix, ArrayList<String> sourceFTP, String dist)/* throws FileNotFoundException */{
                /*
                ForkJoinPool pool = new ForkJoinPool();
                Monitor.filesGlobal.clear();
                for (Iterator iterator = sourceFTP.iterator(); iterator.hasNext();) {
                String string = (String) iterator.next();
                Monitor.filesGlobal.add(string);
                }
                Monitor.sourceFTPPrefix = sourceFTPPrefix;
                Monitor.dist = dist;
                
                ForkJoinTask mainTask = new CpFiles(-1);
                pool.invoke(mainTask);
                if (!(Boolean)mainTask.join())
                throw new FileNotFoundException();
                */
                
                cnt=0;
                for (Iterator<String> iterator = sourceFTP.iterator(); iterator.hasNext();) {
                    String next = iterator.next();
                    //      try{
                    copy(Parser.removeDoubleSlash(sourceFTPPrefix+next)
                            ,Parser.removeDoubleSlash(dist+next));
                    //System.out.println(dist+next);
                    //      }catch (FileNotFoundException e){ cnt++;             }
                }
                disconnect();
                //if (cnt>0)
                //    throw new FileNotFoundException();
        
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }
    static public String toISO_8859_1_And_SlashConv(String s){
            try {
                byte[] iso_8859_1 = new String(s.getBytes(),"UTF-8").getBytes("ISO-8859-1");//WINDOWS-1251 or 1252 is not accepted
                String out = new String(iso_8859_1,"UTF-8");
                return out.replace("/", "\\");
            } catch (UnsupportedEncodingException ex) {
                System.err.println("Невозможно выполнить перекодировку");
            }
            return null;
    }
    
    private void disconnect(){
            try {
                ftp.logout();
                if (ftp.isConnected())
                    ftp.disconnect();
            } catch (IOException ex) {
                System.err.println("cant disconnect");
            }
    }

/*
    @Override
    protected Boolean compute() {
        // make subtask
        if (this.index!=-1&&this.index<monitor.filesGlobal.size()){
            try {
                String fileName = monitor.filesGlobal.get(this.index);
                System.out.println("copy file "+fileName+" to "+monitor.dist);
                copy(monitor.sourceFTPPrefix+fileName, monitor.dist+fileName);
            } catch (FileNotFoundException ex) {
                return false;
            }
            return true;
        }
        // devide task
        ArrayList<CpFiles> subtasks = new ArrayList<CpFiles>();
        for (int i=0;i<monitor.filesGlobal.size();i++){
            CpFiles subtask = new CpFiles(i);
            subtask.fork();
            subtasks.add(subtask);
        }
        Boolean out = true;
        for (Iterator<CpFiles> iterator = subtasks.iterator(); iterator.hasNext();) {
                CpFiles next = iterator.next();
                Boolean out1 = (Boolean)next.join();
                out = out & out1;
            }
        return out;
    }
    */
}

class Monitor{
    public static CopyOnWriteArrayList<String> filesGlobal = new CopyOnWriteArrayList<String>();
    public static String dist;
    public static String sourceFTPPrefix;
}
