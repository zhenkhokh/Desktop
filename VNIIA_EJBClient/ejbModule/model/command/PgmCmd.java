package model.command;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.decorator.SERVICE_NAMES;
import model.service.GetPgmSrv;
import model.service.IEjbGetPgm;
import model.service.Reciver;
import model.umr.VPgm;

public class PgmCmd implements Command<ArrayList<VPgm>> {
	String JNDI_SERVICE=SERVICE_NAMES.JNDI_PGM;
	GetPgmSrv pgmSrv;
	IEjbGetPgm pgm;
	
	public PgmCmd(){
		try{
			if (pgmSrv==null){
	        	System.out.println("start context");
	        	InitialContext ctx = new InitialContext();
	        	System.out.println("start lookup");
	        	pgm = (IEjbGetPgm)ctx.lookup(JNDI_SERVICE);
	        	System.out.println("start service");
	        }else{
	        	System.out.println(PersonNumCmd.class.getName()+" is "+pgmSrv);
	        }        
    	}catch (NamingException e){
    		System.out.println(e.getMessage());
    	} 
	}
	
	@Override
	public ArrayList<VPgm> execute(String fieldValue) {
		return pgm.getPgm(fieldValue);
	}

	@Override
	public void setReciever(Reciver service) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
	}

}
