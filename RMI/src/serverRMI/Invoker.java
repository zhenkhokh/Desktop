package serverRMI;

import clientRMI.ErrorMsg;
import clientRMI.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import root.IFilter;
import root.IInstaller;
import root.IInvoker;
//import sun.rmi.server.UnicastRef;
 //extends UnicastRemoteObject
public class Invoker  implements IInvoker {
    IInstaller installer;

    //    public Invoker() throws RemoteException{    }    
        public void setInstaller(IInstaller i){
            installer = i;
        }
        @Override
	public ArrayList<String> getFiles() throws Exception {
		return installer.getFiles();
	}
        @Override
	public ArrayList<String> getFilteredFiles() throws Exception {
		return installer.getFilteredFiles();
	}
        @Override
	public void execute() {
		try{
                        if (installer==null){
                           throw new ExceptionInInitializerError("installer is not set");
                        }
                        if (!installer.isInstalled()){
                            System.out.println("installing ...");
                            if (!installer.install()){
                                System.out.println("invoke cant install");
                                return;
                            }
                        }else
                            System.out.println("installing succesfully");
                        
                        if (!installer.update()){
                            System.out.println("invoke cant update");
                            return;
                        }
                        Runtime runtime = java.lang.Runtime.getRuntime();
                        //runtime.exec(installer.getExeName()+installer.getParser().getStartUpExe());
                        System.out.println("appName is "+installer.getParser().getAppName());
                        String appPath = localPathRoot+installer.getParser().getAppName()+"\\";
                        //System.out.println("params is "+getParams());
                        //runtime.exec(appPath+installer.getParser().getStartUpExe());              
                        runtime.exec(appPath+installer.getParser().getStartUpExe()+" "+getParams());
		}  catch (IOException ex) {
                    new ErrorMsg("Запуск не возможен");
                } catch (Exception ex) {
                    Logger.getLogger(Invoker.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        public void setExeName(String s) {
        installer.setExeName(s);
    }

    public String getExeName() {
        return installer.getExeName();
    }
    @Override
    public void setParams(String s) {
        installer.setParams(s);
    }
    @Override
    public String getParams() {
        return installer.getParams();
    }
    @Override
    public void updateService() {
        Installer installer = new Installer();
        installer.setExeName("Invoker_Invoker");
        if (!installer.isInstalled()){
            System.out.println("installing ...");
            if (!installer.install()){
                System.out.println("service cant be installed");
                return;
            }
        }else
             System.out.println("installing succesfully");
        if(!installer.update())
            System.out.println("service cant be updated");        
    }
}
