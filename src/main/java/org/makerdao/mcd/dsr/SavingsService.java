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
package org.makerdao.mcd.dsr;

import java.math.BigDecimal;

public interface SavingsService {

    void join(BigDecimal amountInDai);

    void exit(BigDecimal amountInDai);

    void exitAll();

    BigDecimal getBalance() throws Exception;

    BigDecimal getBalanceOf(String address) throws Exception;

    BigDecimal getTotalDai() throws Exception;

    BigDecimal getDsr() throws Exception;

    BigDecimal chi() throws Exception;

    String getDssProxyActionsDsrAddress();

    String getPotAddress();
}
