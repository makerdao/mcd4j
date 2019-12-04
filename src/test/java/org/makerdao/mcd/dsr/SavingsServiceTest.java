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
package org.makerdao.mcd.dsr;

import org.junit.Before;
import org.junit.Test;
import org.makerdao.mcd.Mcd;
import org.makerdao.mcd.contracts.DSProxy;
import org.makerdao.mcd.core.AllowanceService;
import org.makerdao.mcd.core.TokenSymbols;
import org.makerdao.mcd.ds.DSProxyService;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;


public class SavingsServiceTest {

    Mcd mcd;
    Credentials credentials;
    Web3j web3j;

    @Before
    public void beforeTest() throws Exception {
        credentials = Credentials.create("474beb999fed1b3af2ea048f963833c686a0fba05f5724cb6417cf3b8ee9697e");
        web3j = Web3j.build(new HttpService("http://localhost:2000"));
        mcd = new Mcd(web3j,
                credentials,
                new DefaultGasProvider());
    }

    @Test
    public void testJoinAndExit() throws Exception {
        DSProxyService dsProxyService = mcd.getDSProxyService();
        DSProxy dsProxy = dsProxyService.getProxy("0x16fb96a5fa0427af0c8f7cf1eb4870231c8154b6", false);

        assert dsProxy.getContractAddress().equalsIgnoreCase("0x06dcc6fa0b1173fe95e6b94412ea3d2ba9b2c849");
        assert dsProxyService.getOwner(dsProxy).equalsIgnoreCase("0x16fb96a5fa0427af0c8f7cf1eb4870231c8154b6");

        AllowanceService allowanceService = mcd.getAllowanceService();

        TransactionReceipt addDaiAllowance = allowanceService.requireAllowance(credentials.getAddress(),
                dsProxy.getContractAddress(),
                TokenSymbols.DAI,
                BigDecimal.TEN);
        assert addDaiAllowance.isStatusOK();

        SavingsService savingsService = mcd.getSavingsService();

        TransactionReceipt receiptJoinDai = savingsService.join(dsProxy, BigDecimal.valueOf(45));
        assert receiptJoinDai.isStatusOK();

        TransactionReceipt receiptExitDai = savingsService.exit(dsProxy, BigDecimal.valueOf(32));
        assert receiptExitDai.isStatusOK();

        TransactionReceipt receiptExitAllDai = savingsService.exitAll(dsProxy);
        assert receiptExitAllDai.isStatusOK();

        TransactionReceipt removeDaiAllowance = allowanceService.removeAllowance(credentials.getAddress(),
                dsProxy.getContractAddress(),
                TokenSymbols.DAI);
        assert removeDaiAllowance.isStatusOK();

    }
}