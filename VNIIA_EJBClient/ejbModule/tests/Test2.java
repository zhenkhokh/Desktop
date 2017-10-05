package tests;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.msysAdmin.VUsers;
import model.service.IEjbGetUser;

public class Test2 {
	@org.junit.Test
	public void testJNDI(){
	  	try{
        	System.out.println("start context");
        	InitialContext ctx = new InitialContext();
        	System.out.println("start lookup");
        	IEjbGetUser user = (IEjbGetUser)ctx.lookup(Test_.JNDI_SERVICE1);
        	System.out.println("start service");
        	ArrayList<VUsers> vUsers = user.getUser("MSYS_ARA5");
    	}catch (NamingException e){
    	  	org.junit.Assert.assertFalse(true);
    	}
	  	org.junit.Assert.assertFalse(false);
	}

}
