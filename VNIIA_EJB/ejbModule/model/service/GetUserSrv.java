/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.RequestBuilder;
import model.msysAdmin.VUsers;

/**
 *
 * @author 35-khei
 */
@Stateless(name="user")
public class GetUserSrv extends Reciver<String,VUsers> implements IEjbGetUser{
	public static String fName = "userLogin";
	public GetUserSrv(){
		this(defaultFactoryName);
		NAME=fName;
	}
	protected GetUserSrv(String factoryName){
		setFactoryName(factoryName);
	}
	@Override
	public ArrayList<VUsers> getUser(String userLogin) {
		return templateAction(userLogin, new VUsers());
	}
}
