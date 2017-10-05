/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import model.RequestBuilder;
import model.msysAdmin.VUsers;

import javax.ejb.Stateless;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 *
 * @author 35-khei
 * define some actions and call them at Commands execute implementation
 * note that single service method can be used per one reciver
 */
public class Reciver<T,E> {
	public static final String defaultFactoryName="SKY";
	public static final String umrFactoryName="OS2";
	public static final String msysFactoryName="FOS";
	public static final String testFactoryName="TEST";
	//final EntityManagerFactory defaultFactory = Persistence.createEntityManagerFactory(defaultFactoryName);
	//final EntityManagerFactory factoryOS2 =  Persistence.createEntityManagerFactory(umrFactoryName);
	protected String factoryName=defaultFactoryName;
	protected String NAME=null;// must be initial in children
	protected String nativeQuery=null;
	/**
	 * add some code here or define create a new single service method in children.
	 * delegate it in Commmand.exececute method
	 */
    public void action(){}
    /**
     * use for main services, NAME and factory must be initialized  
     * 
     * @param value - is field value
     * @return pojos list
     */
    protected ArrayList<E> templateAction(T value,E entity) throws IllegalArgumentException{    	    	
    	EntityManager em;
    	System.out.println("Factory is "+factoryName);    	
    	em = (EntityManager) Persistence.createEntityManagerFactory(factoryName).createEntityManager();
    	RequestBuilder<E> rb = new RequestBuilder<E>();
    	System.out.println(NAME+" is "+value);
    	E pojo = null;
    	if (value instanceof String){
    		pojo = rb.makeEntity(NAME, (String)value, entity);
    	} else if (value instanceof BigInteger){
    		pojo = rb.makeEntity(NAME, (BigInteger)value, entity);
    	} else
    		new IllegalArgumentException();
    	System.out.println("pojo is "+rb.printEntity(pojo));
    	Query q;
    	System.out.println("query is "+rb.makeQuery(NAME, pojo));
    	q = em.createQuery(rb.makeQuery(NAME, pojo)).setParameter("param1", value);
    	System.out.println("query result1 is "+q.getResultList());
    	//q = em.createNamedQuery("VUsers.findByUserLogin");
    	//q.setParameter(NAME, userLogin);
    	//System.out.println("query result2 is "+q.getResultList());
    	ArrayList<E> pojos = new ArrayList<E>(q.getResultList());
    	//for (Iterator<T> iterator = q.getResultList().iterator(); iterator.hasNext();) {
		//	pojo = (E) iterator.next();
		//	pojos.add(pojo);
		//}
    	em.close();
    	return pojos;
    }
    protected ArrayList<String> templateNativeAction(String value) throws IllegalArgumentException{
    	EntityManager em;
    	System.out.println("Factory is "+factoryName);
    	em = (EntityManager) Persistence.createEntityManagerFactory(factoryName).createEntityManager();
    	System.out.println(NAME+" is "+value);
    	Query q=(Query) em.createNativeQuery(nativeQuery).setParameter("p", value);
    	//Query q=(Query) em.createNativeQuery(nativeQuery);
    	System.out.println("query result is "+q.getResultList());
    	ArrayList<String> result = new ArrayList<String>(q.getResultList());
    	em.close();
    	return result;
    }
    //use in default constructor only, externel call has no effect
    protected void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
    protected String getFactoryName() {
		return factoryName;
	}
    protected void setNativeQuery(String nativeQuery) {
		this.nativeQuery = nativeQuery;
	}
    protected String getNativeQuery() {
		return nativeQuery;
	}
}
