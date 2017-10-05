package model;

import java.util.ArrayList;

public interface IUmr extends IResultJoiner{
	ArrayList<String> getUmrAppsName();
	ArrayList<String> getUMRUpdater();
	ArrayList<String> getNumber();
	ArrayList<String> getPgms();
	ArrayList<String> getUmrSrv();
	
	void initUmrChain();
}
