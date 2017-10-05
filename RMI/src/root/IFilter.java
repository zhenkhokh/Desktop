/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package root;

import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author 35-khei
 */
public interface IFilter extends IParser {
    public void setExtension(String s);
    public ArrayList<String> filter(Map hashFile);
}
