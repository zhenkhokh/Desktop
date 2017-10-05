/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.decorator;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import model.RequestBuilder;

/**
 *
 * @author 35-khei
 */
public class Util<E> {
	final int wLen = 8;//16;
	final int iLen = Integer.SIZE-9; 
	final int shift = 1;
	Collator collator = Collator.getInstance(new Locale("ru_RU"));
	final RequestBuilder<E> rb = new RequestBuilder<E>();

	public ArrayList<String> getFields(ArrayList<E> pojos,String fieldName){
    	ArrayList<String> fields = new ArrayList<String>();
    	RequestBuilder<E> rb = new RequestBuilder<E>();
    	for (Iterator<E> iterator = pojos.iterator(); iterator.hasNext();) {
			E pojo= (E) iterator.next();
			fields.add(rb.getValue(fieldName, pojo));
		}
        return fields;
	}
	public ArrayList<E> getSortedPojos(ArrayList<E> pojos,final String fName){
		//sort
		pojos.sort(new Comparator<E>() {
			@Override
			public int compare(E o1, E o2) {
				String s1=null,s2=null;
				if (o1!=null||o2!=null){
					s1 = rb.getValue(fName, o1);
					s2 = rb.getValue(fName, o2);
				}
				//return s1.hashCode()-s2.hashCode();
				//return getStringHash(s1)-getStringHash(s2);
				if (s1!=null)
					return collator.compare(s1, s2);
				else
					return 0;
			}
		});
		return pojos;
	}
	public ArrayList<E> makeUnique(ArrayList<E> pojos,String fName){
	    E tmp=null;
	    RequestBuilder<E> rb = new RequestBuilder<E>();
	    for (int j = 0; ;) {
	    	if(j>=pojos.size())
	  			break;
	    	String next = rb.getValue(fName, pojos.get(j));
	  		if (j>0&&rb.getValue(fName, tmp).equalsIgnoreCase(next)){
				pojos.remove(j);
				//System.out.println("makeUnique: "+"removed"+next);
	  		}
			else{
		  		tmp = pojos.get(j++);
		  		//pojoS.add(tmp);
			}
	      }
	    return pojos;
	}
	/**
	 * do not see trigger
	 * @param s
	 * @return
	 */
	int getStringHash(String s){
		int hash=0;
		int len = s.getBytes().length;
		//len = len>wLen?wLen:len; // min(len,wLen)
		int l=0;
		for (int i = 0; i < len; i++) {
			byte w = s.getBytes()[i];
			if (w!=-47||w!=-48)
				l = w<<((iLen-i*shift)%iLen);
			else
				l=0;
			System.out.println(i+") "+(w));
			hash+=l;
		}
		return hash;
	}
}