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
package org.makerdao.mcd;

import org.junit.BeforeClass;
import org.junit.Test;
import org.makerdao.mcd.contracts.DSProxy;
import org.makerdao.mcd.core.AllowanceService;
import org.makerdao.mcd.core.TokenSymbols;
import org.makerdao.mcd.ds.DSProxyService;
import org.makerdao.mcd.dsr.SavingsService;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;

public class McdTest {

    private static Mcd MCD_MAINNET;
    private static Mcd MCD_KOVAN;

    @BeforeClass
    public static void initMcd() {

        String privateKey = System.getProperty("privateKey");
        String mainnetEndpoint = System.getProperty("mainnetEndpoint");
        String kovanEndpoint = System.getProperty("kovanEndpoint");

        Credentials credentials = Credentials.create(privateKey);

        try {
            Web3j web3jMainnet = Web3j.build(new HttpService(mainnetEndpoint));
            MCD_MAINNET = new Mcd(web3jMainnet, credentials, new DefaultGasProvider());
        } catch (Exception ex) {
            System.out.println("mainnet MCD not initialized");
        }

        try {
            Web3j web3jKovan = Web3j.build(new HttpService(kovanEndpoint));
            MCD_KOVAN = new Mcd(web3jKovan, credentials, new DefaultGasProvider());
        } catch (Exception ex) {
            System.out.println("kovan MCD not initialized");
        }

    }

    @Test
    public void testSavingsService() throws Exception {

        if (MCD_KOVAN != null) {
            SavingsService savingsService = MCD_KOVAN.getSavingsService();

            assert MCD_KOVAN.getMcdConfiguration().getPotAddress()
                    .equalsIgnoreCase("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb");
            assert MCD_KOVAN.getMcdConfiguration().getDssProxyActionsDsrAddress()
                    .equalsIgnoreCase("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92");
            System.out.println("Kovan CHI: " + savingsService.chi());
            System.out.println("Kovan DSR: " + savingsService.getDsr());
            System.out.println("Kovan Total DAI: " + savingsService.getTotalDai());
        }

        if (MCD_MAINNET != null) {
            SavingsService savingsService = MCD_MAINNET.getSavingsService();

            assert MCD_MAINNET.getMcdConfiguration().getPotAddress()
                    .equalsIgnoreCase("0x197e90f9fad81970ba7976f33cbd77088e5d7cf7");
            assert MCD_MAINNET.getMcdConfiguration().getDssProxyActionsDsrAddress()
                    .equalsIgnoreCase("0x07ee93aeea0a36fff2a9b95dd22bd6049ee54f26");
            System.out.println("Mainnet CHI: " + savingsService.chi());
            System.out.println("Mainnet DSR: " + savingsService.getDsr());
            System.out.println("Mainnet Total DAI: " + savingsService.getTotalDai());
        }

    }

    @Test
    public void testJoinAndExit() throws Exception {
        if (MCD_KOVAN != null) {
            String registeredEthAccount = "0x7f27Fa9B16F42891bC2a702e2F53E4fcA2AA15d2";

            SavingsService savingsService = MCD_KOVAN.getSavingsService();
            DSProxyService dsProxyService = MCD_KOVAN.getDSProxyService();
            AllowanceService allowanceService = MCD_KOVAN.getAllowanceService();

            // 1 - get ds proxy for registered ETH account, if no proxy then create one
            DSProxy dsProxy = dsProxyService.getProxy(registeredEthAccount, true);
            // 2 - set proxy allowance of 10 DAI
            allowanceService.requireAllowance(registeredEthAccount,
                    dsProxy.getContractAddress(),
                    TokenSymbols.DAI,
                    BigDecimal.TEN);

            // 3 - join 3 DAI
            TransactionReceipt receiptJoinThreeDai = savingsService.join(dsProxy, BigDecimal.valueOf(2));
            // check proxy DSR balance
            BigDecimal balance = savingsService.getBalanceOf(dsProxy.getContractAddress());
            System.out.println("DSProxy " + dsProxy.getContractAddress() + " balance: " + balance);

            // 4 - exit 1 DAI
            TransactionReceipt receiptExitOneDai = savingsService.exit(dsProxy, BigDecimal.ONE);

            // 5 - exit all DAI
            TransactionReceipt receiptExitAllDai = savingsService.exitAll(dsProxy);

            // 6 - remove proxy actions allowance
            allowanceService.removeAllowance(registeredEthAccount,
                    dsProxy.getContractAddress(),
                    TokenSymbols.DAI);
        }
    }

}
