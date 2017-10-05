package model.service;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.all.VPersonal;
@Stateless(name="personNum")
public class GetPersonalSrv extends Reciver<BigInteger,VPersonal> implements IEjbGetPerson {
      	public static String fName = "personalId";

	public GetPersonalSrv(){
		this(defaultFactoryName);
		super.NAME = fName;
	}
	protected GetPersonalSrv(String msysfactoryname) {
		setFactoryName(msysfactoryname);
	}
	@Override
	public ArrayList<VPersonal> getPersonNumber(String personalId) {
		System.out.println("personalId is "+personalId);
		return templateAction(new BigInteger(personalId), new VPersonal());
	}
}
