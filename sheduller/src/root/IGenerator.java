package root;


import java.io.File;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 35-khei
 */
public interface IGenerator {
    final String CRITICAL="0";
    final String VERSION="0";
    final String ALFAHRM="AlfaHRM";
    final String ALFASALARY="AlfaSalary";
    final String ALFAHRMKEY="hrm.exe";
    final String ALFASALARYKEY="salary.exe";
    final String ALFAADMINKEY="Administrator.exe";
    final String ALFAADMIN="AlfaAdmin";
    /*
    *  cmd is {App,dir,bin,names,dir}
    */    
    void exe(File file,String ... cmd); 
    
}
