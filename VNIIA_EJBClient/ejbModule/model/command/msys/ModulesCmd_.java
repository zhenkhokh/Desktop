package model.command.msys;

import model.command.ModulesCmd;
import model.decorator.SERVICE_NAMES;

public class ModulesCmd_ extends ModulesCmd {
	public ModulesCmd_(){
		super(SERVICE_NAMES.JNDI_MODULES_);
	}
}
