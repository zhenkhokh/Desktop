package model;

import javax.ejb.Remote;

@Remote
public interface ITimer {
	public void autoDo();
	public int init(Chain chain);
	public void destroy(int id);
	Session getSession(int id);
	Integer getTime(int id);
	void addUser(String name,String pass);
	boolean isValidUser(String loginName,String pass);
}
