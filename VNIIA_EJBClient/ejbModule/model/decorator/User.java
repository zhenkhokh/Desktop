package model.decorator;

import java.util.ArrayList;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.command.Command;
import model.command.UserCmd;
import model.msysAdmin.VUsers;
//TODO 
public class User extends Decorator<VUsers> {
	Decorator<?> core;	
	
	public User(Decorator<?> core) {
		this.core = core;
	}
	@Override
	public void find(Command<?> command) throws NotSupportedException {
		System.out.println("Hello from "+getClass());
		try{
			core.find(core.getCommand());
		}catch (NotSupportedException e){
			if (!(core instanceof Name))
				throw new NotSupportedException();
			else
				if (((Name)core).getSet()==null)
					throw new NotSupportedException();			
		}
		findTemplate(command);
	}	
	@Override
	public UserCmd getCommand() {
		return new UserCmd();
	}
	@Override
	public Set<String> getCoreSet() {
		System.out.println(""+getClass()+" core.set is "+core.getSet());
		return core.getSet();
	}
	@Override
	public void refresh() throws NotSupportedException {
		try{
			core.refresh();
		}catch (NotSupportedException e){
			if (!(core instanceof Name))
				throw new NotSupportedException();
			else
				if (((Name)core).getSet()==null)
					throw new NotSupportedException();			
		}
		refreshComponent();
	}
	//use for pass Name
	public void setCoreSet(Set<String> set) {
		core.setSet(set);		
	}
}
