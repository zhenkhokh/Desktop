package model.service.msys;

import model.service.GetUserSrv;
import model.service.IEjbGetUser;

import javax.ejb.Stateless;

@Stateless(name="user_")
public class GetUserSrv_ extends GetUserSrv implements IEjbGetUser{
	public GetUserSrv_(){
		super(msysFactoryName);
		NAME=fName;
	}
}
