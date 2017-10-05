/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;
import java.util.ArrayList;

import javax.ejb.Remote;

import model.msysAdmin.VMenu;

/**
 *
 * @author 35-khei
 */
@Remote
public interface IEjbGetModules {
	ArrayList<VMenu> getModules(String userId);
}
