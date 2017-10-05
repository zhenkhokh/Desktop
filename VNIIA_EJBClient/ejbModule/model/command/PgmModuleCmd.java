package model.command;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.umr.PgmModule;

import model.decorator.SERVICE_NAMES;
import model.service.GetPgmModuleSrv;
import model.service.IEjbGetPgmModule;
import model.service.Reciver;

public class PgmModuleCmd implements Command<ArrayList<PgmModule>> {
	String JNDI_SERVICE=SERVICE_NAMES.JNDI_PGM_MODULE_DEFAULT;
	GetPgmModuleSrv mAppSrv;
	IEjbGetPgmModule moduleApp;
	
	public PgmModuleCmd(){
    	try{
            if (mAppSrv==null){
            	System.out.println("start context");
            	InitialContext ctx = new InitialContext();
            	System.out.println("start lookup");
            	moduleApp = (IEjbGetPgmModule)ctx.lookup(JNDI_SERVICE);
            	System.out.println("start service");
            }else{
            	System.out.println(ModulesCmd.class.getName()+" is "+moduleApp);
            }        
        	}catch (NamingException e){
        		System.out.println(e.getMessage());
        	}  
	}
	@Override
	public ArrayList<PgmModule> execute(String moduleId) {
		return moduleApp.getModuleApp(moduleId);
	}

	@Override
	public void setReciever(Reciver mAppSrv) throws IllegalArgumentException {
        if (mAppSrv instanceof GetPgmModuleSrv)
            this.mAppSrv = (GetPgmModuleSrv) mAppSrv;
        else
            throw new IllegalArgumentException();
    }
}
