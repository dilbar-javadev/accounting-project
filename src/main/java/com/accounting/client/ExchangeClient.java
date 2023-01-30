package com.accounting.client;

import com.accounting.dto.ExchangeRateDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(url = "https://cdn.jsdelivr.net", name = "Exchange-Rate")
public interface ExchangeClient {

    @GetMapping("/gh/fawazahmed0/currency-api@1/latest/currencies/usd.json")
    ExchangeRateDto getUsdExchangeRate();



}
