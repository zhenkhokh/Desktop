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
public interface Command<T> {
    //TODO add service call in execute if its not null 
	T execute(String fieldValue);
    /**
     * 
     * @param service is actually EJB container that's why this method is depricated
     * @deprecated 
     * @throws IllegalArgumentException 
     */
    void setReciever(Reciver service) throws IllegalArgumentException;
}
