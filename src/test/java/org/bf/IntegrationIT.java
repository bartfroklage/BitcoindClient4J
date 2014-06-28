package org.bf;

import static org.junit.Assert.assertEquals;
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
		BitcoindClientFactory clientFactory = new BitcoindClientFactory(new URL("http://localhost:8335/"), "admin", "adminpwd");
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
		assertEquals("0100000001a1864c18e776892c367bc2d508b7f77bc501aa1901483c98f497c69dd5ae01a50100000000ffffffff0100e1f505000000001976a914a97f17feb61db5d15cdaca52efb5fd69bea07e9388ac00000000",
				client.createrawtransaction (inputs, outputs));	
	}
	
	@Test
	public void testCreateMultisig() throws BitcoindException, JsonGenerationException, JsonMappingException, IOException {
		List<String> keys = new ArrayList<String>();
		keys.add("0491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f86");
		keys.add("04865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec6874");
		keys.add("048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d46213");
		assertEquals("3QJmV3qfvL9SuYo34YihAf3sRCW3qSinyC", client.createmultisig(2, keys).getAddress());
	}
	
	@Test
	public void testDecodeRawTransaction() {
		client.decoderawtransaction("010000000189632848f99722915727c5c75da8db2dbf194342a0429828f66ff88fab2af7d6000000008b483045022100abbc8a73fe2054480bda3f3281da2d0c51e2841391abd4c09f4f908a2034c18d02205bc9e4d68eafb918f3e9662338647a4419c0de1a650ab8983f1d216e2a31d8e30141046f55d7adeff6011c7eac294fe540c57830be80e9355c83869c9260a4b8bf4767a66bacbd70b804dc63d5beeb14180292ad7f3b083372b1d02d7a37dd97ff5c9effffffff0140420f000000000017a914f815b036d9bbbce5e9f2a00abd1bf3dc91e955108700000000");
	}

}
