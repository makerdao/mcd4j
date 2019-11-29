/*
 * This library or application is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Affero General Public License (AGPL) as published by the Free
 * Software Foundation; either version 3 of the License, or (at your option) any later version.
 *
 * This library or application is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License (AGPL) for
 * more details.
 *
 */
package org.makerdao.mcd.core;

import org.makerdao.mcd.contracts.ERC20Token;
import org.makerdao.mcd.exceptions.TokenException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Map;

import static org.makerdao.mcd.McdConstants.*;

public class AllowanceServiceImpl implements AllowanceService {

    Map<String, ERC20Token> tokens;

    public AllowanceServiceImpl(Map<String, ERC20Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public TransactionReceipt requireAllowance(String owner, String receiver, String tokenSymbol, BigDecimal amountInDai) throws Exception {
        ERC20Token token = tokens.get(tokenSymbol);
        if (token == null) {
            throw new TokenException("No token registered with symbol " + tokenSymbol);
        }

        return token.approve(receiver, amountInDai.toBigInteger().multiply(WAD.toBigInteger())).send();
    }

    @Override
    public TransactionReceipt removeAllowance(String owner, String receiver, String tokenSymbol) throws Exception {
        ERC20Token token = tokens.get(tokenSymbol);
        if (token == null) {
            throw new TokenException("No token registered with symbol " + tokenSymbol);
        }

        return token.approve(receiver, BigInteger.ZERO).send();
    }
}
