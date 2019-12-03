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

    static String encodeFunction(String address, Function function) {
        String data = "0x1cff79cd";
        data = data + TypeEncoder.encode(new Address(address));
        data = data + "0000000000000000000000000000000000000000000000000000000000000040";
        data = data + "0000000000000000000000000000000000000000000000000000000000000064";
        data = data + new String(Numeric.cleanHexPrefix(encode(function)).getBytes());
        return data;
    }
}
