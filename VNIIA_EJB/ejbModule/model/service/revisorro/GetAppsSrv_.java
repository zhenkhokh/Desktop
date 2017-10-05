package model.service.revisorro;

import javax.ejb.Stateless;

import model.service.GetAppsSrv;
import model.service.IEjbGetApps;

@Stateless(name="apps_")
public class GetAppsSrv_ extends GetAppsSrv implements IEjbGetApps {
	public GetAppsSrv_() {
		super();
		NAME="admId";
	}
}
