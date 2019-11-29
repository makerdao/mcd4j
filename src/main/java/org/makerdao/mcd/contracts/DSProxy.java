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

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Bytes4;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
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
public class DSProxy extends Contract {
    public static final String BINARY = "608060405234801561001057600080fd5b50604051602080610b37833981016040819052905160018054600160a060020a03191633600160a060020a031690811790915590917fce241d7ca1f669fee44b6fc00b8eba2df3bb514eed0f6f668f8f89096e81ed9490600090a261007d8164010000000061008e810204565b151561008857600080fd5b506102ac565b60006100c6337fffffffff00000000000000000000000000000000000000000000000000000000833516640100000000610180810204565b15156100d157600080fd5b6040805134808252602082018381523693830184905260043593602435938493869333600160a060020a031693600080357fffffffff0000000000000000000000000000000000000000000000000000000016949092606082018484808284376040519201829003965090945050505050a4600160a060020a038416151561015857600080fd5b60028054600160a060020a038616600160a060020a0319909116179055600192505050919050565b600030600160a060020a031683600160a060020a031614156101a4575060016102a6565b600154600160a060020a03848116911614156101c2575060016102a6565b600054600160a060020a031615156101dc575060006102a6565b60008054604080517fb7009613000000000000000000000000000000000000000000000000000000008152600160a060020a03878116600483015230811660248301527fffffffff00000000000000000000000000000000000000000000000000000000871660448301529151919092169263b700961392606480820193602093909283900390910190829087803b15801561027757600080fd5b505af115801561028b573d6000803e3d6000fd5b505050506040513d60208110156102a157600080fd5b505190505b92915050565b61087c806102bb6000396000f30060806040526004361061008d5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166313af4035811461008f5780631cff79cd146100b05780631f6a1eb91461011c57806360c7d295146101c95780637a9e5e4b146101fa5780638da5cb5b1461021b578063948f507614610230578063bf7e214f14610265575b005b34801561009b57600080fd5b5061008d600160a060020a036004351661027a565b60408051602060046024803582810135601f810185900485028601850190965285855261010a958335600160a060020a03169536956044949193909101919081908401838280828437509497506102f89650505050505050565b60408051918252519081900360200190f35b6040805160206004803580820135601f81018490048402850184019095528484526101a694369492936024939284019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506103be9650505050505050565b60408051600160a060020a03909316835260208301919091528051918290030190f35b3480156101d557600080fd5b506101de6105ce565b60408051600160a060020a039092168252519081900360200190f35b34801561020657600080fd5b5061008d600160a060020a03600435166105dd565b34801561022757600080fd5b506101de610657565b34801561023c57600080fd5b50610251600160a060020a0360043516610666565b604080519115158252519081900360200190f35b34801561027157600080fd5b506101de61072d565b61029033600035600160e060020a03191661073c565b151561029b57600080fd5b6001805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a0383811691909117918290556040519116907fce241d7ca1f669fee44b6fc00b8eba2df3bb514eed0f6f668f8f89096e81ed9490600090a250565b600061031033600035600160e060020a03191661073c565b151561031b57600080fd5b6040805134808252602082018381523693830184905260043593602435938493869333600160a060020a03169360008035600160e060020a031916949092606082018484808284376040519201829003965090945050505050a4600160a060020a038516151561038a57600080fd5b60206000855160208701886113885a03f460005193508015600181146103af576103b4565b600080fd5b5050505092915050565b6002546040517f8bf4515c0000000000000000000000000000000000000000000000000000000081526020600482018181528551602484015285516000948594600160a060020a0390911693638bf4515c93899390928392604490910191908501908083838b5b8381101561043d578181015183820152602001610425565b50505050905090810190601f16801561046a5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15801561048957600080fd5b505af115801561049d573d6000803e3d6000fd5b505050506040513d60208110156104b357600080fd5b50519150600160a060020a03821615156105bb576002546040517f7ed0c3b2000000000000000000000000000000000000000000000000000000008152602060048201818152875160248401528751600160a060020a0390941693637ed0c3b293899383926044909201919085019080838360005b83811015610540578181015183820152602001610528565b50505050905090810190601f16801561056d5780820380516001836020036101000a031916815260200191505b5092505050602060405180830381600087803b15801561058c57600080fd5b505af11580156105a0573d6000803e3d6000fd5b505050506040513d60208110156105b657600080fd5b505191505b6105c582846102f8565b90509250929050565b600254600160a060020a031681565b6105f333600035600160e060020a03191661073c565b15156105fe57600080fd5b6000805473ffffffffffffffffffffffffffffffffffffffff1916600160a060020a03838116919091178083556040519116917f1abebea81bfa2637f28358c371278fb15ede7ea8dd28d2e03b112ff6d936ada491a250565b600154600160a060020a031681565b600061067e33600035600160e060020a03191661073c565b151561068957600080fd5b6040805134808252602082018381523693830184905260043593602435938493869333600160a060020a03169360008035600160e060020a031916949092606082018484808284376040519201829003965090945050505050a4600160a060020a03841615156106f857600080fd5b60028054600160a060020a03861673ffffffffffffffffffffffffffffffffffffffff19909116179055600192505050919050565b600054600160a060020a031681565b600030600160a060020a031683600160a060020a031614156107605750600161084a565b600154600160a060020a038481169116141561077e5750600161084a565b600054600160a060020a031615156107985750600061084a565b60008054604080517fb7009613000000000000000000000000000000000000000000000000000000008152600160a060020a0387811660048301523081166024830152600160e060020a0319871660448301529151919092169263b700961392606480820193602093909283900390910190829087803b15801561081b57600080fd5b505af115801561082f573d6000803e3d6000fd5b505050506040513d602081101561084557600080fd5b505190505b929150505600a165627a7a72305820e498874c9ba9e75028e0c84f1b1d83b2dad5de910c59b837b32e5a190794c5e10029";

