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

import org.makerdao.mcd.contracts.DaiJoin;
import org.makerdao.mcd.contracts.DssProxyActionsDsr;
import org.makerdao.mcd.contracts.Pot;
import org.makerdao.mcd.contracts.Vat;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.makerdao.mcd.McdConstants.*;

public class SavingsServiceImpl implements SavingsService {

    Pot pot;
    Vat vat;
    DssProxyActionsDsr dssProxyActionsDsr;
    DaiJoin daiJoin;

    public SavingsServiceImpl(Pot pot, DssProxyActionsDsr dssProxyActionsDsr, Vat vat, DaiJoin daiJoin) {
        this.pot = pot;
        this.vat = vat;
        this.dssProxyActionsDsr = dssProxyActionsDsr;
        this.daiJoin = daiJoin;
    }

    @Override
    public TransactionReceipt join(BigDecimal amountInDai) throws Exception {
        return dssProxyActionsDsr.join(daiJoin.getContractAddress(), pot.getContractAddress(),
                amountInDai.multiply(WAD).toBigInteger()).send();
    }

    @Override
    public TransactionReceipt exit(BigDecimal amountInDai) throws Exception {
        return dssProxyActionsDsr.exit(daiJoin.getContractAddress(), pot.getContractAddress(),
                amountInDai.multiply(WAD).toBigInteger()).send();
    }

    @Override
    public TransactionReceipt exitAll() throws Exception {
        return dssProxyActionsDsr.exitAll(daiJoin.getContractAddress(), pot.getContractAddress()).send();
    }

    @Override
    public BigDecimal getBalanceOf(String guy) throws Exception {
        BigDecimal slice = new BigDecimal(pot.pie(guy).send());
        BigDecimal totalPie = new BigDecimal(pot.Pie().send());

        BigDecimal portion = totalPie.equals(BigDecimal.ZERO) ? totalPie : slice.divide(totalPie, MathContext.DECIMAL128);
        BigDecimal daiInPot = new BigDecimal(vat.dai(pot.getContractAddress()).send()).divide(RAD, MathContext.DECIMAL128);

        return daiInPot.multiply(portion);
    }

    @Override
    public BigDecimal getTotalDai() throws Exception {
        BigDecimal totalPie = new BigDecimal(pot.Pie().send()).divide(WAD, MathContext.DECIMAL128);
        return totalPie.multiply(chi());
    }

    @Override
    public BigDecimal getDsr() throws Exception {
        return new BigDecimal(pot.dsr().send()).divide(RAY, MathContext.DECIMAL128);
    }

    @Override
    public BigDecimal chi() throws Exception {
        return new BigDecimal(pot.chi().send()).divide(RAY, MathContext.DECIMAL128);
    }

}
