/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import model.decorator.SERVICE_NAMES;
import model.msysAdmin.VMenu;
import model.service.IEjbGetModules;
import model.service.Reciver;

import java.util.ArrayList;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.service.GetModulesSrv;

/**
 *
 * @author 35-khei
 */
public class ModulesCmd implements Command<ArrayList<VMenu>> {
    //@EJB
	GetModulesSrv moduleSrv;
    IEjbGetModules modules;

    public ModulesCmd() {
    	this(SERVICE_NAMES.JNDI_MODULES_DEFAULT);    	
    }
    protected ModulesCmd(String jndiService){
    	try{
            if (moduleSrv==null){
            	System.out.println("start context");
            	InitialContext ctx = new InitialContext();
            	modules = (IEjbGetModules)ctx.lookup(jndiService);
            }else{
            	System.out.println(ModulesCmd.class.getName()+" is "+moduleSrv);
            }        
        	}catch (NamingException e){
        		System.out.println(e.getMessage());
        } 
    }
    @Override
    public ArrayList<VMenu> execute(String fieldValue) {
        return modules.getModules(fieldValue);
    }

    @Override
    public void setReciever(Reciver moduleSrv) throws IllegalArgumentException{
        if (moduleSrv instanceof GetModulesSrv)
            this.moduleSrv = (GetModulesSrv) moduleSrv;
        else
            throw new IllegalArgumentException();
    }
}
