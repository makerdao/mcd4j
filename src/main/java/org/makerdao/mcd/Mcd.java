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

import org.makerdao.mcd.contracts.*;
import org.makerdao.mcd.core.*;
import org.makerdao.mcd.ds.DSProxyService;
import org.makerdao.mcd.ds.DSProxyServiceImpl;
import org.makerdao.mcd.dsr.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

import java.util.HashMap;
import java.util.Map;

public class Mcd {

    private McdConfig config;
    private SavingsService savingsService;
    private DSProxyService dsProxyService;
    private SmartContractService smartContractService;
    private AllowanceService allowanceService;

    public Mcd(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) throws Exception {
        initServices(web3j, credentials, gasProvider);
    }

    private void initServices(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) throws Exception {
        this.config = new McdConfig(web3j);

        // tokens initialization
        ERC20Token daiErc20Token = ERC20Token.load(config.getDaiAddress(), web3j, credentials, gasProvider);

        // contracts initialization
        Pot pot = Pot.load(config.getPotAddress(), web3j, credentials, gasProvider);
        DssProxyActionsDsr dssProxyActionsDsr = DssProxyActionsDsr.load(config.getDssProxyActionsDsrAddress(), web3j, credentials, gasProvider);
        Vat vat = Vat.load(config.getVatAddress(), web3j, credentials, gasProvider);
        ProxyRegistry proxyRegistry = ProxyRegistry.load(config.getProxyRegistryAddress(), web3j, credentials, gasProvider);

        // services initialization

        smartContractService = new SmartContractServiceImpl(web3j, credentials, gasProvider);
        dsProxyService = new DSProxyServiceImpl(proxyRegistry, smartContractService);
        savingsService = new SavingsServiceImpl(pot, dssProxyActionsDsr, vat, config.getDaiAdapterAddress());

        Map<String, ERC20Token> tokens = new HashMap<>();
        tokens.put(TokenSymbols.DAI, daiErc20Token);
        allowanceService = new AllowanceServiceImpl(tokens);
    }

    public SavingsService getSavingsService() {
        return savingsService;
    }

    public DSProxyService getDSProxyService() {
        return dsProxyService;
    }

    public McdConfig getMcdConfiguration() {
        return this.config;
    }

    public SmartContractService getSmartContractService() {
        return smartContractService;
    }

    public AllowanceService getAllowanceService() {
        return allowanceService;
    }
}
