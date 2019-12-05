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

import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class DSProxyServiceImpl implements DSProxyService {

    private ProxyRegistry proxyRegistry;
    private SmartContractService smartContractService;
    private Map<String, DSProxy> proxyMap = new HashMap<>();

    public DSProxyServiceImpl(ProxyRegistry proxyRegistry, SmartContractService smartContractService) {
        this.proxyRegistry = proxyRegistry;
        this.smartContractService = smartContractService;
    }

    @Override
    public DSProxy getProxy(String proxyAddress) throws Exception {
        return (DSProxy) smartContractService.getContractByAddress(DSProxy.class, proxyAddress);
    }

    @Override
    public DSProxy getProxy(String ownerAddress, boolean create) throws Exception {

        DSProxy cachedProxy = proxyMap.get(ownerAddress);
        if (cachedProxy != null) {
            return cachedProxy;
        }

        String proxyAddress = proxyRegistry.proxies(ownerAddress).send();
        if (proxyAddress.equals("0x0000000000000000000000000000000000000000")) {
            proxyAddress = null;
        }

        if (proxyAddress == null) {
            if (create) {
                TransactionReceipt receipt = proxyRegistry.build(ownerAddress).send();
                if (receipt.isStatusOK()) {
                    proxyAddress = receipt.getLogs().get(0).getAddress();
                }
            } else {
                throw new DSProxyException("No DS Proxy for address " + ownerAddress);
            }
        }

        DSProxy dsProxy = (DSProxy) smartContractService.getContractByAddress(DSProxy.class, proxyAddress);
        proxyMap.put(ownerAddress, dsProxy);
        return dsProxy;
    }

    @Override
    public TransactionReceipt execute(DSProxy dsProxy, String address, Function function) throws Exception {
        return dsProxy.executeEncodedTransaction(dsProxy.getContractAddress(),
                ExecuteFunctionEncoder.encodeFunction(address, function),
                BigInteger.ZERO,
                function.getName(),
                false);
    }

    @Override
    public String getOwner(DSProxy dsProxy) throws Exception {
        return dsProxy.owner().send();
    }

    @Override
    public TransactionReceipt setOwner(DSProxy dsProxy, String ownerAddress) throws Exception {
        return dsProxy.setOwner(ownerAddress).send();
    }

}
