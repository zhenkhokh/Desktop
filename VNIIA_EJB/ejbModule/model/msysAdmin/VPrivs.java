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
@Table(name = "V_PRIVS", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VPrivs.findAll", query = "SELECT v FROM VPrivs v"),
    @NamedQuery(name = "VPrivs.findByPrivId", query = "SELECT v FROM VPrivs v WHERE v.privId = :privId"),
    @NamedQuery(name = "VPrivs.findByModuleId", query = "SELECT v FROM VPrivs v WHERE v.moduleId = :moduleId"),
    @NamedQuery(name = "VPrivs.findByPrivName", query = "SELECT v FROM VPrivs v WHERE v.privName = :privName"),
    @NamedQuery(name = "VPrivs.findByPrivCode", query = "SELECT v FROM VPrivs v WHERE v.privCode = :privCode"),
    @NamedQuery(name = "VPrivs.findByPrivScript", query = "SELECT v FROM VPrivs v WHERE v.privScript = :privScript"),
    @NamedQuery(name = "VPrivs.findByPrivComment", query = "SELECT v FROM VPrivs v WHERE v.privComment = :privComment")})
@SuppressWarnings("IdDefinedInHierarchy")
public class VPrivs implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PRIV_ID", nullable = false)
    private BigInteger privId;
    @Basic(optional = false)
    @Column(name = "MODULE_ID", nullable = false)
    private BigInteger moduleId;
    @Basic(optional = false)
    @Column(name = "PRIV_NAME", nullable = false, length = 120)
    private String privName;
    @Basic(optional = false)
    @Column(name = "PRIV_CODE", nullable = false, length = 30)
    private String privCode;
    @Column(name = "PRIV_SCRIPT", length = 3000)
    private String privScript;
    @Column(name = "PRIV_COMMENT", length = 1000)
    private String privComment;

    public VPrivs() {
    }

    public BigInteger getPrivId() {
        return privId;
    }

    public void setPrivId(BigInteger privId) {
        this.privId = privId;
    }

    public BigInteger getModuleId() {
        return moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }

    public String getPrivName() {
        return privName;
    }

    public void setPrivName(String privName) {
        this.privName = privName;
    }

    public String getPrivCode() {
        return privCode;
    }

    public void setPrivCode(String privCode) {
        this.privCode = privCode;
    }

    public String getPrivScript() {
        return privScript;
    }

    public void setPrivScript(String privScript) {
        this.privScript = privScript;
    }

    public String getPrivComment() {
        return privComment;
    }

    public void setPrivComment(String privComment) {
        this.privComment = privComment;
    }
    
}
