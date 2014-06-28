package org.bf.bitcoind.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TransactionInput {
	
	private String txid;
	private int vout;
	
	public TransactionInput(String txid, int vout) {
	    this.txid = txid;
        this.vout = vout;
	}
	
	public String getTxid() {		
		return txid;
	}
	public int getVout() {
		return vout;		
	}
}
