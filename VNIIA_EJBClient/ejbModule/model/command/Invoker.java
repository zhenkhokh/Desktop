/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import java.util.ArrayList;

/**
 *
 * @author 35-khei
 */
public class Invoker {
    private ArrayList<Command> commands = new ArrayList<Command>();
    private ArrayList<Object> results = new ArrayList<Object>();
            
    public void pushCommand(Command command){
        commands.add(command);
    }
    public Command pullCommand(String fieldValue){
        int last=commands.size()-1;
        if (last>=0){
            Command cmd = commands.get(last); 
            results.add(cmd.execute(fieldValue));
            commands.remove(last);
            return cmd;
        }
        NoCmd noCmd = new NoCmd();
        noCmd.execute(fieldValue);
        return noCmd;
    }
    public boolean isReady(){
        return commands.isEmpty();
    }
    public Object pullResult(){
        if (!results.isEmpty())
            return results.get(results.size()-1);
        return null;
    }
}
