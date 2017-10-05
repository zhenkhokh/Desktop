package model.command.msys;

import model.command.PersonNumCmd;
import model.decorator.SERVICE_NAMES;

public class PersonNumCmd_ extends PersonNumCmd {
	public PersonNumCmd_(){
		super(SERVICE_NAMES.JNDI_PERSON_);
	}
}
