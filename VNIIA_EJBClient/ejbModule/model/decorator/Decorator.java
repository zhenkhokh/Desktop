package model.decorator;

import java.util.ArrayList;
import java.util.Set;

import javax.resource.NotSupportedException;

import model.command.Command;

public abstract class Decorator<V> extends Component<V>{
	public abstract void find(Command<?> command) throws NotSupportedException;
	public abstract void refresh() throws NotSupportedException;
	public abstract Set<String> getCoreSet();
	public abstract Command<ArrayList<V>> getCommand();
	public abstract void setCoreSet(Set<String> set); //implements only for user 
}
