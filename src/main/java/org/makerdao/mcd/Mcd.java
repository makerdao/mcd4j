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

import org.makerdao.mcd.contracts.DssProxyActionsDsr;
import org.makerdao.mcd.contracts.Pot;
import org.makerdao.mcd.contracts.Vat;
import org.makerdao.mcd.dsr.*;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;

public class Mcd {

    private McdConfig config;
    private SavingsService savingsService;

    public Mcd(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) throws Exception {
        initServices(web3j, credentials, gasProvider);
    }

    private void initServices(Web3j web3j, Credentials credentials, ContractGasProvider gasProvider) throws Exception {
        this.config = new McdConfig(web3j);
        Pot potContract = Pot.load(config.getPotAddress(), web3j, credentials, gasProvider);
        DssProxyActionsDsr dssProxyActionsDsr = DssProxyActionsDsr.load(config.getDssProxyActionsDsrAddress(), web3j, credentials, gasProvider);
        Vat vatContract = Vat.load(config.getVatAddress(), web3j, credentials, gasProvider);
        savingsService = new SavingsServiceImpl(potContract, dssProxyActionsDsr, vatContract);
    }

    public SavingsService getSavingsService() {
        return savingsService;
    }

    public McdConfig getMcdConfiguration() {
        return this.config;
    }
}
