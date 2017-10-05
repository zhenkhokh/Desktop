package model;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

import javax.persistence.Table;

public class RequestBuilder<T> {

	ArrayList<StringBuilder> getXetters(String[] names,final Character x) {
		ArrayList<StringBuilder> xetters = new ArrayList<StringBuilder>();			
		if (names!=null){
			for (int i=0; i<names.length;i++){
				StringBuilder sb = new StringBuilder(names[i]);
				if (sb.length()==0)
					break;
				Character c = new Character(Character.toUpperCase(sb.charAt(0)));
				sb.deleteCharAt(0);
				sb.insert(0, x.toString()+"et"+c.toString()); // get or set
				xetters.add(sb);
			}
		}//else throw new Throwable("names is null");		
		return xetters;
	}
	public T makeEntity(String name,String value,T entity){
		Invoker<T, String> invoker = new Invoker<T, String>();
		return invoker.invokeSetMethod(name,value, entity);
	}
	public T makeEntity(String name,BigInteger value,T entity){
		Invoker<T, BigInteger> invoker = new Invoker<T, BigInteger>();
		return invoker.invokeSetMethod(name,value, entity);
	}
	public T makeEntityBigint_param(String name,BigInteger value,T entity){
		Invoker<T, BigInteger> invoker = new Invoker<T, BigInteger>();
		return invoker.invokeSetMethod(name,value, entity);
	}
	public String getValue(String name,T entity){
		return invokeGetMethod(name, entity);
	}
	public  String makeQuery(String name,T entity){
		String[] names = {name};
		return makeQuery(names, entity);
	}
	/**
	 * 
	 * @param names input field names
	 * @param entity could use empty fields
	 * @return query
	 */
	public String makeQuery(String[] names,T entity){
		StringBuilder sb = new StringBuilder("SELECT t ");
		ArrayList<StringBuilder> getters = getXetters(names,new Character('g'));
		// non empty fields
		ArrayList<Integer> index = new ArrayList<Integer>();
		for (int i=0; i<names.length;i++){
			if (!invokeGetMethod(names[i], entity).isEmpty()){
				index.add(i);
			}
		}
		sb.append("FROM "+getTableName(entity)+" t WHERE ");
		String s=" AND ";
		int c=0;
		for (Iterator iterator = ((ArrayList<Integer>) index).iterator(); iterator.hasNext(); ){
			Integer i = (Integer)iterator.next(); 
			if (c++==index.size()-1)
				s = " ";
			sb.append("t."+names[i]+"=:param"+c+s);
			//sb.append("t."+names[i]+" LIKE "+"\""+invokeGetMethod(names[i], entity)+"\""+s);
			////sb.append("t."+names[i]+" LIKE "+"'%"+invokeGetMethod(names[i], entity)+"%'"+s);			
		}
		return sb.toString();
	}
	String invokeGetMethod(String name,T entity){
		ArrayList<StringBuilder> XETNAMES;
		try {
			String[] NAMES  = {name};
			XETNAMES = getXetters(NAMES,new Character('g'));
			java.lang.reflect.Method m = null;
			for (int i=0;i<XETNAMES.size();i++){
				Class<? extends Object> cl = entity.getClass();
				//System.out.println("what is that class "+cl);
				m = cl.getDeclaredMethod(XETNAMES.get(i).toString(),null);
			}if(m!=null&&m.invoke(entity,null)!=null)
				return  m.invoke(entity,null).toString();
			else
				return "";
		}catch (NoSuchMethodException
				|SecurityException
				|IllegalAccessException
				|IllegalArgumentException
				|InvocationTargetException
				|NullPointerException e) {
					e.printStackTrace();
				}
		return null;
	}
	class Invoker<E,P extends Object>{
		E invokeSetMethod(String name,P value,E entity){
			try {
				String[] NAMES  = {name};
				ArrayList<StringBuilder> XETNAMES = getXetters(NAMES,new Character('s'));
				Class<? extends Object> cl = entity.getClass();			
				java.lang.reflect.Method m = cl.getDeclaredMethod(XETNAMES.get(0).toString(),value.getClass());
				m.invoke(entity,value);
				return entity;
			}catch (NoSuchMethodException
					|SecurityException
					|IllegalAccessException
					|IllegalArgumentException
					|InvocationTargetException
					|NullPointerException e) {
						e.printStackTrace();
					}
			return null;
			}	
	}
	public String getTableName(T entity){
		Class<T> cl = (Class<T>)entity.getClass();
		return cl.getSimpleName();
	}
	public String printEntity(final T entity){
		try{
		Class<?> cl = entity.getClass();
		Field[] f = cl.getDeclaredFields();
		StringBuilder sb = new StringBuilder();
		sb.append("Printing entity "+entity.getClass()+":\n");
		System.out.println("length is  "+f.length);
		for (int i = 0; i < f.length; i++) {
			String name = f[i].getName();
			if (!name.equalsIgnoreCase("serialVersionUID"))
				sb.append(name+" is ").append(invokeGetMethod(name, entity)).append("\n");
		}
		sb.append("end printing");
		return sb.toString();
		}catch (NullPointerException e){return "getClass return null pointer excerption";}
	}
}
