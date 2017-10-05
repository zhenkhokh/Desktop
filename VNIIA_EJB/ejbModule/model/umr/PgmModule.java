package model.umr;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "PGM_MODULE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PgmModule.findAll", query = "SELECT p FROM PgmModule p"),
    @NamedQuery(name = "PgmModule.findByPgmModuleId", query = "SELECT p FROM PgmModule p WHERE p.pgmModuleId = :pgmModuleId"),
    @NamedQuery(name = "PgmModule.findByPgmId", query = "SELECT p FROM PgmModule p WHERE p.pgmId = :pgmId"),
    @NamedQuery(name = "PgmModule.findByModuleId", query = "SELECT p FROM PgmModule p WHERE p.moduleId = :moduleId")})
public class PgmModule implements Serializable {
    private static final long serialVersionUID = 1L;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PGM_MODULE_ID", nullable = false, precision = 0, scale = -127)
    private BigDecimal pgmModuleId;
    @Column(name = "PGM_ID")
    private BigInteger pgmId;
    @Column(name = "MODULE_ID")
    private BigInteger moduleId;

    public PgmModule() {
    }

    public PgmModule(BigDecimal pgmModuleId) {
        this.pgmModuleId = pgmModuleId;
    }

    public BigDecimal getPgmModuleId() {
        return pgmModuleId;
    }

    public void setPgmModuleId(BigDecimal pgmModuleId) {
        this.pgmModuleId = pgmModuleId;
    }

    public BigInteger getPgmId() {
        return pgmId;
    }

    public void setPgmId(BigInteger pgmId) {
        this.pgmId = pgmId;
    }

    public BigInteger getModuleId() {
        return moduleId;
    }

    public void setModuleId(BigInteger moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pgmModuleId != null ? pgmModuleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PgmModule)) {
            return false;
        }
        PgmModule other = (PgmModule) object;
        if ((this.pgmModuleId == null && other.pgmModuleId != null) || (this.pgmModuleId != null && !this.pgmModuleId.equals(other.pgmModuleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.umr.PgmModule[ pgmModuleId=" + pgmModuleId + " ]";
    }
    
}
