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
import org.makerdao.mcd.core.TokenSymbols;
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
    private static SavingsService KOVAN_SAVINGS_SERVICE;
    private static SavingsService MAINNET_SAVINGS_SERVICE;

    @BeforeClass
    public static void initMcd() {

        String privateKey = System.getProperty("privateKey");
        String mainnetEndpoint = System.getProperty("mainnetEndpoint");
        String kovanEndpoint = System.getProperty("kovanEndpoint");

        Credentials credentials = Credentials.create(privateKey);

        try {
            Web3j web3jMainnet = Web3j.build(new HttpService(mainnetEndpoint));
            MCD_MAINNET = new Mcd(web3jMainnet, credentials, new DefaultGasProvider());
            MAINNET_SAVINGS_SERVICE = MCD_MAINNET.getSavingsService();
        } catch (Exception ex) {
            System.out.println("mainnet MCD not initialized");
        }

        try {
            Web3j web3jKovan = Web3j.build(new HttpService(kovanEndpoint));
            MCD_KOVAN = new Mcd(web3jKovan, credentials, new DefaultGasProvider());
            KOVAN_SAVINGS_SERVICE = MCD_KOVAN.getSavingsService();
        } catch (Exception ex) {
            System.out.println("kovan MCD not initialized");
        }

    }

    @Test
    public void testSavingsService() throws Exception {

        if (KOVAN_SAVINGS_SERVICE != null) {
            assert MCD_KOVAN.getMcdConfiguration().getPotAddress()
                    .equalsIgnoreCase("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb");
            assert MCD_KOVAN.getMcdConfiguration().getDssProxyActionsDsrAddress()
                    .equalsIgnoreCase("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92");
            System.out.println("Kovan CHI: " + KOVAN_SAVINGS_SERVICE.chi());
            System.out.println("Kovan DSR: " + KOVAN_SAVINGS_SERVICE.getDsr());
            System.out.println("Kovan Total DAI: " + KOVAN_SAVINGS_SERVICE.getTotalDai());
            String dsProxy = MCD_KOVAN.getDSProxyService().getProxyAddress("0xD71FE1D44B453D2bF57AD6B295Fb2b516e06fC81", true);
            System.out.println("Kovan " + dsProxy + " balance: " + KOVAN_SAVINGS_SERVICE.getBalanceOf(dsProxy).floatValue());

            MCD_KOVAN.getAllowanceService().requireAllowance("0xD71FE1D44B453D2bF57AD6B295Fb2b516e06fC81",
                    MCD_KOVAN.getMcdConfiguration().getDssProxyActionsDsrAddress(), TokenSymbols.DAI, BigDecimal.TEN);

            TransactionReceipt exitAll = KOVAN_SAVINGS_SERVICE.exitAll();
            System.out.println("Kovan Join Exit all DAI: " + exitAll);

            TransactionReceipt receiptJoinOneDai = KOVAN_SAVINGS_SERVICE.join(BigDecimal.valueOf(3));
            System.out.println("Kovan Join 3 DAI: " + receiptJoinOneDai);

            // TODO: Fix balance showing 0
            System.out.println("Kovan 0x65eed7ca9b86f24b919474869bfa0d11742100d5 balance: " + KOVAN_SAVINGS_SERVICE.getBalanceOf(dsProxy).floatValue());

            TransactionReceipt exitOneDai = KOVAN_SAVINGS_SERVICE.exit(BigDecimal.ONE);
            System.out.println("Kovan Join Exit 1 DAI: " + exitOneDai);

            MCD_KOVAN.getAllowanceService().removeAllowance("0xD71FE1D44B453D2bF57AD6B295Fb2b516e06fC81",
                    MCD_KOVAN.getMcdConfiguration().getDssProxyActionsDsrAddress(), TokenSymbols.DAI);
        }

        if (MAINNET_SAVINGS_SERVICE != null) {
            assert MCD_MAINNET.getMcdConfiguration().getPotAddress()
                    .equalsIgnoreCase("0x197e90f9fad81970ba7976f33cbd77088e5d7cf7");
            assert MCD_MAINNET.getMcdConfiguration().getDssProxyActionsDsrAddress()
                    .equalsIgnoreCase("0x07ee93aeea0a36fff2a9b95dd22bd6049ee54f26");
            System.out.println("Mainnet CHI: " + MAINNET_SAVINGS_SERVICE.chi());
            System.out.println("Mainnet DSR: " + MAINNET_SAVINGS_SERVICE.getDsr());
            System.out.println("Mainnet Total DAI: " + MAINNET_SAVINGS_SERVICE.getTotalDai());
        }

    }

    @Test
    public void testDSProxy() throws Exception {
        if (KOVAN_SAVINGS_SERVICE != null) {
            System.out.println("DSProxy address: " + MCD_KOVAN.getDSProxyService().getProxyAddress("0xD71FE1D44B453D2bF57AD6B295Fb2b516e06fC81", true));
            System.out.println("Owner of DS Proxy 0x65eed7ca9b86f24b919474869bfa0d11742100d5 is : " + MCD_KOVAN.getDSProxyService().getOwner("0x65eed7ca9b86f24b919474869bfa0d11742100d5"));
        }
    }
}
