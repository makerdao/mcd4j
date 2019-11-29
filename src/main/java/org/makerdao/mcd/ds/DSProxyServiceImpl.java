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
package org.makerdao.mcd.ds;

import org.makerdao.mcd.contracts.DSProxy;
import org.makerdao.mcd.contracts.ProxyRegistry;
import org.makerdao.mcd.core.SmartContractService;
import org.makerdao.mcd.exceptions.DSProxyException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public class DSProxyServiceImpl implements DSProxyService {

    ProxyRegistry proxyRegistry;
    SmartContractService smartContractService;

    public DSProxyServiceImpl(ProxyRegistry proxyRegistry, SmartContractService smartContractService) {
        this.proxyRegistry = proxyRegistry;
        this.smartContractService = smartContractService;
    }

    @Override
    public String getProxyAddress(String ownerAddress, boolean create) throws Exception {
        String proxyAddress = this.proxyRegistry.proxies(ownerAddress).send();
        if (proxyAddress.equals("0x0000000000000000000000000000000000000000")) {
            proxyAddress = null;
        }

        if (proxyAddress == null) {
            if (create) {
                TransactionReceipt receipt = this.proxyRegistry.build(ownerAddress).send();
                if (receipt.isStatusOK()) {
                    proxyAddress = receipt.getLogs().get(0).getAddress();
                }
            } else {
                throw new DSProxyException("No DS Proxy for address " + ownerAddress);
            }
        }

        return proxyAddress;

    }

    @Override
    public String getOwner(String proxyAddress) throws Exception {
        DSProxy contract = (DSProxy) this.smartContractService.getContractByAddress(DSProxy.class, proxyAddress);
        return contract.owner().send();
    }

    @Override
    public TransactionReceipt setOwner(String proxyAddress, String ownerAddress) throws Exception {
        DSProxy contract = (DSProxy) this.smartContractService.getContractByAddress(DSProxy.class, proxyAddress);
        return contract.setOwner(ownerAddress).send();
    }
}
