package model.service;

import java.util.ArrayList;

import javax.ejb.Stateless;

@Stateless(name="decoder")
public class GetDecoderSrv extends Reciver<String, Object> implements IEjbGetDecoder{
	public GetDecoderSrv(){
		NAME = "decorder";
		setNativeQuery("select msys_admin.get_decode_password(?p) from dual");
	}
	@Override
	public String decodePassword(String pswd) {
		//setNativeQuery("select msys_admin.get_decode_password('"+pswd+"') from dual");		
		ArrayList<String> out = templateNativeAction(pswd);
		if (out!=null)
			if (out.size()==1&&!out.get(0).isEmpty())
				return out.get(0);
			else
				System.out.println("from decodePassword:"+"out.size()==1&&!out.get(0).isEmpty() is false, out.size() is "+out.size());
		return null;
	}
}
