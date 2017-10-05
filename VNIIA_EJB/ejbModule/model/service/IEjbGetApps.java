package model.service;

import java.util.ArrayList;

import javax.ejb.Remote;

import model.umr.VIndustrialPgms;

@Remote
public interface IEjbGetApps { 
	ArrayList<VIndustrialPgms> getApps(String pgmId);
}
