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

import org.junit.Before;
import org.junit.Test;
import org.makerdao.mcd.Mcd;
import org.makerdao.mcd.contracts.ERC20Token;
import org.makerdao.mcd.exceptions.TokenException;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AllowanceServiceTest {

    ERC20Token daiToken;
    Map<String, ERC20Token> tokens = new HashMap<>();
    Mcd mcd;

    @Before
    public void beforeTest() throws Exception {
        daiToken = mock(ERC20Token.class);
        tokens.put(TokenSymbols.DAI, daiToken);
        mcd = new Mcd(Web3j.build(new HttpService("http://localhost:8555")),
                Credentials.create("0x91cf2cc3671a365fcbf38010ff97ee31a5b7e674842663c56769e41600696ead"),
                new DefaultGasProvider());
    }

    @Test(expected = TokenException.class)
    public void testUnknownTokenRequireAllowance() throws Exception {
        AllowanceService service = new AllowanceServiceImpl(tokens);
        service.requireAllowance("", "", "MKR", BigDecimal.TEN);
    }

    @Test
    public void testRequireAllowance() throws Exception {
        AllowanceService allowanceService = mcd.getAllowanceService();
        TransactionReceipt receipt = allowanceService.requireAllowance("0x9596c16d7bf9323265c2f2e22f43e6c80eb3d943",
                "0xe415482ca06eeb684ad3f758c2129fca4b1eb1f4",
                TokenSymbols.DAI,
                BigDecimal.valueOf(100000));

        assert receipt.isStatusOK();
    }

    @Test(expected = TokenException.class)
    public void testUnknownTokenRemoveAllowance() throws Exception {
        AllowanceService service = new AllowanceServiceImpl(tokens);
        service.removeAllowance("", "", "MKR");
    }

    @Test
    public void testRemoveAllowance() throws Exception {
        AllowanceService allowanceService = mcd.getAllowanceService();
        TransactionReceipt receipt = allowanceService.removeAllowance("0x9596c16d7bf9323265c2f2e22f43e6c80eb3d943",
                "0xe415482ca06eeb684ad3f758c2129fca4b1eb1f4",
                TokenSymbols.DAI);

        assert receipt.isStatusOK();
    }
}
