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

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.Contract;
import org.web3j.tx.gas.ContractGasProvider;

import java.lang.reflect.Method;

public class SmartContractServiceImpl implements SmartContractService {

    private Web3j web3j;
    private Credentials credentials;
    private ContractGasProvider gasProvider;

    public SmartContractServiceImpl(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) {
        this.web3j = web3j;
        this.credentials = credentials;
        this.gasProvider = gasProvider;
    }

    @Override
    public Contract getContractByAddress(Class contractClass, String address) throws Exception {
        Method loadContract = contractClass.getDeclaredMethod("load", String.class, Web3j.class, Credentials.class, ContractGasProvider.class);
        return (Contract) loadContract.invoke(null, address, web3j, credentials, gasProvider);
    }
}
