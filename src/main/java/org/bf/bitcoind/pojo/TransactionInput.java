package org.bf.bitcoind.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TransactionInput {

	private String txid;
	private long vout;
	private ScriptSignature scriptSig;
	private long sequence; 
    private String redeemScript;
    public String getRedeemScript() {
		return redeemScript;
	}

	public void setRedeemScript(String redeemScript) {
		this.redeemScript = redeemScript;
	}

	public String getScriptPubKey() {
		return scriptPubKey;
	}

	public void setScriptPubKey(String scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}

	private String scriptPubKey; 
	
	public long getSequence() {
		return sequence;
	}

	public void setSequence(long sequence) {
		this.sequence = sequence;
	}

	public TransactionInput() {
		
	}
	
	public TransactionInput(String txid, long vout) {
	    this.txid = txid;
        this.vout = vout;
	}
	
	public String getTxid() {
		return txid;
	}

	public void setTxid(String txid) {
		this.txid = txid;
	}

	public long getVout() {
		return vout;
	}

	public void setVout(long vout) {
		this.vout = vout;
	}

	public ScriptSignature getScriptSig() {
		return scriptSig;
	}

	public void setScriptSig(ScriptSignature scriptSig) {
		this.scriptSig = scriptSig;
	}	
}
