package model.service.msys;

import model.service.GetModulesSrv;
import model.service.IEjbGetModules;

import javax.ejb.Stateless;

@Stateless(name="modules_")
public class GetModulesSrv_ extends GetModulesSrv implements IEjbGetModules{
	public GetModulesSrv_() {
		super(msysFactoryName);
		NAME=fName;
	}
}
