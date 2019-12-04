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

import org.json.simple.parser.JSONParser;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.tx.ChainIdLong;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

class McdConfig {

    private Map<String, String> addresses;

    McdConfig(Web3j web3j) throws Exception {
        String configFile = getMcdConfigFile(web3j);

        File file = new File(ClassLoader.getSystemClassLoader().getResource(configFile).getFile());
        FileReader reader = new FileReader(file);
        addresses = ((Map<String, String>) new JSONParser().parse(reader));
    }

    String getDssProxyActionsDsrAddress() {
        return addresses.get("PROXY_ACTIONS_DSR");
    }

    String getPotAddress() {
        return addresses.get("MCD_POT");
    }

    String getVatAddress() {
        return addresses.get("MCD_VAT");
    }

    String getProxyRegistryAddress() {
        return addresses.get("PROXY_REGISTRY");
    }

    String getDaiAdapterAddress() {
        return addresses.get("MCD_JOIN_DAI");
    }

    String getDaiAddress() {
        return addresses.get("MCD_DAI");
    }

    String getBatAddress() {
        return addresses.get("BAT");
    }

    private String getMcdConfigFile(Web3j web3j) throws IOException {
        NetVersion version = web3j.netVersion().send();
        String network = version.getNetVersion();

        if (network.equalsIgnoreCase(Long.toString(ChainIdLong.MAINNET))) {
            return "mainnet.json";
        }

        if (network.equalsIgnoreCase(Long.toString(ChainIdLong.KOVAN))) {
            return "kovan.json";
        }

        return "testnet.json";
    }
}
