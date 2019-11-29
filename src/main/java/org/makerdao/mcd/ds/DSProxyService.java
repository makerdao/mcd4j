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

import org.web3j.protocol.core.methods.response.TransactionReceipt;

public interface DSProxyService {

    String getProxyAddress(String ownerAddress, boolean create) throws Exception;

    String getOwner(String proxyAddress) throws Exception;

    TransactionReceipt setOwner(String proxyAddress, String owner) throws Exception;
}
