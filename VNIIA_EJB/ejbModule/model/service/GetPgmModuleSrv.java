package model.service;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.Stateless;

import model.umr.PgmModule;

//import data_exchange.PgmModule;

@Stateless(name="moduleApp")
public class GetPgmModuleSrv extends Reciver<BigInteger, PgmModule> implements IEjbGetPgmModule{
	public static String fName = "moduleId";
	public GetPgmModuleSrv() {
		NAME=fName;
		setFactoryName(umrFactoryName);
	}
	@Override
	public ArrayList<PgmModule> getModuleApp(String moduleId) {
		return templateAction(new BigInteger(moduleId), new PgmModule());
	}
}
