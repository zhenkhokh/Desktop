package model;

import java.util.ArrayList;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.all.VPersonal;
import model.command.AppsCmd;
import model.command.PersonNumCmd;
import model.decorator.Component;
import model.decorator.PersonNum;
import model.decorator.UMRApps;
import model.decorator.UmrPgm;
import model.decorator.Module;
import model.decorator.PgmModule;
import model.decorator.Name;
import model.decorator.User;
import model.decorator.msys.PersonNum_;
import model.msysAdmin.VModules;
import model.msysAdmin.VUsers;
import model.service.GetAppsSrv;
import model.service.GetModulesSrv;
import model.service.GetPgmSrv;
import model.umr.VIndustrialPgms;

public class MsysChain implements IMsys{
	Name name;
	Component<VUsers> user;
	Component<VModules> module;
	PgmModule pgmModule;
	UMRApps apps;
	Component<VPersonal> personNum;
	
	public MsysChain(Name name){
		this();
		_MsysChain(name);
		configure();
	}
	
	protected MsysChain(){}
	
	protected void configure(){
		apps = new UMRApps(pgmModule);
		user.fName=GetModulesSrv.fName;
		module.fName= "moduleId";
		pgmModule.fName = GetAppsSrv.fName;
		apps.fName="pgm";
		personNum.fName="personalNumber";
	}
	/**
	 * such method must be overloaded
	 * @param name
	 */
	private void _MsysChain(Name name){
		_MsysChain(new User(name));
		this.name = name;
	}
	private void _MsysChain(User user){
		_MsysChain(new Module(user));
		this.user = user;
		personNum = new PersonNum(user);
	}
	private void _MsysChain(Module module){
		_MsysChain(new PgmModule(module));
		this.module = module;
	}
	protected void _MsysChain(PgmModule pgmModule){		
		this.pgmModule = pgmModule;
	}
	@Override
	public void initMsysChain(){
	  try{apps.find(new AppsCmd());}catch (NotSupportedException e){ System.out.println(""+getClass()+" apps cant init chain");}
	}
	@Override
	public void initPesonalNumber() {
//		Name name = new Name();
//		name.login="";
//		if (user.getSet().iterator().hasNext())
//			name.login = user.getSet().iterator().next();
//		name.setSet(user.getSet());
//		System.out.println("---------"+user.getSet());
//		personNum = new PersonNum(name);
		user.fName="personalId";
		try{personNum.find(new PersonNumCmd());}catch (NotSupportedException e){ System.out.println(""+getClass()+" personalNum cant init chain");}
	}
	@Override
	public ArrayList<String> getAppsName(){
		apps.fName = "pgm";
		return apps.getResult();
	}
	@Override
	public ArrayList<String> getUpdater(){
		apps.fName = "ticketName";
		ArrayList<String> ticket = apps.getResult();
		apps.fName = "exeName";
		ArrayList<String> name = apps.getResult();
		return Chain.getUpdater(ticket, name);
	}
	@Override
	public Set<String> getUserId(){
		user.fName = "userId";
		return user.getSet();
	}
	@Override
	public Set<String> getLogin(){
		user.fName = "userLogin";
		return user.getSet();
	}
	@Override
	public Set<String> getPassword(){
		user.fName = "codePass";
		return user.getSet();
	}
	@Override
	public ArrayList<String> getAppsSrv() {
		apps.fName = "srvName";
		return apps.getResult();
	}
	@Override
	public Set<String> getPesonalId() {
		user.fName = "personalId";
		return user.getSet();
	}
	@Override
	public Set<String> getPesonalNumber() {
		personNum.fName = "personalNumber";
		return personNum.getSet();
	}

	@Override
	public ArrayList<VIndustrialPgms> getAppPojos() {
		return apps.getPojos();
	}

	@Override
	public void joinAppPojos(ArrayList<VIndustrialPgms> pojos) {
		this.apps.joinPojos(pojos, IResultJoiner.SORTED_FIELD);
	}

	@Override
	public ArrayList<VPersonal> getPersonalPojos() {
		return personNum.getPojos();
	}
	@Override
	public void joinPersonalPojos(ArrayList<VPersonal> pojos) {
		this.personNum.joinPojos(pojos, "personalId");
	}
/*
	@Override
	public ArrayList<VUsers> getUserPojos() {
		return user.getPojos();
	}

	@Override
	public void joinUserPojos(ArrayList<VUsers> pojos) {
		user.joinUniqPojos(pojos, "userId");
	}
	*/

	@Override
	public ArrayList<String> getSubSystem() {
		apps.fName=IResultJoiner.SORTED_FIELD;
		return apps.getResult();
	}
	@Override
	public void makeUnique(String fName) {
		apps.makeUnique(fName);
	}

	@Override
	public void sortPojos(String fName) {
		apps.sortPojos(fName);
	}

	@Override
	public void setAppPojos(ArrayList<VIndustrialPgms> pojos) {
		apps.setPojos(pojos);
	}

	@Override
	public ArrayList<String> getMarker() {
		apps.fName=IResultJoiner.MARK_FIELD;
		return apps.getResult();
	}
}
