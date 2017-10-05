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
@Table(name = "V_WORKBENCHES", catalog = "", schema = "MSYS_ADMIN")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VWorkbenches.findAll", query = "SELECT v FROM VWorkbenches v"),
    @NamedQuery(name = "VWorkbenches.findByWorkbenchId", query = "SELECT v FROM VWorkbenches v WHERE v.workbenchId = :workbenchId"),
    @NamedQuery(name = "VWorkbenches.findByWorkbenchName", query = "SELECT v FROM VWorkbenches v WHERE v.workbenchName = :workbenchName"),
    @NamedQuery(name = "VWorkbenches.findByWorkbenchComment", query = "SELECT v FROM VWorkbenches v WHERE v.workbenchComment = :workbenchComment")})
public class VWorkbenches implements Serializable {
    private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "WORKBENCH_ID", nullable = false)
    private BigInteger workbenchId;
    @Basic(optional = false)
    @Column(name = "WORKBENCH_NAME", nullable = false, length = 120)
    private String workbenchName;
    @Column(name = "WORKBENCH_COMMENT", length = 1000)
    private String workbenchComment;

    public VWorkbenches() {
    }

    public BigInteger getWorkbenchId() {
        return workbenchId;
    }

    public void setWorkbenchId(BigInteger workbenchId) {
        this.workbenchId = workbenchId;
    }

    public String getWorkbenchName() {
        return workbenchName;
    }

    public void setWorkbenchName(String workbenchName) {
        this.workbenchName = workbenchName;
    }

    public String getWorkbenchComment() {
        return workbenchComment;
    }

    public void setWorkbenchComment(String workbenchComment) {
        this.workbenchComment = workbenchComment;
    }
    
}
