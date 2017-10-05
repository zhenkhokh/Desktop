package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;
import java.nio.channels.SeekableByteChannel;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import model.Chain;
import model.decorator.SERVICE_NAMES;
import model.service.IEjbGetDecoder;
import model.service.Reciver;
import model.Chain;
import java.util.Set;

import java_cup.sym;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.apache.regexp.recompile;

import java.util.Calendar;
import java.util.concurrent.ConcurrentHashMap;

import com.sun.xml.ws.api.pipe.PipelineAssembler;

//@Startup
//@Singleton(name="timer")
public class Manager implements ITimer {
	private static Manager singleton=null;
	public final int EXPIRED = 29;
	public final int TIME_FIELD = Calendar.MINUTE;
	public final static String SRV1 = Reciver.defaultFactoryName; 
	public final static String SRV2 = Reciver.msysFactoryName;
	public final static String UMR = Reciver.umrFactoryName;
	Map<String,String> up = new HashMap<String, String>();
	ArrayDeque<Integer> ids = new ArrayDeque<Integer>();
	private static Monitor monitor;
	private MonitorTest monitorTest = new MonitorTest();

	private Manager() {
		monitor = new Monitor();
		addUser("khei", "12341");
	}
	public static Manager getInstance(){
		if (singleton==null)
			singleton = new Manager();
		return singleton;
	}
	public int init(Chain chain){
		int id = getId();
		Calendar c = Calendar.getInstance();
		Integer time = new Integer(c.get(TIME_FIELD));
		monitor.addElement(id, new Session(this, chain), time);
		System.out.println("init id="+id+" tmark="+monitor.getTmark()+" time="+time+" sessions="+monitor.getSessions());		
		return id;
	}
	public void initTest(){
		int id = getIdTest();
		Calendar c = Calendar.getInstance();
		Integer time = new Integer(c.get(TIME_FIELD));
		monitorTest.addElement(id, new Object(), time);
		System.out.println("init id="+id+" tmark="+monitorTest.getTmark()+" time="+time+" sessions="+monitorTest.getSessions());
	}
	int getIdTest(){
		if (!ids.isEmpty())
			return ids.pop();
		int newId = monitorTest.getSize();
		return newId;
	}
	public Integer getTimeTest(int id){
		if (id>=0&&id<monitorTest.getSize())
			return monitorTest.getTmark().get(id);
		return null;
	}
	public void destroy(int id){
		if (id>=0&&id<monitor.getSize()){
			ids.push(id);
			monitor.remove(id);
			System.out.println("remove session id="+id);
		}else
			System.out.println("Cant remove such session isnt exist id="+id);
	}
	public Session getSession(int id){
		if (id>=0&&id<monitor.getSize()){
			Calendar c = Calendar.getInstance();
			Integer time = new Integer(c.get(TIME_FIELD));
			monitor.update(id, time);
			return monitor.getSessions().get(id);
		}
		return null;
	}
	int getId(){
		if (!ids.isEmpty())
			return ids.pop();
		int newId = monitor.getSize();
		return newId;
	}
	public Integer getTime(int id){
		if (id>=0&&id<monitor.getSize())
			return monitor.getTmark().get(id);
		return null;
	}
	/**
	 *  for accelerating 
	 */
	public void addUser(String name,String pass){
		up.put(name, pass);
	}
	//TODO use chain at parameters
	public boolean isValidUser(String loginName,String pass){
		if(up.get(loginName)!=null){
			return ((up.get(loginName).equals(pass))&&up.containsKey(loginName));
		}
		// init 1-st chain
		Chain chain = new Chain(loginName);
		if (chain.isValidName()){
			System.out.println("isValidUser"+" login is exist");
			if (chain.getPassword().iterator().hasNext())
				//if (chain.getPassword().iterator().next().equalsIgnoreCase(pass)){
				if (isValidPswd(chain.getPassword().iterator().next(),pass)){
					System.out.println("isValidUser"+" ok");
					return true;
				}else{
					System.out.println("isValidUser"+" password is incorrect");
					return false;
				}
			else{
				System.out.println("isValidUser"+" no password");
				return false;
			}
		}
		System.out.println("isValidUser"+" no valid user");
		return false;
	}
	private boolean isValidPswd(String pswd,String userPSWD){
    	InitialContext ctx;
		try {
			ctx = new InitialContext();
			IEjbGetDecoder decoder = (IEjbGetDecoder) ctx.lookup(SERVICE_NAMES.JNDI_DECODER);
			String pswd1 = decoder.decodePassword(pswd);
			if (pswd1!=null&&pswd1.equalsIgnoreCase(userPSWD))
				return true;
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return false;
	}
	//TODO use TIME_FIELD and EXPIRED in custom annotation
	//@Override
	//@Schedule(second="25,50",minute="*",hour="*",persistent=false)
	public void autoDo() {
		Calendar c = Calendar.getInstance();
		Integer curTime = c.get(TIME_FIELD);		
		System.out.println("autoDo: curTime="+curTime+" tmark="+monitor.getTmark()+" sessions="+monitor.getSessions());
		for (Iterator iterator = monitor.getTmark().keySet().iterator(); iterator.hasNext();) {
			//use ConcurrentHashMap for avoiding null pointer at previouse line
			Integer id = (Integer) iterator.next();
			if (getTime(id)!=null){
				System.out.println("autoDo: id="+id+" curTime-tmark.get(id)="+(curTime-getTime(id)));
				if (curTime-getTime(id)>EXPIRED||curTime-getTime(id)<-EXPIRED){// mod
					destroy(id);
					System.out.println("destroy session id="+id);
				}
			}else
				System.out.println("getTime(id)==null id="+id+" size="+monitor.getSize());
		}
	}
	public void autoDoTest(){
		Calendar c = Calendar.getInstance();
		Integer curTime = c.get(TIME_FIELD);		
		System.out.println("autoDo: curTime="+curTime+" tmark="+monitorTest.getTmark()+" sessions="+monitorTest.getSessions());
		for (Iterator iterator = monitorTest.getTmark().keySet().iterator(); iterator.hasNext();) {
			//use ConcurrentHashMap for avoiding null pointer at previouse line
			Integer id = (Integer) iterator.next();
			System.out.println("id="+id+"kesySet="+ monitorTest.getTmark().keySet()+"size="+monitorTest.getSize());
			if (getTimeTest(id)==null){
				System.out.println("getTimeTest(id)==null");
				break;
			}
			System.out.println("autoDo: id="+id+" curTime-tmark.get(id)="+(curTime-getTimeTest(id)));
		}
	}
	
	public boolean isValidSessionId(int id){
		System.out.println("id="+id+" tmark.get(id)="+monitor.getTmark().get(id)+" sessions.get(id)="+monitor.getSessions().get(id));
		if (monitor.getSessions().get(id)!=null&&monitor.getTmark().get(id)!=null)
			return true;
		return false;
	}
}

class Monitor {
	// null key is not used for ConcurrentHashMap
	Map<Integer,Integer> tmark = new ConcurrentHashMap<Integer, Integer>();
	Map<Integer,Session> sessions = new ConcurrentHashMap<Integer, Session>();
	
