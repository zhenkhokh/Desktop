package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.all.VPersonal;
import model.command.AppsCmd;
import model.command.revisorro.AppsCmd_;
import model.decorator.Name;
import model.decorator.revisorro.UMRApps_;
import model.umr.VIndustrialPgms;

public class Revisorro implements IResultJoiner{
	UMRApps_ apps;
	public Revisorro(Name src){
		apps = new UMRApps_(src);
		apps.fName = "admId";
	}
	public ArrayList<String> getAppsName(){
		apps.fName="pgm";
		return apps.getResult();
	}
	public ArrayList<String> getUpdater(){
		apps.fName = "ticketName";
		ArrayList<String> ticket = apps.getResult();		
		apps.fName = "exeName";
		ArrayList<String> name = apps.getResult();
		return Chain.getUpdater(ticket, name);		
	}	
	public ArrayList<String> getAppsSrv() {
		apps.fName="srvName";
		return apps.getResult();
	}
	public ArrayList<String> getSubSystem(){
		apps.fName="subsystem";
		return apps.getResult();
	}
	public void init(){
		apps.fName="admId";
		try{apps.find(new AppsCmd_());}catch (NotSupportedException e){ System.out.println(""+getClass()+" apps cant init chain");}
	}
	@Override
	public ArrayList<VIndustrialPgms> getAppPojos() {
		return apps.getPojos();
	}
	@Override
	public void joinAppPojos(ArrayList<VIndustrialPgms> pojos) {
		apps.joinPojos(pojos, IResultJoiner.SORTED_FIELD);
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
