package model.command.msys;

import model.command.UserCmd;
import model.decorator.SERVICE_NAMES;

public class UserCmd_ extends UserCmd {
	public UserCmd_(){
		super(SERVICE_NAMES.JNDI_USER_);
	}
}
