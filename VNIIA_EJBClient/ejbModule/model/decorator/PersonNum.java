package model.decorator;

import java.util.Set;

import javax.resource.NotSupportedException;

import model.command.Command;
import model.command.PersonNumCmd;

public class PersonNum extends Decorator {
	Decorator<?> core;	
	
	public PersonNum(Decorator<?> core) {
		this.core = core;
	}
	@Override
	public void find(Command command) throws NotSupportedException {
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
	@Override
	public Set getCoreSet() {
		System.out.println(""+getClass()+" core.set is "+core.getSet());
		return core.getSet();
	}
	@Override
	public Command getCommand() {
		return new PersonNumCmd();
	}
	@Override
	public void setCoreSet(Set set) {
		core.setSet(set);
	}
}
