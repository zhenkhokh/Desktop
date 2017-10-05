package model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import model.all.VPersonal;
import model.decorator.Component;
import model.decorator.Name;
import model.decorator.UMRApps;
import model.decorator.UmrPgm;
import model.decorator.User;
import model.msysAdmin.VApps;
import model.msysAdmin.VUsers;
import model.umr.VIndustrialPgms;

public class Chain implements IMsys,IUmr,IChain{
	MsysChain msysChain;
	MsysChain_ msysChain_;
	UmrChain umrChain;
	Name src = new Name();
	Name node = new Name();
	Revisorro revisorro,r;
	// use for coloring, be carefull override admId!
	ArrayList<VIndustrialPgms> markAdm(BigInteger marker,ArrayList<VIndustrialPgms> apps){
		for (Iterator iterator = apps.iterator(); iterator.hasNext();) {
			VIndustrialPgms vApps = (VIndustrialPgms) iterator.next();
			if (marker==IResultJoiner.UMR_MARK||
					marker==IResultJoiner.MSYS_MARK||
					marker==IResultJoiner.OTHERS_MARK)
				vApps.setAdmId(marker);
		}
		return apps;
	}
	public Chain(String loginName){
		loginName = loginName.toUpperCase();
		src.login = loginName;
		msysChain = new MsysChain(src);
		msysChain_ = new MsysChain_(src);
		initMsysChain();
		initPesonalNumber();
			
		//Set<String> set = new HashSet<String>();
		//set.addAll(msysChain.getPesonalNumber());
		//set.addAll(msysChain_.getPesonalNumber());
		//node.setSet(set);
		
		msysChain.joinPersonalPojos(msysChain_.getPersonalPojos());
		node.setSet(msysChain.getPesonalNumber());
		node.login=Component.EMPTY;
		
		setAppPojos(markAdm(MSYS_MARK, getAppPojos()));
		}
	public void initUmrChain(){
		umrChain = new UmrChain(node);
		umrChain.initUmrChain();		
		msysChain.joinAppPojos(markAdm(IResultJoiner.UMR_MARK,umrChain.getAppPojos()));		
	}
	public void initRevisorro(){
		Name name=  new Name();
		revisorro = new Revisorro(name);
		for (int i=3;i<=9;i++){			
			name.login = String.valueOf(i);
			Revisorro r =new Revisorro(name);
			r.init();
			revisorro.joinAppPojos(r.getAppPojos());
		}
		msysChain.joinAppPojos(markAdm(IResultJoiner.OTHERS_MARK,revisorro.getAppPojos()));
	}
	public boolean isValidName(){
		return !msysChain.getUserId().isEmpty()||!msysChain_.getUserId().isEmpty();
	}
	public Name getNode(){
		return node;
	}
	@Override
	public ArrayList<String> getUmrAppsName() {
		//umrChain.joinAppPojos(null);// just sort
		return umrChain.getUmrAppsName();
	}
	@Override
	public ArrayList<String> getUMRUpdater() {
		umrChain.joinAppPojos(null);// just sort
		return umrChain.getUMRUpdater();
	}
	@Override
	public ArrayList<String> getNumber() {
		return umrChain.getNumber();
	}
	@Override
	public Set<String> getPassword() {
		//TODO delegate msysChain only
		HashSet<String> set = new HashSet<String>(msysChain.getPassword());
		set.addAll(msysChain_.getPassword());
		return set;
	}
	@Override
	public Set<String> getLogin() {
		return msysChain.getLogin();
	}
	@Override
	public Set<String> getUserId() {
		return msysChain.getUserId();
	}
	@Override
	public ArrayList<String> getUpdater() {
		return msysChain.getUpdater();
	}
	@Override
	public ArrayList<String> getAppsName() {
		return msysChain.getAppsName();
	}
	@Override
	public ArrayList<String> getPgms() {
		return umrChain.getPgms();
	}
	@Override
	public ArrayList<String> getUmrSrv() {
		return umrChain.getUMRUpdater();
	}
	@Override
	public ArrayList<String> getAppsSrv() {
		return msysChain.getAppsSrv();
	}
	@Override
	public Set<String> getPesonalId() {		
		return msysChain.getPesonalId();
	}
	@Override
	public Set<String> getPesonalNumber() {
		return msysChain.getPesonalNumber();
	}
	@Override
	public void initPesonalNumber() {
		msysChain.initPesonalNumber();
		msysChain_.initPesonalNumber();
	}
	@Override
	public void initMsysChain() {
		msysChain.initMsysChain();
		msysChain_.initMsysChain();
		msysChain.joinAppPojos(msysChain_.getAppPojos());
		//msysChain.joinUserPojos(msysChain_.getUserPojos());
	}
	@Override
	public ArrayList<VIndustrialPgms> getAppPojos() {
		return msysChain.getAppPojos();
	}
	@Override
	public void joinAppPojos(ArrayList<VIndustrialPgms> pojos) {
		msysChain.joinAppPojos(pojos);		
	}
	@Override
	public ArrayList<VPersonal> getPersonalPojos() {
		return msysChain.getPersonalPojos();
	}
	@Override
	public void joinPersonalPojos(ArrayList<VPersonal> pojos) {
		msysChain.joinPersonalPojos(pojos);
		
	}/*
	@Override
	public ArrayList<VUsers> getUserPojos() {
		return msysChain.getUserPojos();
	}
	@Override
	public void joinUserPojos(ArrayList<VUsers> pojos) {
		msysChain.joinUserPojos(pojos);	
	}*/
	@Override
	public ArrayList<String> getAppsName_() {
		return msysChain_.getAppsName();
	}
	@Override
	public ArrayList<String> getAppsSrv_() {
		return msysChain_.getAppsSrv();
	}
	@Override
	public ArrayList<String> getAppsDir_() {
		return msysChain_.getUpdater();
	}
	@Override
	public ArrayList<String> getSubSystem() {
		return msysChain.getSubSystem();
	}
	@Override
	public void makeUnique(String fName) {
		msysChain.makeUnique(fName);
	}
	@Override
	public void sortPojos(String fName) {
		msysChain.sortPojos(fName);
	}
	@Override
	public void setAppPojos(ArrayList<VIndustrialPgms> pojos) {
		msysChain.setAppPojos(pojos);
	}
	@Override
	public ArrayList<String> getMarker() {
		return msysChain.getMarker();
	}
	static public Set<String> getUpdater(Set<String> ticket,Set<String> name){
		Set<String> updater = new HashSet<>();
		Iterator i2 = name.iterator();
		for (Iterator iterator = ticket.iterator(); iterator.hasNext();) {
			String s1 = (String) iterator.next();
			String s2 = (String) i2.next();
			updater.add(s1+"_"+s2);
		}
		return updater;
	}
	static public ArrayList<String> getUpdater(ArrayList<String> ticket,ArrayList<String> name){
		ArrayList<String> updater = new ArrayList<>();
		Iterator i2 = name.iterator();
		for (Iterator iterator = ticket.iterator(); iterator.hasNext();) {
			String s1 = (String) iterator.next();
			String s2 = (String) i2.next();
			updater.add(s1+"_"+s2);
		}
		return updater;
	}
}
