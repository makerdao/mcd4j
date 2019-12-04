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

import org.junit.Test;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class ExecuteFunctionEncoderTest {

    @Test
    public void testEncodeExitDSExecute() {
        Function function = new Function(
                "exit",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address("0x5aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c"),
                        new org.web3j.abi.datatypes.Address("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb"),
                        new org.web3j.abi.datatypes.generated.Uint256(BigInteger.TEN)),
                Collections.<TypeReference<?>>emptyList());
        String data = ExecuteFunctionEncoder.encodeFunction("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92", function);
        assert data.equals("" +
                "0x1cff79cd" +
                "000000000000000000000000c5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92" +
                "0000000000000000000000000000000000000000000000000000000000000040" +
                "0000000000000000000000000000000000000000000000000000000000000064" +
                "71006c09" +
                "0000000000000000000000005aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c" +
                "000000000000000000000000ea190dbdc7adf265260ec4da6e9675fd4f5a78bb" +
                "000000000000000000000000000000000000000000000000000000000000000a");
    }

    @Test
    public void testEncodeExitAllDSExecute() {
        Function function = new Function(
                "exitAll",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address("0x5aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c"),
                        new org.web3j.abi.datatypes.Address("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb")),
                Collections.<TypeReference<?>>emptyList());
        String data = ExecuteFunctionEncoder.encodeFunction("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92", function);
        assert data.equals("" +
                "0x1cff79cd" +
                "000000000000000000000000c5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92" +
                "0000000000000000000000000000000000000000000000000000000000000040" +
                "0000000000000000000000000000000000000000000000000000000000000064" +
                "c77843b3" +
                "0000000000000000000000005aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c" +
                "000000000000000000000000ea190dbdc7adf265260ec4da6e9675fd4f5a78bb");
    }

    @Test
    public void testEncodeJoinDSExecute() {
        Function function = new Function(
                "join",
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address("0x5aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c"),
                        new org.web3j.abi.datatypes.Address("0xea190dbdc7adf265260ec4da6e9675fd4f5a78bb"),
                        new org.web3j.abi.datatypes.generated.Uint256(BigInteger.ONE)),
                Collections.<TypeReference<?>>emptyList());
        String data = ExecuteFunctionEncoder.encodeFunction("0xc5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92", function);
        assert data.equals("" +
                "0x1cff79cd" +
                "000000000000000000000000c5cc1dfb64a62b9c7bb6cbf53c2a579e2856bf92" +
                "0000000000000000000000000000000000000000000000000000000000000040" +
                "0000000000000000000000000000000000000000000000000000000000000064" +
                "9f6c3dbd" +
                "0000000000000000000000005aa71a3ae1c0bd6ac27a1f28e1415fffb6f15b8c" +
                "000000000000000000000000ea190dbdc7adf265260ec4da6e9675fd4f5a78bb" +
                "0000000000000000000000000000000000000000000000000000000000000001");
    }

}
