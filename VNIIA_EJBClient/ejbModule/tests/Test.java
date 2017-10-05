package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import model.Chain;
import model.RequestBuilder;
import model.command.Invoker;
import model.command.UserCmd;
import model.decorator.SERVICE_NAMES;
import model.msysAdmin.VUsers;
import model.service.GetUserSrv;
import model.service.IEjbGetApps;
import model.service.IEjbGetDecoder;
import model.service.IEjbGetUser;
import model.service.Reciver;
//import model.service.TimerSrv;

public class Test {
	String msys_user_name = "MSYS_KNO35";
	/*
	@EJB(name="java:global/VNIIA_EJB/timer1",beanInterface=model.service.ITimer.class)// java:comp/env do not support see spec
	 TimerSrv timerSrv;	
	@org.junit.Test
	public void testTimer() {
		if (timerSrv==null)
			org.junit.Assert.assertFalse(true);
		timerSrv.autoDo();
		org.junit.Assert.assertFalse(false);
	}
	*/
	/*
	@EJB(name="java:global/VNIIA_EJB/user")
	 GetUserSrv user;	
	@org.junit.Test
	public void testUserEjb() {
		System.out.println("start testUserEjb...");
		String name=msys_user_name;
		ArrayList<VUsers> users=null;
		if(user!=null)
			users=user.getUser(name);
		else
			org.junit.Assert.assertFalse(true);
		if (users!=null&&users.iterator().hasNext()){
			String name1=users.iterator().next().getUserLogin();
			System.out.println("------- "+name1);
			org.junit.Assert.assertFalse(!name1.equalsIgnoreCase(name));
		}else
			org.junit.Assert.assertFalse(true);
	}
	*/
	@org.junit.Test	
	public void testGetAppName() {
		System.out.println("start  testGetAppName...");
		Chain chain = new Chain(msys_user_name);
		System.out.println("msys--------"+chain.getAppsName());
		System.out.println("msys--------"+chain.getAppsName_());

		ArrayList<String> gold = new ArrayList<>();
		ArrayList<String> gold_ = new ArrayList<>();
		gold.add("Справочник заказов");
		gold.add("Справочник “Контрагенты”");
		gold.add("Справочник готовой продукции");
		gold.add("АС Комплектация-2");
		gold.add("Диспетчирование основного производства");
		gold.add("Комплектовочный склад (КС-2)");
		gold.add("План ДСЕ, управление производством");
		gold.add("Рабочая матрица плана");
		gold.add("Ввод накладных на заготовки и детали и готовую продукцию");
		gold.add("Система проектного документооборота");
		gold.add("Управление проектами");
		gold.add("АС Финансы");

		gold_.add("АС Комплектация-2");
		gold_.add("АС Финансы");
		gold_.add("Ввод накладных на заготовки и детали и готовую продукцию");
		gold_.add("Справочник готовой продукции");
		
		if (gold.size()!=chain.getAppsName().size())
			org.junit.Assert.assertFalse(true);
		System.out.println(gold_.size()+" "+gold.size());
		if (gold_.size()!=chain.getAppsName_().size())
			org.junit.Assert.assertFalse(true);
		org.junit.Assert.assertFalse(!(gold).equals(chain.getAppsName()));	
		org.junit.Assert.assertFalse(!(gold_).equals(chain.getAppsName_()));
	}
	@org.junit.Test	
	public void testGetUmrAppName() {
		System.out.println("start  testGetUmrAppName...");
		Chain chain = new Chain(msys_user_name);
		chain.initUmrChain();
		System.out.println("umr--------"+chain.getUmrAppsName());

		ArrayList<String> gold = new ArrayList<>();
		gold.add("Call-центр");
		gold.add("Метрология");
		gold.add("Система учета рабочего времени");
		gold.add("Учет материальных ресурсов");
		gold.add("Учет основных средств");
		gold.add("Электронный табель");
		Iterator<?> i = chain.getUmrAppsName().iterator();
		Iterator<?> iGold = gold.iterator();
		if (gold.size()!=chain.getUmrAppsName().size())
			org.junit.Assert.assertFalse(true);
		for (; i.hasNext();) {		
				System.out.println("Hello4---");
				String g = (String) iGold.next();
				org.junit.Assert.assertFalse(!((String)i.next()).equalsIgnoreCase(g));
				System.out.println("Hello4--- "+g);
		}
	}
	@org.junit.Test	
	public void testCmd() {
		System.out.println("start  testCmd...");
	  	Invoker invoker = new Invoker();
	  	UserCmd c1 = new UserCmd();
	      //c1.setReciever(new GetUserSrv());
	      invoker.pushCommand(c1);
	      invoker.pullCommand(msys_user_name);
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
		System.out.println("start  testJNDI...");
	  	try{
        	System.out.println("start context");
        	InitialContext ctx = new InitialContext();
        	System.out.println("start lookup");
        	IEjbGetUser user = (IEjbGetUser)ctx.lookup(Test_.JNDI_SERVICE1);
        	System.out.println("start service");
        	ArrayList<VUsers> vUsers = user.getUser(msys_user_name);
    	}catch (NamingException e){
    	  	org.junit.Assert.assertFalse(true);
    	}
	  	org.junit.Assert.assertFalse(false);
	}
	@org.junit.Test
	public void testDecoder(){
		System.out.println("start  testDecoder...");
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
