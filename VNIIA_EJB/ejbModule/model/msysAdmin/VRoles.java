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
@Table(name = "V_ROLES", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VRoles.findAll", query = "SELECT v FROM VRoles v"),
    @NamedQuery(name = "VRoles.findByRoleId", query = "SELECT v FROM VRoles v WHERE v.roleId = :roleId"),
    @NamedQuery(name = "VRoles.findByModuleId", query = "SELECT v FROM VRoles v WHERE v.moduleId = :moduleId"),
    @NamedQuery(name = "VRoles.findByRoleName", query = "SELECT v FROM VRoles v WHERE v.roleName = :roleName"),
    @NamedQuery(name = "VRoles.findByRoleCode", query = "SELECT v FROM VRoles v WHERE v.roleCode = :roleCode"),
    @NamedQuery(name = "VRoles.findByRoleComment", query = "SELECT v FROM VRoles v WHERE v.roleComment = :roleComment")})
public class VRoles implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ROLE_ID", nullable = false)
    private BigInteger roleId;
    @Basic(optional = false)
    @Column(name = "MODULE_ID", nullable = false)
    private BigInteger moduleId;
    @Basic(optional = false)
    @Column(name = "ROLE_NAME", nullable = false, length = 120)
    private String roleName;
    @Column(name = "ROLE_CODE", length = 30)
    private String roleCode;
    @Column(name = "ROLE_COMMENT", length = 1000)
    private String roleComment;

    public VRoles() {
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }

    public BigInteger getModuleId() {
        return moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleComment() {
        return roleComment;
    }

    public void setRoleComment(String roleComment) {
        this.roleComment = roleComment;
    }
    
}
