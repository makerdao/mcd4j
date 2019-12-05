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
import org.makerdao.mcd.Mcd;
import org.makerdao.mcd.contracts.DSProxy;
import org.makerdao.mcd.core.AllowanceService;
import org.makerdao.mcd.core.TokenSymbol;
import org.makerdao.mcd.ds.DSProxyService;
import org.makerdao.mcd.dsr.SavingsService;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

import java.math.BigDecimal;

/**
 *
 * Example class for interacting with DSR module
 * Private key and http endpoint are passed as arguments
 *
 */
public class DsrExample {

    public static void main(String[] args) throws Exception {
        String privateKey = args[0];
        String httpEndpoint = args[1];

        Credentials credentials = Credentials.create(privateKey);
        Web3j web3j = Web3j.build(new HttpService(httpEndpoint));

        // instantiate MCD object - MCD configuration is created based on provided http endpoint type.
        // It currently supports Mainnet and Kovan networks
        Mcd mcd = new Mcd(web3j, credentials, new DefaultGasProvider());

        // get MCD service of interest for DSR
        SavingsService savingsService = mcd.getSavingsService();
        DSProxyService dsProxyService = mcd.getDSProxyService();
        AllowanceService allowanceService = mcd.getAllowanceService();

        // get ds proxy for registered ETH account and print its address. Create proxy if doesn't exist
        DSProxy dsProxy = dsProxyService.getProxy(credentials.getAddress(), true);
        System.out.println(dsProxy.getContractAddress());

        // allow ds proxy to spend 10 DAI (this value should be set accordingly) from registered ETH account
        // and print transaction hash
        TransactionReceipt addDaiAllowance = allowanceService.requireAllowance(credentials.getAddress(),
                dsProxy.getContractAddress(),
                TokenSymbol.DAI,
                BigDecimal.TEN);
        System.out.println(addDaiAllowance.getTransactionHash());

        // join 3 DAI and print transaction hash
        TransactionReceipt receiptJoinThreeDai = savingsService.join(dsProxy, BigDecimal.valueOf(3));
        System.out.println(receiptJoinThreeDai.getTransactionHash());

        // retrieve and print proxy DSR balance
        BigDecimal balance = savingsService.getBalanceOf(dsProxy.getContractAddress());
        System.out.println(balance);

        // exit 1 DAI and print transaction hash
        TransactionReceipt receiptExitOneDai = savingsService.exit(dsProxy, BigDecimal.valueOf(1));
        System.out.println(receiptExitOneDai.getTransactionHash());

        // exit all DAI and print transaction hash
        TransactionReceipt receiptExitAllDai = savingsService.exitAll(dsProxy);
        System.out.println(receiptExitAllDai.getTransactionHash());

        // remove ds proxy allowance on registered ETH account and print transaction hash
        TransactionReceipt removeDaiAllowance = allowanceService.removeAllowance(credentials.getAddress(),
                dsProxy.getContractAddress(),
                TokenSymbol.DAI);
        System.out.println(removeDaiAllowance.getTransactionHash());

    }
}
