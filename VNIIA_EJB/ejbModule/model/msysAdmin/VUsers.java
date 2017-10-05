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
@Table(name = "V_USERS", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VUsers.findAll", query = "SELECT v FROM VUsers v"),
    @NamedQuery(name = "VUsers.findByUserId", query = "SELECT v FROM VUsers v WHERE v.userId = :userId"),
    @NamedQuery(name = "VUsers.findByPersonalId", query = "SELECT v FROM VUsers v WHERE v.personalId = :personalId"),
    @NamedQuery(name = "VUsers.findByUserLogin", query = "SELECT v FROM VUsers v WHERE v.userLogin = :userLogin"),
    @NamedQuery(name = "VUsers.findByCodePass", query = "SELECT v FROM VUsers v WHERE v.codePass = :codePass"),
    @NamedQuery(name = "VUsers.findByUserStatus", query = "SELECT v FROM VUsers v WHERE v.userStatus = :userStatus"),
    @NamedQuery(name = "VUsers.findByUserComment", query = "SELECT v FROM VUsers v WHERE v.userComment = :userComment")})
public class VUsers implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "USER_ID", nullable = false)
    private BigInteger userId;
    @Column(name = "PERSONAL_ID")
    private BigInteger personalId;
    @Basic(optional = false)
    @Column(name = "USER_LOGIN", nullable = false, length = 30)
    private String userLogin;
    @Column(name = "CODE_PASS", length = 4000)
    private String codePass;
    @Basic(optional = false)
    @Column(name = "USER_STATUS", nullable = false)
    private BigInteger userStatus;
    @Column(name = "USER_COMMENT", length = 1000)
    private String userComment;

    public VUsers() {
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getPersonalId() {
        return personalId;
    }

    public void setPersonalId(BigInteger personalId) {
        this.personalId = personalId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getCodePass() {
        return codePass;
    }

    public void setCodePass(String codePass) {
        this.codePass = codePass;
    }

    public BigInteger getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(BigInteger userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
    
}
