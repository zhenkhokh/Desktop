package model.service.msys;

import java.util.ArrayList;

import javax.ejb.Stateless;

import model.msysAdmin.VMenu;
import model.service.GetPersonalSrv;
import model.service.IEjbGetPerson;

@Stateless(name="personNum_")
public class GetPersonalSrv_ extends GetPersonalSrv implements IEjbGetPerson{
	public GetPersonalSrv_() {
		super(msysFactoryName);
		NAME=fName;
	}
}
