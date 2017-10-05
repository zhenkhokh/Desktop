package model.decorator;

import java.util.ArrayList;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.command.Command;
import model.command.ModulesCmd;
import model.command.UserCmd;

public class Module extends Decorator {
	Decorator<?> core;	
	
	public Module(Decorator<?> core) {
		this.core = core;
	}
	@Override
	public void find(Command command)
			throws NotSupportedException {
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
	public ModulesCmd getCommand() {
		return new ModulesCmd();
	}
	@Override
	public Set getCoreSet() {
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
	@Override
	public void setCoreSet(Set set) {
		core.setSet(set);
	}
}
