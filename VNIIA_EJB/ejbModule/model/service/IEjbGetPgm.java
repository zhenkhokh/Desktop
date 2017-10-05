package model.service;

import java.util.ArrayList;

import javax.ejb.Remote;

import model.umr.VPgm;

@Remote
public interface IEjbGetPgm {
	ArrayList<VPgm> getPgm(String pgmId);
}
