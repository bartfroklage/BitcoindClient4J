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
import org.bf.bitcoind.pojo.SignRawTransactionInput;
import org.bf.bitcoind.pojo.SignRawTransactionResponse;
import org.bf.bitcoind.pojo.TransactionInformation;
import org.bf.bitcoind.pojo.TransactionInput;
import org.bf.bitcoind.pojo.Unspent;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class IntegrationIT {
	
	static BitcoindInterface client;
	
	@BeforeClass
	static public void before() throws MalformedURLException, IOException{
		BitcoindClientFactory clientFactory = new BitcoindClientFactory(new URL("http://localhost:18332/"), "admin", "adminpwd");
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
		TransactionInformation info  = client.decoderawtransaction("010000000189632848f99722915727c5c75da8db2dbf194342a0429828f66ff88fab2af7d6000000008b483045022100abbc8a73fe2054480bda3f3281da2d0c51e2841391abd4c09f4f908a2034c18d02205bc9e4d68eafb918f3e9662338647a4419c0de1a650ab8983f1d216e2a31d8e30141046f55d7adeff6011c7eac294fe540c57830be80e9355c83869c9260a4b8bf4767a66bacbd70b804dc63d5beeb14180292ad7f3b083372b1d02d7a37dd97ff5c9effffffff0140420f000000000017a914f815b036d9bbbce5e9f2a00abd1bf3dc91e955108700000000");
		System.out.println(info.getVout().get(0).getScriptPubKey().getHex());
	}
	
	@Test
	public void testSignRawTransaction() {
		List<SignRawTransactionInput> inputs = new ArrayList<SignRawTransactionInput>();
		SignRawTransactionInput input = new SignRawTransactionInput();
		input.setRedeemScript("52410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353ae");
		input.setScriptPubKey("a914f815b036d9bbbce5e9f2a00abd1bf3dc91e9551087");
		input.setVout(0);
		input.setTxid("3c9018e8d5615c306d72397f8f5eef44308c98fb576a88e030c25456b4f3a7ac");
		inputs.add(input);
		
		List<String> privateKeys = new ArrayList<String>();
		privateKeys.add("5JaTXbAUmfPYZFRwrYaALK48fN6sFJp4rHqq2QSXs8ucfpE4yQU");
		privateKeys.add("5JFjmGo5Fww9p8gvx48qBYDJNAzR9pmH5S389axMtDyPT8ddqmw");
		String hash = "0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c0000000000ffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000";
		SignRawTransactionResponse response = client.signrawtransaction(hash, inputs, privateKeys);
		
		System.out.println(response.getHex());
		System.out.println(response.isComplete());
	}
	
	@Test
	public void testSendRawTransaction() {
		//System.out.println(client.sendrawtransaction("0100000001aca7f3b45654c230e0886a57fb988c3044ef5e8f7f39726d305c61d5e818903c00000000fd5d010047304402207a8712a3a291eac16cd2cbf543932a12f024bf9a8944c517935cb756dda1739a02205fe0e7ae126a2ce829ec6aa9b594e9089901db1fa3a3508f7d1567dc41654de801483045022100a8978ef2079ecbfdf8446f2a50f15617cc0441aab3c45bf32c6ce554c4693216022074cab1f9d19289ce51c2704ad4ead825c365b2edee6e85a862a60e8aeb778540014cc952410491bba2510912a5bd37da1fb5b1673010e43d2c6d812c514e91bfa9f2eb129e1c183329db55bd868e209aac2fbc02cb33d98fe74bf23f0c235d6126b1d8334f864104865c40293a680cb9c020e7b1e106d8c1916d3cef99aa431a56d253e69256dac09ef122b1a986818a7cb624532f062c1d1f8722084861c5c3291ccffef4ec687441048d2455d2403e08708fc1f556002f1b6cd83f992d085097f9974ab08a28838f07896fbab08f39495e15fa6fad6edbfb1e754e35fa1c7844c41f322a1863d4621353aeffffffff0140420f00000000001976a914ae56b4db13554d321c402db3961187aed1bbed5b88ac00000000"));
	}
	
	
	@Test 
	public void testAddAddress() {
		System.out.println(client.getaccountaddress("test"));
		
		System.out.println(client.getbalance("miGpyv2yWoDyYTJAuyGKMDHLYuWCBsCD62"));
		System.out.println(client.getbalance("msNv8aRoafzJyoiU1s71NEHXmhwWmnsPow"));
		
		List<Unspent> unspents = client.listunspent();
		
	}
	
	
	

}
