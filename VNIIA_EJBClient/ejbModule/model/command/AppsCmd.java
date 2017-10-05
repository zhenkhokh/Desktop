package model.command;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.decorator.SERVICE_NAMES;
import model.service.GetAppsSrv;
import model.service.IEjbGetPerson;
import model.service.IEjbGetApps;
import model.service.Reciver;
import model.umr.VIndustrialPgms;

public class AppsCmd implements Command<ArrayList<VIndustrialPgms>> {
	GetAppsSrv uMRAppSrv;
	IEjbGetApps apps;
	
	public AppsCmd(){
		this(SERVICE_NAMES.JNDI_APPS);
	}	
	protected AppsCmd(String JNDI_SERVICE){
		try{
			if (uMRAppSrv==null){
	        	System.out.println("start context");
	        	InitialContext ctx = new InitialContext();
	        	System.out.println("start lookup");
	        	apps = (IEjbGetApps)ctx.lookup(JNDI_SERVICE);
	        	System.out.println("start service");
	        }else{
	        	System.out.println(PersonNumCmd.class.getName()+" is "+uMRAppSrv);
	        }
    	}catch (NamingException e){
    		System.out.println(e.getMessage());
    	}
	}
	@Override
	public ArrayList<VIndustrialPgms> execute(String pgmId) {
		return apps.getApps(pgmId);
	}

	@Override
	public void setReciever(Reciver uMRAppSrv) throws IllegalArgumentException {
	       if (uMRAppSrv instanceof GetAppsSrv)
	            this.uMRAppSrv = (GetAppsSrv) uMRAppSrv;
	        else
	        	throw new IllegalArgumentException();
	}
}
