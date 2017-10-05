/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.Stateless;
import model.msysAdmin.VMenu;

/**
 *
 * @author 35-khei
 */
@Stateless(name="modules")
public class GetModulesSrv extends Reciver<BigInteger,VMenu> implements IEjbGetModules {
	public static String fName = "userId";
	public GetModulesSrv(){
		this(defaultFactoryName);
		NAME=fName;
	}
	protected GetModulesSrv(String factoryName){
		setFactoryName(factoryName);
	}
	public ArrayList<VMenu> getModules(String userId){
    	return templateAction(new BigInteger(userId), new VMenu());
    }
}
