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
@Table(name = "V_MENU", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VMenu.findAll", query = "SELECT v FROM VMenu v"),
    @NamedQuery(name = "VMenu.findByMenuId", query = "SELECT v FROM VMenu v WHERE v.menuId = :menuId"),
    @NamedQuery(name = "VMenu.findByUserId", query = "SELECT v FROM VMenu v WHERE v.userId = :userId"),
    @NamedQuery(name = "VMenu.findByModuleId", query = "SELECT v FROM VMenu v WHERE v.moduleId = :moduleId"),
    @NamedQuery(name = "VMenu.findByRoleId", query = "SELECT v FROM VMenu v WHERE v.roleId = :roleId")})
public class VMenu implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MENU_ID", nullable = false)
    private BigInteger menuId;
    @Basic(optional = false)
    @Column(name = "USER_ID", nullable = false)
    private BigInteger userId;
    @Basic(optional = false)
    @Column(name = "MODULE_ID", nullable = false)
    private BigInteger moduleId;
    @Basic(optional = false)
    @Column(name = "ROLE_ID", nullable = false)
    private BigInteger roleId;

    public VMenu() {
    }

    public BigInteger getMenuId() {
        return menuId;
    }

    public void setMenuId(BigInteger menuId) {
        this.menuId = menuId;
    }

    public BigInteger getUserId() {
        return userId;
    }

    public void setUserId(BigInteger userId) {
        this.userId = userId;
    }

    public BigInteger getModuleId() {
        return moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }

    public BigInteger getRoleId() {
        return roleId;
    }

    public void setRoleId(BigInteger roleId) {
        this.roleId = roleId;
    }
    
}
