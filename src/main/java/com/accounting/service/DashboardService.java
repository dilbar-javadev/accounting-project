package com.accounting.service;

import com.accounting.dto.ExchangeRate;
import com.accounting.dto.InvoiceDTO;

import java.util.List;
import java.util.Map;

public interface DashboardService {

    Map<String, Double> profitLoss();

    ExchangeRate listUsdExchangeRate();

    List<InvoiceDTO> listLatestThreeApprovedInvoices();
}
