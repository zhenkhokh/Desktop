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
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
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
@Table(name = "V_MODULES", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VModules.findAll", query = "SELECT v FROM VModules v"),
    @NamedQuery(name = "VModules.findByModuleId", query = "SELECT v FROM VModules v WHERE v.moduleId = :moduleId"),
    @NamedQuery(name = "VModules.findByWorkbenchId", query = "SELECT v FROM VModules v WHERE v.workbenchId = :workbenchId"),
    @NamedQuery(name = "VModules.findByModuleName", query = "SELECT v FROM VModules v WHERE v.moduleName = :moduleName"),
    @NamedQuery(name = "VModules.findByModuleCode", query = "SELECT v FROM VModules v WHERE v.moduleCode = :moduleCode"),
    @NamedQuery(name = "VModules.findByModuleComment", query = "SELECT v FROM VModules v WHERE v.moduleComment = :moduleComment")})
//@SuppressWarnings("IdDefinedInHierarchy")
public class VModules implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MODULE_ID", nullable = false)
    private BigInteger moduleId;
    @Column(name = "WORKBENCH_ID")
    private BigInteger workbenchId;
    @Basic(optional = false)
    @Column(name = "MODULE_NAME", nullable = false, length = 120)
    private String moduleName;
    @Basic(optional = false)
    @Column(name = "MODULE_CODE", nullable = false, length = 30)
    private String moduleCode;
    @Column(name = "MODULE_COMMENT", length = 1000)
    private String moduleComment;

    public VModules() {
    }

    public BigInteger getModuleId() {
        return moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }

    public BigInteger getWorkbenchId() {
        return workbenchId;
    }

    public void setWorkbenchId(BigInteger workbenchId) {
        this.workbenchId = workbenchId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleComment() {
        return moduleComment;
    }

    public void setModuleComment(String moduleComment) {
        this.moduleComment = moduleComment;
    }
    
}
