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
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface DSProxyService {

    DSProxy getProxy(String proxyAddress) throws Exception;

    DSProxy getProxy(String ownerAddress, boolean create) throws Exception;

    TransactionReceipt execute(DSProxy dsProxy, String address, Function function) throws Exception;

    String getOwner(DSProxy dsProxy) throws Exception;

    TransactionReceipt setOwner(DSProxy dsProxy, String owner) throws Exception;
}
