package org.bf.bitcoind.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TransactionInput {

	private String txid;
	private int vout;
	private ScriptSignature scriptSig;
	private long sequence; 
	
	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public TransactionInput() {
		
	}
	
	public TransactionInput(String txid, int vout) {
	    this.txid = txid;
        this.vout = vout;
	}
	
	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public int getVout() {
		return vout;
	}

	public void setVout(int vout) {
		this.vout = vout;
	}

	public ScriptSignature getScriptSig() {
		return scriptSig;
	}

	public void setScriptSig(ScriptSignature scriptSig) {
		this.scriptSig = scriptSig;
	}	
}
