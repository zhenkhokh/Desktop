/**
 * Created by 35-khei on 30.03.2015.
 */
import clientRMI.CpFiles;
import clientRMI.Parser;
import serverRMI.Installer;
import serverRMI.Invoker;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import root.IInvoker;

public class testRMI {
            //static String updatePath = "\\\\fileserver\\35-Soft\\Комплектация 2\\COMPLECTASYA-2\\COMPLECTASYA-2\\bin\\Debug\\";//"C:\\Users\\35-khei\\Desktop\\javaSoft\\APP\\Debug\\";
            //static String updatePath = "C:\\APP\\TMP\\";
            static String updatePath = "C:\\Users\\35-khei\\Desktop\\javaSoft\\APP\\Debug\\";
            //static String updatePath = "/mnt/randDir/";
            //static String updatePath = "/mnt/javaSoft/APP/Debug/";

            static String anyPath="C:\\Users\\35-khei\\Desktop\\";
            static String fileName = "update.xml";
            
            

            @SuppressWarnings("empty-statement")
    public static void main(String[] argv){
                  //testScannerXML();//bad
                    //testPipeCopyXML();//good
                    //testBufferCopyXML(); //bad
                    
                    //testInvoke();
                    
                    //testXMLParser();
                    //testcopyClassXML();
                    //String[] params = {" 1"," 2"};                  helloParams(params);
                    //delphiBug("C:\\APP\\Plan_DSE.exe ");
                    //removeDoubleSlash();
                    File file = new File(anyPath+"1.txt");
                    if (file.isFile())
                        System.out.println(file.lastModified());
                    else
                        System.out.println("file isn't exist");
     }

    // bad code
    static void testScannerXML(){
        long time = System.currentTimeMillis();
        try {
            Scanner scanner = new Scanner(new File(updatePath+fileName));
            StringBuffer sb = new StringBuffer();
            FileOutputStream fos=new FileOutputStream(anyPath+fileName);
            while(scanner.hasNext()){
                //sb.append(scanner.next());
                fos.write(scanner.next().getBytes());
            }
            //fos.write(sb.toString().getBytes()); 
            System.out.println("File is saved on "+anyPath+fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("work "+(System.currentTimeMillis()-time));
    }
    static void testcopyClassXML(){
        long time = System.currentTimeMillis();
        CpFiles cpFiles = new CpFiles();
               // try {
                    cpFiles.copy(updatePath+"update.xml", anyPath+"update.xml");
               // } catch (FileNotFoundException ex) {
               //     Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
               // }
        System.out.println("work "+(System.currentTimeMillis()-time));
    }
    static void testBufferCopyXML(){
        long time = System.currentTimeMillis();
        try {            
            FileInputStream fis = new FileInputStream(new File(updatePath+fileName));
            BufferedInputStream bis = new BufferedInputStream(fis);

            FileOutputStream fos = new FileOutputStream(anyPath+fileName);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            int b = bis.read();
            while (b!=-1) {                
                bos.write(b);
                b = bis.read();
            }
            bos.flush();
            System.out.println("File is safed on "+anyPath+fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("work "+(System.currentTimeMillis()-time));
    }
    static void testPipeCopyXML(){
        long time = System.currentTimeMillis();
                try {
            FileInputStream fis = new FileInputStream(new File(updatePath+fileName));            
            FileOutputStream fos = new FileOutputStream(anyPath+fileName);
            PipedInputStream pis = new PipedInputStream();
            PipedOutputStream pos = new PipedOutputStream(pis);

            int b = fis.read();
            while (b!=-1) {                
                pos.write(b);
                b = fis.read();
                fos.write(pis.read());
            }
            System.out.println("File is piped ");
            
            System.out.println("File is safed on "+anyPath+fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("work "+(System.currentTimeMillis()-time));
    }
    static void testXMLParser(){
    	try{
        File file = new File(updatePath);
        Parser parser = new Parser(file);
        System.out.println(parser.getAppName());
        System.out.println(parser.getCritical());
        System.out.println(parser.getStartUpExe());
        System.out.println(parser.getUpdatePath());
        System.out.println(parser.getVersion());
        
        System.out.println(parser.getFileNames());
        System.out.println(parser.getFiles());
    	}catch (FileNotFoundException e){
    		System.out.println("File not found");
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    void  testRMIServer(){
        serverRMI.Installer installer = new Installer();
        serverRMI.Invoker service;        
        try {
            service = new Invoker();
            service.setInstaller(installer);
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }
            //IInvoker stub = (IInvoker)UnicastRemoteObject.exportObject(service, 0);
            IInvoker stub=service;
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind("invokerService", (Remote) stub);
            
            //System.setProperty("java.rmi.server.hostname", "10.66.3.73");           
            //Naming.bind("invokerService", service);
            service.setExeName("\\\\fileserver\\35-Soft\\Комплектация 2\\COMPLECTASYA-2\\COMPLECTASYA-2\\bin\\Debug\\");
            //service.execute("App.exe");
        } catch (RemoteException ex) {
            Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    static void delphiBug(String exePath){
            Runtime runtime = java.lang.Runtime.getRuntime();
            //String[] params = {"MSYS_hei35", "hei35", "sky"};
                try {
                    //runtime.exec(exePath, params);
                    runtime.exec(exePath+" MSYS_hei35 hei35 sky ");
                } catch (IOException ex) {
                    Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    static void helloParams(String[] params){
        Runtime runtime = java.lang.Runtime.getRuntime();
        try {
                runtime.exec("/usr/bin/echo 123");
            } catch (IOException ex) {
                Logger.getLogger(testRMI.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    static void removeDoubleSlash(){
                File dir = new File(anyPath+"\\1\\2\\3");
        dir.mkdirs();
    	
    	CpFiles cpFiles = new CpFiles();
    	StringBuffer sb = new StringBuffer("\\\\dfjo\\ijds\\\\jjds\\\\\\");
    	System.out.println("primary line:"+sb);
    	String s = cpFiles.removeDoubleSlash(sb).toString();
    	System.out.println("after :"+s);
    }
}
