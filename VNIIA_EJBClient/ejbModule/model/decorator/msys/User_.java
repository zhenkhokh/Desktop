package model.decorator.msys;

import model.command.msys.UserCmd_;
import model.decorator.Decorator;
import model.decorator.User;

public class User_ extends User {

	public User_(Decorator<?> core) {
		super(core);
	}
	@Override
	public UserCmd_ getCommand(){
		return new UserCmd_();
	}
}