	synchronized public void addElement(Integer id, Session session,Integer time){
		tmark.put(id, time);
		sessions.put(id, session);
	}
	public Map<Integer, Session> getSessions() {
		return sessions;
	}
	public void setSessions(Map<Integer, Session> sessions) {
		this.sessions = sessions;
	}
	public Map<Integer, Integer> getTmark() {
		return tmark;
	}
	public void setTmark(Map<Integer, Integer> tmark) {
		this.tmark = tmark;
	}
	public int getSize(){
		if (tmark.size()==sessions.size())
			return tmark.size();
		return -1;
	}
	synchronized public void remove(Integer id){
		tmark.remove(id);
		sessions.remove(id);
	}
	synchronized public void update(Integer id,Integer time){
		tmark.put(id,time);
	}
}

class MonitorTest {
	Map<Integer,Integer> tmark = new ConcurrentHashMap<Integer, Integer>();
	Map<Integer, Object> sessions = new ConcurrentHashMap<Integer, Object>();
	
	synchronized public void addElement(Integer id, Object session,Integer time){
		tmark.put(id, time);
		sessions.put(id, session);
	}
	public Map<Integer, Object> getSessions() {
		return sessions;
	}
	public void setSessions(Map<Integer, Object> sessions) {
		this.sessions = sessions;
	}
	public Map<Integer, Integer> getTmark() {
		return tmark;
	}
	public void setTmark(Map<Integer, Integer> tmark) {
		this.tmark = tmark;
	}
	public int getSize(){
		if (tmark.size()==sessions.size())
			return tmark.size();
		return -1;
	}
	synchronized public void remove(Integer id){
		tmark.remove(id);
		sessions.remove(id);
	}
	synchronized public void update(Integer id,Integer time){
		tmark.put(id,time);
	}
}
