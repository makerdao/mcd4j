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

import org.makerdao.mcd.contracts.*;
import org.makerdao.mcd.ds.DSProxyService;
import org.web3j.abi.datatypes.Function;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import java.math.BigDecimal;
import java.math.MathContext;

import static org.makerdao.mcd.McdConstants.*;

public class SavingsServiceImpl implements SavingsService {

    Pot pot;
    Vat vat;
    DSProxyService dsProxyService;
    DssProxyActionsDsr dssProxyActionsDsr;
    DaiJoin daiJoin;

    public SavingsServiceImpl(Pot pot, DSProxyService dsProxyService, DssProxyActionsDsr dssProxyActionsDsr, Vat vat, DaiJoin daiJoin) {
        this.pot = pot;
        this.vat = vat;
        this.dsProxyService = dsProxyService;
        this.dssProxyActionsDsr = dssProxyActionsDsr;
        this.daiJoin = daiJoin;
    }

    @Override
    public TransactionReceipt join(DSProxy dsProxy, BigDecimal amountInDai) throws Exception {
        Function joinFunction = dssProxyActionsDsr.getJoinFunction(daiJoin.getContractAddress(), pot.getContractAddress(),
                amountInDai.multiply(WAD).toBigInteger());
        return dsProxyService.execute(dsProxy, dssProxyActionsDsr.getContractAddress(), joinFunction);
    }

    @Override
    public TransactionReceipt exit(DSProxy dsProxy, BigDecimal amountInDai) throws Exception {
        Function exitFunction = dssProxyActionsDsr.getExitFunction(daiJoin.getContractAddress(), pot.getContractAddress(),
                amountInDai.multiply(WAD).toBigInteger());
        return dsProxyService.execute(dsProxy, dssProxyActionsDsr.getContractAddress(), exitFunction);
    }

    @Override
    public TransactionReceipt exitAll(DSProxy dsProxy) throws Exception {
        Function exitAllFunction = dssProxyActionsDsr.getExitAllFunction(daiJoin.getContractAddress(), pot.getContractAddress());
        return dsProxyService.execute(dsProxy, dssProxyActionsDsr.getContractAddress(), exitAllFunction);
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
