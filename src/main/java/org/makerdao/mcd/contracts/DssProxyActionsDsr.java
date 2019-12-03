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
package org.makerdao.mcd.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.3.0.
 */
public class DssProxyActionsDsr extends Contract {
    private static final String BINARY = "Bin file was not provided";

    public static final String FUNC_DAIJOIN_JOIN = "daiJoin_join";

    public static final String FUNC_EXIT = "exit";

    public static final String FUNC_EXITALL = "exitAll";

    public static final String FUNC_JOIN = "join";

    @Deprecated
    protected DssProxyActionsDsr(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DssProxyActionsDsr(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DssProxyActionsDsr(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DssProxyActionsDsr(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> daiJoin_join(String apt, String urn, BigInteger wad) {
        final Function function = new Function(
                FUNC_DAIJOIN_JOIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(apt), 
                new org.web3j.abi.datatypes.Address(urn), 
                new org.web3j.abi.datatypes.generated.Uint256(wad)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> exit(String daiJoin, String pot, BigInteger wad) {
        final Function function = new Function(
                FUNC_EXIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(daiJoin), 
                new org.web3j.abi.datatypes.Address(pot), 
                new org.web3j.abi.datatypes.generated.Uint256(wad)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public Function getExitFunction(String daiJoin, String pot, BigInteger wad) {
        return new Function(
                FUNC_EXIT,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(daiJoin),
                        new org.web3j.abi.datatypes.Address(pot),
                        new org.web3j.abi.datatypes.generated.Uint256(wad)),
                Collections.<TypeReference<?>>emptyList());
    }

    public RemoteCall<TransactionReceipt> exitAll(String daiJoin, String pot) {
        final Function function = new Function(
                FUNC_EXITALL, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(daiJoin), 
                new org.web3j.abi.datatypes.Address(pot)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public Function getExitAllFunction(String daiJoin, String pot) {
        return new Function(
                FUNC_EXITALL,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(daiJoin),
                        new org.web3j.abi.datatypes.Address(pot)),
                Collections.<TypeReference<?>>emptyList());
    }

    public RemoteCall<TransactionReceipt> join(String daiJoin, String pot, BigInteger wad) {
        final Function function = new Function(
                FUNC_JOIN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(daiJoin), 
                new org.web3j.abi.datatypes.Address(pot), 
                new org.web3j.abi.datatypes.generated.Uint256(wad)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public Function getJoinFunction(String daiJoin, String pot, BigInteger wad) {
        return new Function(
                FUNC_JOIN,
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(daiJoin),
                        new org.web3j.abi.datatypes.Address(pot),
                        new org.web3j.abi.datatypes.generated.Uint256(wad)),
                Collections.<TypeReference<?>>emptyList());
    }

    @Deprecated
    public static DssProxyActionsDsr load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DssProxyActionsDsr(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DssProxyActionsDsr load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DssProxyActionsDsr(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DssProxyActionsDsr load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DssProxyActionsDsr(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DssProxyActionsDsr load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DssProxyActionsDsr(contractAddress, web3j, transactionManager, contractGasProvider);
    }
}
