package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.all.VPersonal;
import model.command.PgmCmd;
import model.command.AppsCmd;
import model.decorator.Component;
import model.decorator.Name;
import model.decorator.PersonNum;
import model.decorator.UMRApps;
import model.decorator.UmrPgm;
import model.decorator.User;
import model.msysAdmin.VUsers;
import model.service.GetPersonalSrv;
import model.service.GetPgmSrv;
import model.service.GetAppsSrv;
import model.umr.VIndustrialPgms;
/**
 * call constructor after first initChain for MSYS admin, because user call  setCoreSet
 * 
 * @author zheka
 *
 */
public class UmrChain implements IUmr{
	//PersonNum personNum; //go to msys
	Name name;// = new Name();
	UmrPgm umrPgm;
	UMRApps umrApps;
	public UmrChain(Name name){
	  this.name = name;	  
	  //personNum = new PersonNum(name);
	  umrPgm = new UmrPgm(name);
	  umrApps = new UMRApps(umrPgm);
	  
	  ////this.user.setCoreSet(new HashSet<String>());
	  ////this.user.fName=GetPersonalSrv.fName;
	  //personNum.fName="personalNumber";
	  umrPgm.fName=GetAppsSrv.fName;
	  umrApps.fName="pgm";
	}
	@Override
	public void initUmrChain(){
		try{umrApps.find(new AppsCmd());}catch (NotSupportedException e){}
		//try{umrPgm.find(new PgmCmd());}catch (NotSupportedException e){}
	}
	public ArrayList<String> getNumber(){
		//personNum.fName="personalNumber";
		//return personNum.getSet();
		name.login=Component.EMPTY;
		return name.getResult();
	}
	public ArrayList<String> getUMRUpdater(){
		umrApps.fName = "ticketName";
		ArrayList<String> ticket = umrApps.getResult();		
		umrApps.fName = "exeName";
		ArrayList<String> name = umrApps.getResult();
		return Chain.getUpdater(ticket, name);
	}
	public ArrayList<String> getUmrAppsName(){
		umrApps.fName="pgm";
		return umrApps.getResult();
	}
	@Override
	public ArrayList<String> getPgms() {
		return umrPgm.getResult();
	}
	@Override
	public ArrayList<String> getUmrSrv() {
		umrApps.fName="srvName";
		return umrApps.getResult();
	}
	@Override
	public ArrayList<VIndustrialPgms> getAppPojos() {
		return umrApps.getPojos();
	}
	@Override
	public void joinAppPojos(ArrayList<VIndustrialPgms> pojos) {
		umrApps.joinPojos(pojos, IResultJoiner.SORTED_FIELD);
	}
	/**
	 * do not use
	 **/
	@Override
	public ArrayList<String> getSubSystem() {
		return null;
	}
	@Override
	public void makeUnique(String fName) {
		umrApps.makeUnique(fName);
	}
	@Override
	public void sortPojos(String fName) {
		umrApps.sortPojos(fName);
	}
	@Override
	public void setAppPojos(ArrayList<VIndustrialPgms> pojos) {
		umrApps.setPojos(pojos);
	}
	@Override
	public ArrayList<String> getMarker() {
		umrApps.fName=IResultJoiner.MARK_FIELD;
		return umrApps.getResult();
	}
}