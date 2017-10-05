package model.decorator.msys;

import model.command.msys.PersonNumCmd_;
import model.decorator.Decorator;
import model.decorator.PersonNum;

public class PersonNum_ extends PersonNum {

	public PersonNum_(Decorator<?> core) {
		super(core);
	}
	@Override
	public PersonNumCmd_ getCommand(){
		return new PersonNumCmd_();
	}
}
