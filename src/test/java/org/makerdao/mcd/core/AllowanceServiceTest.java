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
        mcd = new Mcd(Web3j.build(new HttpService("http://localhost:2000")),
                Credentials.create("474beb999fed1b3af2ea048f963833c686a0fba05f5724cb6417cf3b8ee9697e"),
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
        TransactionReceipt receipt = allowanceService.requireAllowance("0x16fb96a5fa0427af0c8f7cf1eb4870231c8154b6",
                "0x81431b69b1e0e334d4161a13c2955e0f3599381e",
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
        TransactionReceipt receipt = allowanceService.removeAllowance("0x16fb96a5fa0427af0c8f7cf1eb4870231c8154b6",
                "0x81431b69b1e0e334d4161a13c2955e0f3599381e",
                TokenSymbols.DAI);

        assert receipt.isStatusOK();
    }
}
