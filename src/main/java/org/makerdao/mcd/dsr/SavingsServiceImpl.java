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
import java.math.MathContext;

import static org.makerdao.mcd.McdConstants.*;

public class SavingsServiceImpl implements SavingsService {

    Pot pot;
    Vat vat;
    DssProxyActionsDsr dssProxyActionsDsr;

    public SavingsServiceImpl(Pot pot, DssProxyActionsDsr dssProxyActionsDsr, Vat vat) {
        this.pot = pot;
        this.vat = vat;
        this.dssProxyActionsDsr = dssProxyActionsDsr;
    }

    @Override
    public void join(BigDecimal amountInDai) {
    }

    @Override
    public void exit(BigDecimal amountInDai) {
    }

    @Override
    public void exitAll() {
    }

    @Override
    public BigDecimal getBalance() {
        return null;
    }

    @Override
    public BigDecimal getBalanceOf(String guy) throws Exception {
        BigDecimal slice = new BigDecimal(this.pot.pie(guy).send()).divide(WAD, MathContext.UNLIMITED);
        BigDecimal totalPie = new BigDecimal(this.pot.Pie().send()).divide(WAD, MathContext.UNLIMITED);

        BigDecimal portion = totalPie.equals(BigDecimal.ZERO) ? totalPie : slice.divide(totalPie, MathContext.UNLIMITED);
        BigDecimal daiInPot = new BigDecimal(vat.dai(this.pot.getContractAddress()).send()).divide(RAD, MathContext.UNLIMITED);

        return daiInPot.multiply(portion);
    }

    @Override
    public BigDecimal getTotalDai() throws Exception {
        BigDecimal totalPie = new BigDecimal(this.pot.Pie().send()).divide(WAD, MathContext.UNLIMITED);
        return totalPie.multiply(chi());
    }

    @Override
    public BigDecimal getDsr() throws Exception {
        return new BigDecimal(this.pot.dsr().send()).divide(RAY, MathContext.UNLIMITED);
    }

    @Override
    public BigDecimal chi() throws Exception {
        return new BigDecimal(this.pot.chi().send()).divide(RAY, MathContext.UNLIMITED);
    }

    @Override
    public String getDssProxyActionsDsrAddress() {
        return this.dssProxyActionsDsr.getContractAddress();
    }

    @Override
    public String getPotAddress() {
        return this.pot.getContractAddress();
    }
}
