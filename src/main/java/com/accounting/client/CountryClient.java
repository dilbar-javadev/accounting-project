package com.accounting.client;


import com.accounting.dto.CountryDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(url = "https://www.universal-tutorial.com/api", name = "Country-FeignClient")
public interface CountryClient {


    @GetMapping("/getaccesstoken")
    public String getAccessToken(@RequestHeader(value = "api-token") String apiToken, @RequestHeader(value = "user-email") String userEmail);


    @GetMapping("/countries")
    List<CountryDTO> getCountries(@RequestHeader(value = "Authorization") String auth,
                      @RequestHeader(value = "Accept") String accept);


}
