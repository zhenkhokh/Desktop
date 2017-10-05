/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.math.BigInteger;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.sun.xml.bind.v2.schemagen.xmlschema.List;

import jdk.nashorn.internal.runtime.ListAdapter;
import model.msysAdmin.VUsers;

/**
 *
 * @author 35-khei
 */
@Remote
public interface IEjbGetUser {
  ArrayList<VUsers> getUser(String userLogin);
}
