package org.bf.bitcoind.pojo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TransactionOutput {
	
	private long n;
	private BigDecimal value;
	private ScriptPubKey scriptPubKey;
	
	public long getN() {
		return n;
	}
	public void setN(long n) {
		this.n = n;
	}
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public ScriptPubKey getScriptPubKey() {
		return scriptPubKey;
	}
	public void setScriptPubKey(ScriptPubKey scriptPubKey) {
		this.scriptPubKey = scriptPubKey;
	}
}
