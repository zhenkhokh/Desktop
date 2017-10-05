package model.service;

import java.util.ArrayList;

import javax.ejb.Remote;

import model.all.VPersonal;

@Remote
public interface IEjbGetPerson {
	ArrayList<VPersonal> getPersonNumber(String personalId);
}
