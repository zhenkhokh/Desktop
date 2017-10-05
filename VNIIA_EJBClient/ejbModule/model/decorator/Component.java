package model.decorator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.resource.NotSupportedException;

import model.RequestBuilder;
import model.command.Command;
import model.command.Invoker;
import model.umr.VIndustrialPgms;

public abstract class Component<V> {
	public static final String EMPTY="string_for_passing";
    final Predicate<String> p = new Predicate<String>() {
		@Override
		public boolean test(String t) {
			return t.equalsIgnoreCase(EMPTY)||t.isEmpty();
		}
	};
	//Set<FILO>, FILO is name, userId, moduleId, appId
	//Set<ArrayList<String>> result = new HashSet<ArrayList<String>>();
	Set<String> set = new HashSet<String>();
	ArrayList<V> pojos=new ArrayList<V>();
	public String fName=EMPTY; 
	
	public String corefName=EMPTY;
	
	public Set<String> getSet(){
    	Util<V> util = new Util<V>();
    	if (!fName.equalsIgnoreCase(EMPTY)){
    		pojos = util.getSortedPojos(pojos, fName);
    		Set<String> s = new HashSet(util.getFields(pojos, fName));
			s.removeIf(p);
    		return s;
    	}
    	System.out.println(""+getClass()+" fName is empty");
		return set;
	}
	public void setSet(Set<String> set){
		this.set=set;
	}
	public ArrayList<String> getResult(){
    	Util<V> util = new Util<V>();
    	if (!fName.equalsIgnoreCase(EMPTY)){
    		return util.getFields(pojos, fName);
    	}
		return null;
	}
	public void makeUnique(String fName){
		sortPojos(fName);
		
    	Util<V> util = new Util<V>();
        RequestBuilder<V> rb = new RequestBuilder<V>();
    	pojos = util.makeUnique(pojos, fName);
	}
	public void sortPojos(String fName){
    	Util<V> util = new Util<V>();
    	if (!fName.equalsIgnoreCase(EMPTY)){
    		pojos = util.getSortedPojos(pojos, fName);
    		Set<String> s = new HashSet(util.getFields(pojos, fName));
			s.removeIf(p);
    	}
	}
	public void joinPojos(ArrayList<V> pojos,final String fName){
		if (pojos!=null)
			this.pojos.addAll(pojos);
		Util<V> util = new Util<V>();
		this.pojos = util.getSortedPojos(this.pojos, fName);
	}
	public ArrayList<V> getPojos(){
		return pojos;
	}
	public void setPojos(ArrayList<V> pojos) {
		this.pojos = pojos;
	}
	public abstract void find(Command<?> command) throws NotSupportedException;
	public abstract Set<String> getCoreSet();
	public abstract void refresh() throws NotSupportedException;
	
	void findTemplate(Command<?> command) {
		if (fName.equalsIgnoreCase(EMPTY)){
			System.out.println(""+getClass()+" fName must be not empty. Do nothig");
			return;
		}
    Invoker invoker = new Invoker();    
    if (getCoreSet()!=null){
    	int numFields = 0;
    	pojos.clear();
        for (Iterator<String> iterator = getCoreSet().iterator(); iterator.hasNext();) {
        	String fValue = (String)iterator.next();
        	System.out.println(""+getClass()+" invoke "+fValue);
        	if (!fValue.isEmpty()){   	
				invoker.pushCommand((Command) command);
				invoker.pullCommand(fValue);
		        pojos.addAll(pojos.size(),(ArrayList<V>)invoker.pullResult());
		        set = getSet();
        	}
	   }
        
        RequestBuilder<V> rb = new RequestBuilder<V>();
		System.out.println("print etitys:");
		for (Iterator iterator2 = pojos.iterator(); iterator2.hasNext();) {
			V v = (V) iterator2.next();
			System.out.println(rb.printEntity(v));
		}
        if (!isValidData())
        	System.out.println(""+getClass()+" has pojos.size()="+pojos.size()+" set.size()="+set.size());
    }else
    	System.out.println(""+getClass()+" has getCoreSet()==null");
    System.out.println(""+getClass()+":"+getSet()+" ");
	}
	public boolean isValidData(){
		return (set.size()==pojos.size())?true:false;
	}
	void refreshComponent(){
    	Util<V> util = new Util<V>();
		if (pojos!=null&&pojos.size()>0){
			set.clear();
			set.addAll(new HashSet<String>(util.getFields(pojos, fName)));
		}else
			System.out.println(""+getClass()+" pojos is not initilized, refresh() cant be done");
	}
}
