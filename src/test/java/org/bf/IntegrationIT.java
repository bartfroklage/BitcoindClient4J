package org.bf;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bf.bitcoind.BitcoindClientFactory;
import org.bf.bitcoind.BitcoindInterface;
import org.bf.bitcoind.exception.BitcoindException;
import org.bf.bitcoind.pojo.Info;
import org.bf.bitcoind.pojo.TransactionInput;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IntegrationIT {
	
	static BitcoindInterface client;
	
	@BeforeClass
	static public void before() throws MalformedURLException, IOException{
		BitcoindClientFactory clientFactory = new BitcoindClientFactory(new URL("http://localhost:8332/"), "admin", "adminpwd");
		client = clientFactory.getClient();
	}
	
	@Test
	public void testInfo() throws Exception{
		Info info = client.getinfo();
		System.out.println(new ObjectMapper().writeValueAsString(info));
	}

	@Test
	public void testBalance(){
		BigDecimal balance = client.getbalance();

        System.out.println("balance = " + balance);

        assertTrue(balance.compareTo(BigDecimal.ZERO) >= 0);
	}
	
	
	@Test
	public void testCreaterawtransaction() throws BitcoindException, JsonGenerationException, JsonMappingException, IOException {
		List<TransactionInput> inputs = new ArrayList<TransactionInput> ();		
		inputs.add(new TransactionInput("a501aed59dc697f4983c480119aa01c57bf7b708d5c27b362c8976e7184c86a1", 1));
		Map<String, BigDecimal> outputs = new HashMap<String, BigDecimal>();
		outputs.put("1GTDT3hYk4x4wzaa9k38pRsHy9SPJ7qPzT", new BigDecimal(1));
		System.out.println(client.createrawtransaction (inputs, outputs));
		
	}

}
