package model;

import java.util.ArrayList;
import java.util.Set;

import model.all.VPersonal;
import model.decorator.UmrPgm;
import model.decorator.User;
import model.msysAdmin.VUsers;
import model.umr.VIndustrialPgms;

public interface IMsys extends IMsys_,IResultJoiner{
	Set<String> getPassword();
	Set<String> getLogin();
	//Set<String> getAppsDir();
	ArrayList<String> getAppsName();
	//Set<String> getAppsSrv();
	ArrayList<String> getAppsSrv();
	ArrayList<String> getUpdater();
	void initMsysChain();
	void initPesonalNumber();
		
	ArrayList<VPersonal> getPersonalPojos();
	void joinPersonalPojos(ArrayList<VPersonal> pojos);
}
