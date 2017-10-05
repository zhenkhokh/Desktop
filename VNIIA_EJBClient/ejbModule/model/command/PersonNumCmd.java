package model.command;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.all.VPersonal;
import model.decorator.SERVICE_NAMES;
import model.service.GetPersonalSrv;
import model.service.IEjbGetPerson;
import model.service.Reciver;

public class PersonNumCmd implements Command<ArrayList<VPersonal>> {
	String JNDI_SERVICE=SERVICE_NAMES.JNDI_PERSON_DEFAULT;
	GetPersonalSrv getPersonalSrv;
	IEjbGetPerson personNum;
	
	public PersonNumCmd(){
		this(SERVICE_NAMES.JNDI_PERSON_DEFAULT);
	}
	protected PersonNumCmd(String jndiService){
		try{
			if (getPersonalSrv==null){
	        	System.out.println("start context");
	        	InitialContext ctx = new InitialContext();
	        	System.out.println("start lookup");
	        	personNum = (IEjbGetPerson)ctx.lookup(jndiService);
	        	System.out.println("start service");
	        }else{
	        	System.out.println(PersonNumCmd.class.getName()+" is "+getPersonalSrv);
	        }        
    	}catch (NamingException e){
    		System.out.println(e.getMessage());
    	} 
	}
	@Override
	public ArrayList<VPersonal> execute(String personalId) {		
		return personNum.getPersonNumber(personalId);
	}

	@Override
	public void setReciever(Reciver getPersonalSrv) throws IllegalArgumentException {
	       if (getPersonalSrv instanceof GetPersonalSrv)
	            this.getPersonalSrv = (GetPersonalSrv) getPersonalSrv;
	        else
	            throw new IllegalArgumentException();		
	}

}
