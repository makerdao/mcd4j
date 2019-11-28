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
import org.makerdao.mcd.dsr.SavingsService;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

public class McdTest {

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
            Mcd mcdMainnet = new Mcd(web3jMainnet, credentials, new DefaultGasProvider());
            MAINNET_SAVINGS_SERVICE = mcdMainnet.getSavingsService();
        } catch (Exception ex) {
            System.out.println("mainnet MCD not initialized");
        }

        try {
            Web3j web3jKovan = Web3j.build(new HttpService(kovanEndpoint));
            Mcd mcdKovan = new Mcd(web3jKovan, credentials, new DefaultGasProvider());
            KOVAN_SAVINGS_SERVICE = mcdKovan.getSavingsService();
        } catch (Exception ex) {
            System.out.println("kovan MCD not initialized");
        }

    }

    @Test
    public void testSavingsService() {

        if (KOVAN_SAVINGS_SERVICE != null) {
            assert KOVAN_SAVINGS_SERVICE.getPotAddress().equalsIgnoreCase("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb");
            assert KOVAN_SAVINGS_SERVICE.getDssProxyActionsDsrAddress().equalsIgnoreCase("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92");
        }

        if (MAINNET_SAVINGS_SERVICE != null) {
            assert MAINNET_SAVINGS_SERVICE.getPotAddress().equalsIgnoreCase("0x197e90f9fad81970ba7976f33cbd77088e5d7cf7");
            assert MAINNET_SAVINGS_SERVICE.getDssProxyActionsDsrAddress().equalsIgnoreCase("0x07ee93aeea0a36fff2a9b95dd22bd6049ee54f26");
        }

    }

    @Test
    public void testService() throws Exception {

        if (KOVAN_SAVINGS_SERVICE != null) {
            System.out.println("Kovan CHI: " + KOVAN_SAVINGS_SERVICE.chi());
            System.out.println("Kovan DSR: " + KOVAN_SAVINGS_SERVICE.getDsr());
            System.out.println("Kovan Total DAI: " + KOVAN_SAVINGS_SERVICE.getTotalDai());
        }

        if (MAINNET_SAVINGS_SERVICE != null) {
            System.out.println("Mainnet CHI: " + MAINNET_SAVINGS_SERVICE.chi());
            System.out.println("Mainnet DSR: " + MAINNET_SAVINGS_SERVICE.getDsr());
            System.out.println("Mainnet Total DAI: " + MAINNET_SAVINGS_SERVICE.getTotalDai());
        }
    }
}
