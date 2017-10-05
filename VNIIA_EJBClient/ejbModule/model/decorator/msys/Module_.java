package model.decorator.msys;

import model.command.msys.ModulesCmd_;
import model.decorator.Decorator;
import model.decorator.Module;

public class Module_ extends Module {

	public Module_(Decorator<?> core) {
		super(core);
	}
	@Override
	public ModulesCmd_ getCommand(){
		return new ModulesCmd_();
	}
}
