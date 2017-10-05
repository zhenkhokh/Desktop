/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.command;

import model.service.Reciver;

/**
 *
 * @author 35-khei
 */
public class NoCmd implements Command<Void>{

    @Override
    public Void execute(String fieldValue) {
        System.out.println("no command is done");
        return null;
    }

    @Override
    public void setReciever(Reciver services) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
