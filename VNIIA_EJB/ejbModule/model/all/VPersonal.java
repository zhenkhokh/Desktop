/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.all;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 35-khei
 */
@Entity
@Table(name = "V_PERSONAL", catalog = "", schema = "MSYS_ALL")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VPersonal.findAll", query = "SELECT v FROM VPersonal v"),
    @NamedQuery(name = "VPersonal.findByPersonalId", query = "SELECT v FROM VPersonal v WHERE v.personalId = :personalId"),
    @NamedQuery(name = "VPersonal.findByPersonalName", query = "SELECT v FROM VPersonal v WHERE v.personalName = :personalName"),
    @NamedQuery(name = "VPersonal.findByPersonalNumber", query = "SELECT v FROM VPersonal v WHERE v.personalNumber = :personalNumber"),
    @NamedQuery(name = "VPersonal.findByFlagDismiss", query = "SELECT v FROM VPersonal v WHERE v.flagDismiss = :flagDismiss")})
public class VPersonal implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PERSONAL_ID", nullable = false)
    private BigInteger personalId;
    @Column(name = "PERSONAL_NAME", length = 92)
    private String personalName;
    @Basic(optional = false)
    @Column(name = "PERSONAL_NUMBER", nullable = false)
    private BigInteger personalNumber;
    @Column(name = "FLAG_DISMISS")
    private Short flagDismiss;

    public VPersonal() {
    }

    public BigInteger getPersonalId() {
        return personalId;
    }

    public void setPersonalId(BigInteger personalId) {
        this.personalId = personalId;
    }

    public String getPersonalName() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName = personalName;
    }

    public BigInteger getPersonalNumber() {
        return personalNumber;
    }

    public void setPersonalNumber(BigInteger personalNumber) {
        this.personalNumber = personalNumber;
    }

    public Short getFlagDismiss() {
        return flagDismiss;
    }

    public void setFlagDismiss(Short flagDismiss) {
        this.flagDismiss = flagDismiss;
    }
    
}
