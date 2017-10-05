package model.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import model.RequestBuilder;
import model.msysAdmin.VApps;
import model.umr.VPgm;

@Stateless(name="pgm")
public class GetPgmSrv extends Reciver<BigInteger, VPgm> implements IEjbGetPgm{
	public static String fName="pgmId";
	public GetPgmSrv(){
		NAME=fName;
		setFactoryName(umrFactoryName);
		setNativeQuery("select data_Exchange.PGM_1(?p) from dual");
	}
	@Override
	public ArrayList<VPgm> getPgm(String pgmId) {
		ArrayList<VPgm> pojos = new ArrayList<VPgm>();		
    	RequestBuilder<VPgm> rb = new RequestBuilder<VPgm>();
    	String numbers = (String) templateNativeAction(pgmId).get(0);
    	if (numbers!=null&&!numbers.equalsIgnoreCase("null")){
    		numbers = numbers.replace(" ", "");    	
	    	for (Iterator<?> iterator =  Arrays.asList(numbers.split(",")).iterator();  iterator.hasNext();) {
				String pgmId1 = (String) iterator.next();
				VPgm pojo = rb.makeEntity(NAME, new BigInteger(pgmId1), new VPgm());
				pojos.add(pojo);
				System.out.println("From PgmSrv: "+pojo.getPgmId());
			}
    	}else
    		System.out.println("From PgmSrv: null");
    	return pojos;
	}
}
