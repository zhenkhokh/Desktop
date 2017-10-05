/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.msysAdmin;

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
@Table(name = "V_SETUP", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VSetup.findAll", query = "SELECT v FROM VSetup v"),
    @NamedQuery(name = "VSetup.findBySetupId", query = "SELECT v FROM VSetup v WHERE v.setupId = :setupId"),
    @NamedQuery(name = "VSetup.findByRoleId", query = "SELECT v FROM VSetup v WHERE v.roleId = :roleId"),
    @NamedQuery(name = "VSetup.findByPrivId", query = "SELECT v FROM VSetup v WHERE v.privId = :privId"),
    @NamedQuery(name = "VSetup.findByPrivStatus", query = "SELECT v FROM VSetup v WHERE v.privStatus = :privStatus")})
public class VSetup implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SETUP_ID", nullable = false)
    private BigInteger setupId;
    @Basic(optional = false)
    @Column(name = "ROLE_ID", nullable = false)
    private BigInteger roleId;
    @Basic(optional = false)
    @Column(name = "PRIV_ID", nullable = false)
    private BigInteger privId;
    @Basic(optional = false)
    @Column(name = "PRIV_STATUS", nullable = false)
    private BigInteger privStatus;

    public VSetup() {
    }

    public BigInteger getSetupId() {
        return setupId;
    }

    public void setSetupId(BigInteger setupId) {
        this.setupId = setupId;
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public BigInteger getPrivId() {
        return privId;
    }

    public void setPrivId(BigInteger privId) {
        this.privId = privId;
    }

    public BigInteger getPrivStatus() {
        return privStatus;
    }

    public void setPrivStatus(BigInteger privStatus) {
        this.privStatus = privStatus;
    }
    
}
