package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Chain;
import model.RequestBuilder;
import model.command.Invoker;
import model.command.UserCmd;
import model.decorator.SERVICE_NAMES;
import model.msysAdmin.VUsers;
import model.service.IEjbGetApps;
import model.service.IEjbGetDecoder;
import model.service.IEjbGetUser;
import model.service.Reciver;

public class Tests {

	@org.junit.Test
	//@org.testng.annotations.Test
	public void testChain() {
		Chain chain = new Chain("MSYS_ARA5");
		System.out.println("chain----"+chain.getUserId());
		Set<String> set = new HashSet<String>();
		set.add("623");
		//set.add("7668");
		org.junit.Assert.assertFalse(!(chain.getUserId()).equals(set));
	}
	@org.junit.Test	
	public void testGetAppName() {
		Chain chain = new Chain("MSYS_KNO35");
		System.out.println("msys--------"+chain.getAppsName());
		System.out.println("msys--------"+chain.getAppsName_());

//		Iterator<String> i = chain.getAppsName().iterator();
//		if (Reciver.defaultFactoryName.equalsIgnoreCase("TEST")){
//			System.out.println("Hello1---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Справочник готовой продукции"));
//			System.out.println("Hello1---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("АС Финансы"));
//			System.out.println("Hello1---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Ввод накладных на заготовки и детали и готовую продукцию"));
//		}else if (Reciver.defaultFactoryName.equalsIgnoreCase("SKY")){
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Диспетчирование основного производства"));
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Система проектного документооборота"));
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Комплектовочный склад (КС-2)"));	
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Справочник заказов"));
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Управление проектами"));
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Рабочая матрица плана"));
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("План ДСЕ, управление производством"));
//			System.out.println("Hello2---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Справочник “Контрагенты”"));	
//			System.out.println("Hello2---");
//		}else if (Reciver.defaultFactoryName.equalsIgnoreCase("FOS")){
//			System.out.println("Hello3---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Справочник готовой продукции"));
//			System.out.println("Hello3---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("АС Финансы"));
//			System.out.println("Hello3---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Ввод накладных на заготовки и детали и готовую продукцию"));
//			System.out.println("Hello3---");
//			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("АС Комплектация-2"));
//		}else
//			org.junit.Assert.assertFalse(true);
		
		ArrayList<String> set = new ArrayList<String>();
		set.add("Комплектовочный склад (КС-2)");
		set.add("Справочник “Контрагенты”");
		set.add("Управление проектами");
		set.add("Рабочая матрица плана");
		set.add("Диспетчирование основного производства");
		set.add("Справочник заказов");
		set.add("План ДСЕ, управление производством");
		set.add("Система проектного документооборота");
		org.junit.Assert.assertFalse(!(set).equals(chain.getAppsName()));
		
		set.clear();
		set.add("АС Комплектация-2");
		set.add("Ввод накладных на заготовки и детали и готовую продукцию");
		set.add("Справочник готовой продукции");
		set.add("АС Финансы");
		org.junit.Assert.assertFalse(!(set).equals(chain.getAppsName_()));
	}
	@org.junit.Test	
	public void testGetUmrAppName() {
		Chain chain = new Chain("MSYS_ARA5");
		chain.initUmrChain();
		System.out.println("umr--------"+chain.getUmrAppsName());
		System.out.println("umr------1--"+chain.getNumber());

		org.junit.Assert.assertFalse(!((String)chain.getNumber().iterator().next()).equalsIgnoreCase("349"));
		Iterator<?> i = chain.getUmrAppsName().iterator();
		if (Reciver.defaultFactoryName.equalsIgnoreCase("TEST")||
				Reciver.defaultFactoryName.equalsIgnoreCase("SKY")||
				Reciver.defaultFactoryName.equalsIgnoreCase("FOS")){
			System.out.println("Hello4---");
			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Система учета рабочего времени"));
			System.out.println("Hello4---");
			org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase("Электронный табель"));
		}else
			org.junit.Assert.assertFalse(true);

	}
	@org.junit.Test	
	public void testCmd() {
	  	Invoker invoker = new Invoker();
	  	UserCmd c1 = new UserCmd();
	      //c1.setReciever(new GetUserSrv());
	      invoker.pushCommand(c1);
	      invoker.pullCommand("MSYS_ARA5");
		  ArrayList<VUsers> col = (ArrayList<VUsers>) invoker.pullResult();
		  for (Iterator iterator = col.iterator(); iterator.hasNext();) {
			VUsers type = (VUsers) iterator.next();
			RequestBuilder<VUsers> rb = new RequestBuilder<VUsers>();
			//System.out.println(rb.printEntity(type));
			org.junit.Assert.assertFalse(type.getUserId().toString().equalsIgnoreCase("349"));
		}
	}
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
	@org.junit.Test
	public void testDecoder(){
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			IEjbGetDecoder decoder = (IEjbGetDecoder) ctx.lookup(SERVICE_NAMES.JNDI_DECODER);
			String pswd1 = decoder.decodePassword("2E0");//2E 
			System.out.println("pswd-----------"+pswd1);
			if (pswd1!=null)
				org.junit.Assert.assertFalse(!pswd1.equalsIgnoreCase("ARA"));
			else
				org.junit.Assert.assertFalse(true);
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
