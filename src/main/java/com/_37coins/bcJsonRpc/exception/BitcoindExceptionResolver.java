package com._37coins.bcJsonRpc.exception;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.googlecode.jsonrpc4j.ExceptionResolver;

public class BitcoindExceptionResolver implements ExceptionResolver {

	@Override
	public Throwable resolveException(ObjectNode objectnode) {
		
		return new BitcoindException();
	}

}
