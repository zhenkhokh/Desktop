/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.umr;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 35-khei
 */
@Entity
@Table(name = "V_INDUSTRIAL_PGMS", catalog = "", schema = "DATA_EXCHANGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VIndustrialPgms.findAll", query = "SELECT v FROM VIndustrialPgms v"),
    @NamedQuery(name = "VIndustrialPgms.findByPgmId", query = "SELECT v FROM VIndustrialPgms v WHERE v.pgmId = :pgmId"),
    @NamedQuery(name = "VIndustrialPgms.findBySubsystem", query = "SELECT v FROM VIndustrialPgms v WHERE v.subsystem = :subsystem"),
    @NamedQuery(name = "VIndustrialPgms.findByPgm", query = "SELECT v FROM VIndustrialPgms v WHERE v.pgm = :pgm"),
    @NamedQuery(name = "VIndustrialPgms.findByRamAmount", query = "SELECT v FROM VIndustrialPgms v WHERE v.ramAmount = :ramAmount"),
    @NamedQuery(name = "VIndustrialPgms.findByCpuValue", query = "SELECT v FROM VIndustrialPgms v WHERE v.cpuValue = :cpuValue"),
    @NamedQuery(name = "VIndustrialPgms.findByPgmAdr", query = "SELECT v FROM VIndustrialPgms v WHERE v.pgmAdr = :pgmAdr"),
    @NamedQuery(name = "VIndustrialPgms.findByExeName", query = "SELECT v FROM VIndustrialPgms v WHERE v.exeName = :exeName"),
    @NamedQuery(name = "VIndustrialPgms.findByTicketName", query = "SELECT v FROM VIndustrialPgms v WHERE v.ticketName = :ticketName"),
    @NamedQuery(name = "VIndustrialPgms.findByAllowedUsers", query = "SELECT v FROM VIndustrialPgms v WHERE v.allowedUsers = :allowedUsers"),
    @NamedQuery(name = "VIndustrialPgms.findByRespondent", query = "SELECT v FROM VIndustrialPgms v WHERE v.respondent = :respondent"),
    @NamedQuery(name = "VIndustrialPgms.findByConditions", query = "SELECT v FROM VIndustrialPgms v WHERE v.conditions = :conditions"),
    @NamedQuery(name = "VIndustrialPgms.findBySrvName", query = "SELECT v FROM VIndustrialPgms v WHERE v.srvName = :srvName"),
    @NamedQuery(name = "VIndustrialPgms.findByAdmId", query = "SELECT v FROM VIndustrialPgms v WHERE v.admId = :admId"),
    @NamedQuery(name = "VIndustrialPgms.findByAvtor", query = "SELECT v FROM VIndustrialPgms v WHERE v.avtor = :avtor"),
    @NamedQuery(name = "VIndustrialPgms.findByDZap", query = "SELECT v FROM VIndustrialPgms v WHERE v.dZap = :dZap"),
    @NamedQuery(name = "VIndustrialPgms.findByAdresat", query = "SELECT v FROM VIndustrialPgms v WHERE v.adresat = :adresat"),
    @NamedQuery(name = "VIndustrialPgms.findByAllowedPodr", query = "SELECT v FROM VIndustrialPgms v WHERE v.allowedPodr = :allowedPodr"),
    @NamedQuery(name = "VIndustrialPgms.findByDwe", query = "SELECT v FROM VIndustrialPgms v WHERE v.dwe = :dwe"),
    @NamedQuery(name = "VIndustrialPgms.findByPgmVid", query = "SELECT v FROM VIndustrialPgms v WHERE v.pgmVid = :pgmVid"),
    @NamedQuery(name = "VIndustrialPgms.findByKeyPr", query = "SELECT v FROM VIndustrialPgms v WHERE v.keyPr = :keyPr")})
