package model;

import java.util.Set;

import javax.resource.NotSupportedException;

import model.command.msys.PersonNumCmd_;
import model.decorator.Module;
import model.decorator.Name;
import model.decorator.PgmModule;
import model.decorator.User;
import model.decorator.msys.Module_;
import model.decorator.msys.PersonNum_;
import model.decorator.msys.User_;

public class MsysChain_ extends MsysChain implements IMsys_{
	//User_ user;
	//Module_ module;
	//PersonNum_ personNum;

	public MsysChain_(Name name) {
		super();
		_MsysChain(name);
		configure();
	}
	private void _MsysChain(Name name){
		_MsysChain(new User_(name));
		super.name = name;
	}
	private void _MsysChain(User_ user){
		_MsysChain(new Module_(user));
		super.user = user;
		super.personNum = new PersonNum_(user);
	}
	private void _MsysChain(Module_ module){
		_MsysChain(new PgmModule(module));
		super.module = module;
	}
}
