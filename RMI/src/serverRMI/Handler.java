/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serverRMI;

import clientRMI.Parser;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import root.IFilter;
import root.IHandler;

/**
 *
 * @author zheka
 */
public class Handler implements IHandler{
Invoker invoker = new Invoker();
Installer installer = new Installer();

public Handler(){
    invoker.setInstaller(installer);
} 
    @Override
    public void execute() {
        invoker.execute();
    }
    @Override
    public void setExeName(String s) {
        installer.setExeName(s);
    }
    @Override
    public String getExeName() {
        return installer.getExeName();
    }
    @Override
    public ArrayList<String> getFiles() throws Exception{
        return installer.getFiles();
    }
    @Override
    public ArrayList<String> getFilteredFiles() throws Exception {
        return installer.getFilteredFiles();
    }
    @Override
    public void setParams(String s) {
        installer.setParams(s);
    }
    @Override
    public String getParams() {
        return installer.getParams();
    }

}
