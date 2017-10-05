package model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;

//import com.google.gwt.user.client.rpc.IsSerializable;

import model.all.VPersonal;
import model.msysAdmin.VUsers;
import model.umr.VIndustrialPgms;
//import com.google.gwt.user.client.rpc.IsSerializable;


public interface IResultJoiner /*extends Serializable, IsSerializable do compilation problems*/{
	final BigInteger UMR_MARK = new BigInteger("1");
	final BigInteger MSYS_MARK = new BigInteger("2");
	final BigInteger OTHERS_MARK = new BigInteger("3");
	final String SORTED_FIELD="subsystem";
	final String UNIQUE_FIELD="pgm";
	final String MARK_FIELD="admId";
	ArrayList<VIndustrialPgms> getAppPojos();
	void setAppPojos(ArrayList<VIndustrialPgms> pojos);
	void joinAppPojos(ArrayList<VIndustrialPgms> pojos);
	ArrayList<String> getSubSystem();
	ArrayList<String> getMarker();
	void makeUnique(String fName);
	void sortPojos(String fName);
	//ArrayList<VUsers> getUserPojos();
	//void joinUserPojos(ArrayList<VUsers> pojos);
}
