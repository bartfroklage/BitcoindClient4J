package com._37coins.bitcoind.pojo;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class TransactionOutput {
	private String address;
	private BigDecimal amount;
	
	public TransactionOutput(String address, BigDecimal amount) {
	    this.address = address;
        this.amount = amount;
	}
	    
	
	public String getAddress() {
		return address;
	}

	public BigDecimal getAmount() {
		return amount;
	}
}
