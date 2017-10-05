package model;

import java.util.Iterator;
import controller.dto.Dto;

public class Session{
	static String CONDRATIEVSPATCH="COMPLECTASYA-2";
	Dto dto;
	Chain chain;
	Manager manager;
	
	public Session(Manager m,Chain chain){
		manager = m;
		dto = new Dto();
		this.chain = chain;
		if (chain.getLogin().iterator().hasNext()){
			dto.name = chain.getLogin().iterator().next();
			manager.addUser(dto.name,dto.password);
			chain.initUmrChain();
			chain.initRevisorro();
			
			chain.makeUnique(IResultJoiner.UNIQUE_FIELD);
			chain.sortPojos(IResultJoiner.SORTED_FIELD);
			
			Integer ord = initDto(chain.getUpdater().iterator(),
					chain.getAppsSrv().iterator(),
					chain.getAppsName().iterator(),
					chain.getSubSystem().iterator(),
					chain.getMarker().iterator(),
					0);			
			
			System.out.println("msys apps:"+chain.getAppsName());
			System.out.println("umr apps:"+chain.getUmrAppsName());
			System.out.println("apps "+dto.apps);
		}else
			System.out.println(""+getClass()+" no such name");
	}
	private Integer initDto(Iterator<String> iupDir,Iterator<String> isrv,
			Iterator<String> iupName,Iterator<String> isubSys,Iterator<String> imark,Integer ord){
		for (Iterator<String> iterator = iupName; iterator.hasNext();) {
			ord++;
			String srv=null;
			if (isrv.hasNext())
				srv = (String)isrv.next();
			else
				System.out.println("isrv.size must be the same with chain.getAppsName()");
			String n = (String) iterator.next();// + " SRV:"+srv;
			dto.apps.put(ord.toString(), n);
			String updater=null;
			if (iupDir.hasNext()){
				updater = (String)iupDir.next();
				if (updater.indexOf(CONDRATIEVSPATCH)!=-1)
					updater = updater.replace(CONDRATIEVSPATCH, "App");
				dto.updater.put(ord.toString(), updater);
			}else
				System.out.println("iupDir.size must be the same with chain.getAppsName()");
			dto.invApps.put(n, ord.toString());
			dto.srV.put(ord.toString(), srv);
			if (isubSys.hasNext())
				dto.subsystem.put(ord.toString(), (String)isubSys.next());
			else
				System.out.println("isubSys.size must be the same with chain.getAppsName()");
			dto.marksystem.put(ord.toString(), (String)imark.next());
		}
		return ord;
	}
	private Chain patchComplectacia(Chain chain){
		StringBuffer sb = new StringBuffer("COMPLECTASYA-2");
		for (int i = 0; i < chain.getUpdater().size(); i++) {
			if (chain.getUpdater().get(i).indexOf("COMPLECTASYA-2")!=-1){
				String s = chain.getUpdater().get(i).replace("COMPLECTASYA-2", "App");
				chain.getUpdater().set(i, s);
				System.out.println("replace COMPLECTASYA-2"+" "+s);
			}
		}
		return chain;
	}
	public Chain getChain() {
		return chain;
	}
	public Dto getDto() {
		return dto;
	}
	//TODO remove it 
	public void setPassword(String password) {
		dto.password = password;		
	}
}