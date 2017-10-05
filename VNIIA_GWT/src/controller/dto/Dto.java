package controller.dto;

import java.io.Serializable;
import java.math.BigInteger;

import com.google.gwt.user.client.rpc.IsSerializable;

import controller.action.ACTION;

import java.util.ArrayList;
import java.util.HashMap;

public class Dto implements Serializable, IsSerializable{
	public String name;									// go to RMI
	public String password;
	public String srv;									 
	public String xmlName;
	public String domain;
	public ACTION action;								// install,update or execute
	public Integer id;									// from session for viewer
	public HashMap<String, String> apps = new HashMap<String, String>();				// model: <userKey,appsName>
	public HashMap<String, String> invApps = new HashMap<String, String>();				// <appsName,userKey>
	public HashMap<String, String> updater = new HashMap<String, String>();				// <userKey,dataToRMI>
	public HashMap<String, String> srV = new HashMap<String, String>();					// <userKey,dataToRMI>	
	public HashMap<String, String> subsystem = new HashMap<String, String>();			// sorted <userKey,subsystem>
	public HashMap<String, String> marksystem = new HashMap<String, String>();			// sorted <userKey,marker>
}