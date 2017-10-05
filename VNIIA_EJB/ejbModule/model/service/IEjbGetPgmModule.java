/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.ArrayList;

import javax.ejb.Remote;


import model.msysAdmin.VApps;
import model.umr.PgmModule;

/**
 *
 * @author 35-khei
 */
@Remote
public interface IEjbGetPgmModule {
	ArrayList<PgmModule> getModuleApp(String moduleId);
}