    public static final String FUNC_SETOWNER = "setOwner";

    public static final String FUNC_EXECUTE = "execute";

    public static final String FUNC_CACHE = "cache";

    public static final String FUNC_SETAUTHORITY = "setAuthority";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_SETCACHE = "setCache";

    public static final String FUNC_AUTHORITY = "authority";

    public static final Event LOGNOTE_EVENT = new Event("LogNote", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Bytes4>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Bytes32>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<DynamicBytes>() {}));
    ;

    public static final Event LOGSETAUTHORITY_EVENT = new Event("LogSetAuthority", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    public static final Event LOGSETOWNER_EVENT = new Event("LogSetOwner", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}));
    ;

    @Deprecated
    protected DSProxy(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DSProxy(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DSProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DSProxy(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> setOwner(String owner_) {
        final Function function = new Function(
                FUNC_SETOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(owner_)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> execute(String _target, byte[] _data, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_EXECUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_target), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> execute(byte[] _code, byte[] _data, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_EXECUTE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.DynamicBytes(_code), 
                new org.web3j.abi.datatypes.DynamicBytes(_data)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<String> cache() {
        final Function function = new Function(FUNC_CACHE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setAuthority(String authority_) {
        final Function function = new Function(
                FUNC_SETAUTHORITY, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(authority_)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<TransactionReceipt> setCache(String _cacheAddr) {
        final Function function = new Function(
                FUNC_SETCACHE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cacheAddr)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> authority() {
        final Function function = new Function(FUNC_AUTHORITY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public List<LogNoteEventResponse> getLogNoteEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGNOTE_EVENT, transactionReceipt);
        ArrayList<LogNoteEventResponse> responses = new ArrayList<LogNoteEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogNoteEventResponse typedResponse = new LogNoteEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sig = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.guy = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.foo = (byte[]) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.bar = (byte[]) eventValues.getIndexedValues().get(3).getValue();
            typedResponse.wad = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.fax = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogNoteEventResponse> logNoteEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogNoteEventResponse>() {
            @Override
            public LogNoteEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGNOTE_EVENT, log);
                LogNoteEventResponse typedResponse = new LogNoteEventResponse();
                typedResponse.log = log;
                typedResponse.sig = (byte[]) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.guy = (String) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.foo = (byte[]) eventValues.getIndexedValues().get(2).getValue();
                typedResponse.bar = (byte[]) eventValues.getIndexedValues().get(3).getValue();
                typedResponse.wad = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.fax = (byte[]) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogNoteEventResponse> logNoteEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGNOTE_EVENT));
        return logNoteEventFlowable(filter);
    }

    public List<LogSetAuthorityEventResponse> getLogSetAuthorityEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSETAUTHORITY_EVENT, transactionReceipt);
        ArrayList<LogSetAuthorityEventResponse> responses = new ArrayList<LogSetAuthorityEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogSetAuthorityEventResponse typedResponse = new LogSetAuthorityEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.authority = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogSetAuthorityEventResponse> logSetAuthorityEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogSetAuthorityEventResponse>() {
            @Override
            public LogSetAuthorityEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSETAUTHORITY_EVENT, log);
                LogSetAuthorityEventResponse typedResponse = new LogSetAuthorityEventResponse();
                typedResponse.log = log;
                typedResponse.authority = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogSetAuthorityEventResponse> logSetAuthorityEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSETAUTHORITY_EVENT));
        return logSetAuthorityEventFlowable(filter);
    }

    public List<LogSetOwnerEventResponse> getLogSetOwnerEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGSETOWNER_EVENT, transactionReceipt);
        ArrayList<LogSetOwnerEventResponse> responses = new ArrayList<LogSetOwnerEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogSetOwnerEventResponse typedResponse = new LogSetOwnerEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogSetOwnerEventResponse> logSetOwnerEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogSetOwnerEventResponse>() {
            @Override
            public LogSetOwnerEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGSETOWNER_EVENT, log);
                LogSetOwnerEventResponse typedResponse = new LogSetOwnerEventResponse();
                typedResponse.log = log;
                typedResponse.owner = (String) eventValues.getIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogSetOwnerEventResponse> logSetOwnerEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGSETOWNER_EVENT));
        return logSetOwnerEventFlowable(filter);
    }

    @Deprecated
    public static DSProxy load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DSProxy(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DSProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DSProxy(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DSProxy load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DSProxy(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DSProxy load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DSProxy(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<DSProxy> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cacheAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cacheAddr)));
        return deployRemoteCall(DSProxy.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<DSProxy> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cacheAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cacheAddr)));
        return deployRemoteCall(DSProxy.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DSProxy> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cacheAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cacheAddr)));
        return deployRemoteCall(DSProxy.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<DSProxy> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cacheAddr) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cacheAddr)));
        return deployRemoteCall(DSProxy.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class LogNoteEventResponse {
        public Log log;

        public byte[] sig;

        public String guy;

        public byte[] foo;

        public byte[] bar;

        public BigInteger wad;

        public byte[] fax;
    }

    public static class LogSetAuthorityEventResponse {
        public Log log;

        public String authority;
    }

    public static class LogSetOwnerEventResponse {
        public Log log;

        public String owner;
    }
}
