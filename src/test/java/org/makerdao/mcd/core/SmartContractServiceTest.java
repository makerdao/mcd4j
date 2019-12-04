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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.makerdao.mcd.contracts.DSProxy;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.DefaultGasProvider;

import static org.mockito.Mockito.mock;

@PrepareForTest({DSProxy.class})
@RunWith(PowerMockRunner.class)
public class SmartContractServiceTest {

    @Test
    public void testGetContract() throws Exception {
        Web3j web3j = mock(Web3j.class);
        Credentials credentials = Credentials.create("0x0000");
        DefaultGasProvider gasProvider = new DefaultGasProvider();
        SmartContractService service = new SmartContractServiceImpl(web3j, credentials, gasProvider);

        PowerMockito.mockStatic(DSProxy.class);

        service.getContractByAddress(DSProxy.class, "0x84f62bf8ef340d94ac367cc66a2a088a1d41fbd5");

        PowerMockito.verifyStatic(DSProxy.class);
        DSProxy.load("0x84f62bf8ef340d94ac367cc66a2a088a1d41fbd5", web3j, credentials, gasProvider);
    }
}
