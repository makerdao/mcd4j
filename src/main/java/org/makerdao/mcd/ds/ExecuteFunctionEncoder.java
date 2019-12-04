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

import org.web3j.abi.DefaultFunctionEncoder;
import org.web3j.abi.TypeEncoder;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Function;
import org.web3j.utils.Numeric;

abstract class ExecuteFunctionEncoder extends DefaultFunctionEncoder {

    /**
     * Encode DSProxy.execute(address _target, bytes _data) function.
     *
     * @param address - target contract address (e.g. DssProxyActionsDsr address)
     * @param function - function to execute on target contract
     *                 e.g. DssProxyActionsDsr.exit(address daiJoin, address pot, uint wad)
     *                      DssProxyActionsDsr.exitAll(address daiJoin, address pot)
     *                      DssProxyActionsDsr.join(address daiJoin, address pot, uint wad)
     *
     * @return data - DSProxy.execute data
     *
     */
    static String encodeFunction(String address, Function function) {
        String data = "0x1cff79cd"; // DSProxy.execute method
        data = data + TypeEncoder.encode(new Address(address)); // _target parameter
        data = data + "0000000000000000000000000000000000000000000000000000000000000040";
        data = data + "0000000000000000000000000000000000000000000000000000000000000064";
        data = data + new String(Numeric.cleanHexPrefix(encode(function)).getBytes()); // _data parameter
        return data;
    }
}
