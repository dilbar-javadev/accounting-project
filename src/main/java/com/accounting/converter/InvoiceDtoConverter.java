package com.accounting.converter;

import com.accounting.dto.InvoiceDTO;
import com.accounting.service.InvoiceService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class InvoiceDtoConverter implements Converter<String, InvoiceDTO> {

    private final InvoiceService invoiceService;

    public InvoiceDtoConverter(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public InvoiceDTO convert(String source) {

        if (source == null || source.equals("")) {
            return null;
        }


        return invoiceService.findInvoiceById(Long.parseLong(source));


    }
}
