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
package org.makerdao.mcd.core;

import org.junit.Before;
import org.junit.Test;
import org.makerdao.mcd.contracts.ERC20Token;
import org.makerdao.mcd.exceptions.TokenException;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class AllowanceServiceTest {

    ERC20Token daiToken;
    Map<String, ERC20Token> tokens = new HashMap<>();

    @Before
    public void beforeTest() {
        daiToken = mock(ERC20Token.class);
        tokens.put(TokenSymbols.DAI, daiToken);
    }

    @Test(expected = TokenException.class)
    public void testUnknownTokenRequireAllowance() throws Exception {
        AllowanceService service = new AllowanceServiceImpl(tokens);
        service.requireAllowance("", "", "MKR", BigDecimal.TEN);
    }

    @Test(expected = TokenException.class)
    public void testUnknownTokenRemoveAllowance() throws Exception {
        AllowanceService service = new AllowanceServiceImpl(tokens);
        service.removeAllowance("", "", "MKR");
    }
}
