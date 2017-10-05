package model.msysAdmin;

import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "V_APPS", catalog = "", schema = "MSYS_ADMIN")
//@JoinTable()
public class VApps {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
        @Basic(optional = false)
        @Column(name = "MODULE_ID", nullable = false)
	BigInteger appId;
    @Basic(optional = false)
    @Column(name = "MODULE_IDN", nullable = false)
    //@OneToMany(mappedBy="")
    private BigInteger moduleIdN;
        @Basic(optional = false)
        @Column(name = "UPDATEDIR", nullable = false, length = 30)
	String updateDir;
	@Basic(optional = false)
        @Column(name = "NAME", nullable = false, length = 30)	
        String name;
	public BigInteger getAppId() {
		return appId;
	}
	public void setAppId(BigInteger appId) {
		this.appId = appId;
	}
	public BigInteger getModuleIdN() {
		return moduleIdN;
	}
	public void setModuleIdN(BigInteger moduleIdN) {
		this.moduleIdN = moduleIdN;
	}	
	public String getUpdateDir() {
		return updateDir;
	}
	public void setUpdateDir(String updateDir) {
		this.updateDir = updateDir;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
