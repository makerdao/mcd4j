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

import org.junit.Before;
import org.junit.Test;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.response.NetVersion;
import org.web3j.tx.ChainIdLong;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

public class McdConfigTest {

    private Web3j web3j;
    private Web3jService web3jService;

    @Before
    public void beforeTest() {
        web3jService = mock(Web3jService.class);
        web3j = Web3j.build(web3jService);
    }

    @Test
    public void testKovanConfig() throws Exception {
        NetVersion netVersion = new NetVersion();
        netVersion.setResult(Long.toString(ChainIdLong.KOVAN));
        when(web3jService.send(any(Request.class), eq(NetVersion.class))).thenReturn(netVersion);

        McdConfig config = new McdConfig(web3j);
        assert config.getDssProxyActionsDsrAddress().equalsIgnoreCase("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92");
        assert config.getPotAddress().equalsIgnoreCase("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb");
        assert config.getVatAddress().equalsIgnoreCase("0xba987bdb501d131f766fee8180da5d81b34b69d9");
        assert config.getProxyRegistryAddress().equalsIgnoreCase("0x64a436ae831c1672ae81f674cab8b6775df3475c");
        assert config.getDaiAdapterAddress().equalsIgnoreCase("0x5aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c");
        assert config.getDaiAddress().equalsIgnoreCase("0x4f96fe3b7a6cf9725f59d353f723c1bdb64ca6aa");
    }

    @Test
    public void testMainnetConfig() throws Exception {
        NetVersion netVersion = new NetVersion();
        netVersion.setResult(Long.toString(ChainIdLong.MAINNET));
        when(web3jService.send(any(Request.class), eq(NetVersion.class))).thenReturn(netVersion);

        McdConfig config = new McdConfig(web3j);
        assert config.getDssProxyActionsDsrAddress().equalsIgnoreCase("0x07ee93aeea0a36fff2a9b95dd22bd6049ee54f26");
        assert config.getPotAddress().equalsIgnoreCase("0x197e90f9fad81970ba7976f33cbd77088e5d7cf7");
        assert config.getVatAddress().equalsIgnoreCase("0x35d1b3f3d7966a1dfe207aa4514c12a259a0492b");
        assert config.getProxyRegistryAddress().equalsIgnoreCase("0x4678f0a6958e4d2bc4f1baf7bc52e8f3564f3fe4");
        assert config.getDaiAdapterAddress().equalsIgnoreCase("0x9759a6ac90977b93b58547b4a71c78317f391a28");
        assert config.getDaiAddress().equalsIgnoreCase("0x6b175474e89094c44da98b954eedeac495271d0f");
    }

}
