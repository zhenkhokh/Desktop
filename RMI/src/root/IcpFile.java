/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 *
 * @author 35-khei
 */
public interface IcpFile {
    //TODO uncomment Excerption  for 7
    public void copy(String source,String dist) /*throws FileNotFoundException*/;
    public void copySimple(String sourceFTP, String distFile);
    public void copy(ArrayList<String> source,String dist) /*throws FileNotFoundException*/;
    public void copy(String sourcePrefix,ArrayList<String> source,String dist) /*throws FileNotFoundException*/;
    public int getCnt();
    public void setCnt(int cnt);
}
