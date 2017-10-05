package ru.vniia.server;

import java.io.IOException;
import java.rmi.server.RMIClientSocketFactory;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.management.MalformedObjectNameException;

import model.Chain;
import model.Manager;
import model.Session;

import org.apache.commons.collections.map.HashedMap;

import controller.dto.Dto;
import ru.vniia.client.GreetingService;
import java.util.Set;
import rmiClient.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
/**
 * The server-side implementation of the RPC service.
 * 		this.getThreadLocalResponse().setHeader("Content-Type", "application/octet-stream");
 *		this.getThreadLocalResponse().setHeader("Content-Disposition", "attachment;filename=installer.exe");
 *		use that lines for dynamic download
 */
@Startup
@Singleton(name="timer")
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	Manager manager = Manager.getInstance();
	
	@Override
	public String greetServer(String name) throws IllegalArgumentException {return null;}
	@Override
	public String action(Dto dto){
		System.out.println("client Host is "+this.getThreadLocalRequest().getRemoteHost());
		System.out.println("client Addr is "+this.getThreadLocalRequest().getRemoteAddr());
		System.out.println("client URI is "+this.getThreadLocalRequest().getRequestURI());
		System.out.println("client URL is "+this.getThreadLocalRequest().getRequestURL());
		System.out.println("client localAddr is "+this.getThreadLocalRequest().getLocalAddr());
		System.out.println("client localName is "+this.getThreadLocalRequest().getLocalName());
		System.out.println("client pathInfo is "+this.getThreadLocalRequest().getPathInfo());
		System.out.println("client remoteUser is "+this.getThreadLocalRequest().getRemoteUser());
		dto.domain = this.getThreadLocalRequest().getRemoteHost();
		System.out.println("client dto.domain is "+dto.domain);
		
		Session session = null;
		if (manager.isValidSessionId(dto.id)){
			session = manager.getSession(dto.id);
			System.out.println("unit session id="+dto.id);
			System.out.println("update path is "+dto.xmlName);
			System.out.println("srv is "+dto.srv);
			Dto toRMI = session.getDto();
			dto.name = toRMI.name;
			dto.password = toRMI.password;
		}else
			return "Ваша сессия устарела. Обновите страницу";
		//toRMI.action = dto.action;
		//toRMI.domain = dto.domain;
		//toRMI.
		//dto.password = session.getPassword();
		//dto.srv = "sky.vniia.net:1521:db35"; 
		//dto.domain = this.getThreadLocalRequest().getRemoteHost(); //got from viewer
		
		switch (dto.action){
			//case  INSTALL: return install(dto);
			//case  UPDATE: return update(dto);
			case  EXECUTE: return execute(dto);
			default : return "no action";
		}
	}
	@Override
	public Dto login (Dto dto){
		if (manager.isValidUser(dto.name, dto.password)){
			int id = manager.init(new Chain(dto.name));
			Session s = manager.getSession(id);
			Dto model = s.getDto();
			s.setPassword(dto.password); //TODO remove it 
			model.id = id;
			return model;
		}
		return null;
	}
	@Override
	public Boolean loginFromServer (){return null; } 
	@Override
	public void logout (Dto model){manager.destroy(model.id);}
	private String install(final Dto dto){		
		return "ok";
	}
	private String update(final Dto dto){
		return "ok";
	}
	private String execute(final Dto dto){
		try{
		RmiClient c = new RmiClient(dto.domain);
		c.execute(dto.xmlName,dto.name+" "+dto.password+" "+dto.srv+" start");
		} catch (IOException e){
			return e.getMessage();
		} catch (MalformedObjectNameException e) {
			return e.getMessage();
		}
		return "ok";
	}
	//TODO use TIME_FIELD and EXPIRED in custom annotation
	//@Schedule(second="*",minute="0,59",hour="*",persistent=false)
	@Schedule(second="*",minute="0,59",hour="*",persistent=false)
	public void autoDo(){
		manager.autoDo();
	}
	/*//uncomment to test sheduller, see log
	@Schedule(second="0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,20,21,22,23,24,25,26,27,30,31,32,40,44,48,49,50,51,52,53,54,59",minute="*",hour="*",persistent=false)
	public void autoDoTest(){
		manager.autoDoTest();
	}
	@Schedule(second="16,27,37,47,57",minute="*",hour="*",persistent=false)
	public void addSession(){
		manager.initTest();
	}
	 */
}
