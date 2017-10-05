package model.service;

import java.util.ArrayList;

import javax.ejb.Remote;
// do not deploy in Decorator
@Remote
public interface IEjbGetDecoder {
	String decodePassword(String pswd);
}
