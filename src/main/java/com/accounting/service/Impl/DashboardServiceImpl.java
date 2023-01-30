package com.accounting.service.Impl;

import com.accounting.client.ExchangeClient;
import com.accounting.dto.ExchangeRate;
import com.accounting.dto.InvoiceDTO;
import com.thegogetters.accounting.dto.*;
import com.accounting.service.DashboardService;
import com.accounting.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {

    private final ExchangeClient exchangeClient;
    private final InvoiceService invoiceService;

    public DashboardServiceImpl(ExchangeClient exchangeClient, InvoiceService invoiceService1) {
        this.exchangeClient = exchangeClient;
        this.invoiceService = invoiceService1;
    }

    //----------------------------------------------------------------------------------///


    @Override
    public Map<String, Double> profitLoss() {
        return invoiceService.calculateCostSummary();
    }

    @Override
    public ExchangeRate listUsdExchangeRate() {

        ExchangeRate exchangeRate = new ExchangeRate();

        double euro = exchangeClient.getUsdExchangeRate().getUsd().getEur();
        exchangeRate.setEuro(BigDecimal.valueOf(euro).setScale(2, RoundingMode.CEILING));

        double gdb = exchangeClient.getUsdExchangeRate().getUsd().getGbp();
        exchangeRate.setBritishPound(BigDecimal.valueOf(gdb).setScale(2,RoundingMode.CEILING));

        double cad = exchangeClient.getUsdExchangeRate().getUsd().getCad();
        exchangeRate.setCanadianDollar(BigDecimal.valueOf(cad).setScale(2,RoundingMode.CEILING));

        double jpy = exchangeClient.getUsdExchangeRate().getUsd().getJpy();
        exchangeRate.setJapaneseYen(BigDecimal.valueOf(jpy).setScale(2,RoundingMode.CEILING));

        double inr = exchangeClient.getUsdExchangeRate().getUsd().getInr();
        exchangeRate.setIndianRupee(BigDecimal.valueOf(inr).setScale(2,RoundingMode.CEILING));


        return exchangeRate;

    }

    //---------------------------------------------------------------------------------//

    @Override
    public List<InvoiceDTO> listLatestThreeApprovedInvoices() {

        return invoiceService.lastThreeTransactions();


    }

    //-----------------------------------------------------------------------------------//



}
