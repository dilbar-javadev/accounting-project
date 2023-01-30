package com.accounting.controller;

import com.accounting.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping
    public String retrieveDashboard(Model model) {

        model.addAttribute("summaryNumbers", dashboardService.profitLoss());
        model.addAttribute("invoices", dashboardService.listLatestThreeApprovedInvoices());
        model.addAttribute("exchangeRates", dashboardService.listUsdExchangeRate());


        return "/dashboard";
    }


}
