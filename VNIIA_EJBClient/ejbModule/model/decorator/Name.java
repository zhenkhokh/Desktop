package model.decorator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.command.Command;

public class Name extends Decorator<Object> {
	public String login=EMPTY;
	@Override
	public void find(Command<?> command) throws NotSupportedException {
		System.out.println("Hello from "+getClass());
		if (login!=null&&!login.equalsIgnoreCase(EMPTY))
			super.set.add(login);
		else
			System.out.println("login must be set");
		throw new NotSupportedException();
	}
	@Override
	public Set<String> getCoreSet() {
		return null;
	}
	public Set<String> getSet() {
		if (login!=null){
			if(!login.equalsIgnoreCase(EMPTY))
				super.set.add(login);
			return set;
		}
		System.out.println("login must be set");
		return null;
	}
	@Override
	public Command<ArrayList<Object>> getCommand() {
		return null;
	}

	@Override
	public void refresh() throws NotSupportedException {
		throw new NotSupportedException();
	}
	@Override
	public void setCoreSet(Set set) {
		//do nothing
	}
}