public class VIndustrialPgms implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PGM_ID", nullable = false)
    private BigInteger pgmId;
    @Column(length = 200)
    private String subsystem;
    @Column(length = 1000)
    private String pgm;
    @Column(name = "RAM_AMOUNT")
    private BigInteger ramAmount;
    @Column(name = "CPU_VALUE")
    private BigInteger cpuValue;
    @Column(name = "PGM_ADR", length = 1000)
    private String pgmAdr;
    @Column(name = "EXE_NAME", length = 500)
    private String exeName;
    @Column(name = "TICKET_NAME", length = 200)
    private String ticketName;
    @Column(name = "ALLOWED_USERS", length = 1000)
    private String allowedUsers;
    @Column(length = 1500)
    private String respondent;
    @Column(length = 2000)
    private String conditions;
    @Column(name = "SRV_NAME", length = 100)
    private String srvName;
    @Column(name = "ADM_ID")
    private BigInteger admId;
    private BigInteger avtor;
    @Column(name = "D_ZAP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dZap;
    @Column(length = 100)
    private String adresat;
    @Column(name = "ALLOWED_PODR", length = 1000)
    private String allowedPodr;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dwe;
    @Column(name = "PGM_VID")
    private BigInteger pgmVid;
    @Column(name = "KEY_PR")
    private BigInteger keyPr;

    public VIndustrialPgms() {
    }

    public BigInteger getPgmId() {
        return pgmId;
    }

    public void setPgmId(BigInteger pgmId) {
        this.pgmId = pgmId;
    }

    public String getSubsystem() {
        return subsystem;
    }

    public void setSubsystem(String subsystem) {
        this.subsystem = subsystem;
    }

    public String getPgm() {
        return pgm;
    }

    public void setPgm(String pgm) {
        this.pgm = pgm;
    }

    public BigInteger getRamAmount() {
        return ramAmount;
    }

    public void setRamAmount(BigInteger ramAmount) {
        this.ramAmount = ramAmount;
    }

    public BigInteger getCpuValue() {
        return cpuValue;
    }

    public void setCpuValue(BigInteger cpuValue) {
        this.cpuValue = cpuValue;
    }

    public String getPgmAdr() {
        return pgmAdr;
    }

    public void setPgmAdr(String pgmAdr) {
        this.pgmAdr = pgmAdr;
    }

    public String getExeName() {
        return exeName;
    }

    public void setExeName(String exeName) {
        this.exeName = exeName;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }

    public String getAllowedUsers() {
        return allowedUsers;
    }

    public void setAllowedUsers(String allowedUsers) {
        this.allowedUsers = allowedUsers;
    }

    public String getRespondent() {
        return respondent;
    }

    public void setRespondent(String respondent) {
        this.respondent = respondent;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getSrvName() {
        return srvName;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public BigInteger getAdmId() {
        return admId;
    }

    public void setAdmId(BigInteger admId) {
        this.admId = admId;
    }

    public BigInteger getAvtor() {
        return avtor;
    }

    public void setAvtor(BigInteger avtor) {
        this.avtor = avtor;
    }

    public Date getDZap() {
        return dZap;
    }

    public void setDZap(Date dZap) {
        this.dZap = dZap;
    }

    public String getAdresat() {
        return adresat;
    }

    public void setAdresat(String adresat) {
        this.adresat = adresat;
    }

    public String getAllowedPodr() {
        return allowedPodr;
    }

    public void setAllowedPodr(String allowedPodr) {
        this.allowedPodr = allowedPodr;
    }

    public Date getDwe() {
        return dwe;
    }

    public void setDwe(Date dwe) {
        this.dwe = dwe;
    }

    public BigInteger getPgmVid() {
        return pgmVid;
    }

    public void setPgmVid(BigInteger pgmVid) {
        this.pgmVid = pgmVid;
    }

    public BigInteger getKeyPr() {
        return keyPr;
    }

    public void setKeyPr(BigInteger keyPr) {
        this.keyPr = keyPr;
    }
    
}
