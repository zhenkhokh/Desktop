package model.umr;

import java.io.Serializable;
import java.math.BigInteger;

public class VPgm implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigInteger pgmId=new BigInteger("0");
	public BigInteger getPgmId() {
		return pgmId;
	}
	public void setPgmId(BigInteger pgmId) {
		this.pgmId = pgmId;
	}
}
