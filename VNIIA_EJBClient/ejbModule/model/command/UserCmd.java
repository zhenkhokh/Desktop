/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.decorator.SERVICE_NAMES;
import model.msysAdmin.VUsers;
import model.service.GetUserSrv;
import model.service.IEjbGetUser;
import model.service.Reciver;

/**
 *
 * @author 35-khei
 */
public class UserCmd implements Command<ArrayList<VUsers>> {
	String JNDI_SERVICE=SERVICE_NAMES.JNDI_USER_DEFAULT;
    //  Error in allocating a connection
	//@EJB(lookup="java:global/VNIIA_EJB/user")
	GetUserSrv userSrv;    
    IEjbGetUser user;
        
    public UserCmd() {    	
    	this(SERVICE_NAMES.JNDI_USER_DEFAULT);
    }
    protected UserCmd(String jndiService){
    	try{
	        if (userSrv==null){
	        	System.out.println("start context");
	        	InitialContext ctx = new InitialContext();
	        	user = (IEjbGetUser)ctx.lookup(jndiService);
	        }else{
	        	System.out.println(UserCmd.class.getName()+" is "+userSrv);
	        }        
	    	}catch (NamingException e){
	    		System.out.println(e.getMessage());
    	}
    }
    @Override
    public ArrayList<VUsers> execute(String fieldValue) {
        return user.getUser(fieldValue);
    }

    @Override
    public void setReciever(Reciver userSrv) throws IllegalArgumentException{
        if (userSrv instanceof GetUserSrv)
            this.userSrv = (GetUserSrv) userSrv;
        else
            throw new IllegalArgumentException();
    }
}
