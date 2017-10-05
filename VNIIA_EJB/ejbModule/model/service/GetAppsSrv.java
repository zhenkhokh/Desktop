package model.service;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.umr.VIndustrialPgms;
@Stateless(name="apps")
public class GetAppsSrv extends Reciver<BigInteger, VIndustrialPgms> implements IEjbGetApps {
    	public static String fName = "pgmId";
	public GetAppsSrv(){
		NAME=fName;
		setFactoryName(umrFactoryName);
	}
	@Override
	public ArrayList<VIndustrialPgms> getApps(String pgmId) {
		return templateAction(new BigInteger(pgmId), new VIndustrialPgms());
	}

}
