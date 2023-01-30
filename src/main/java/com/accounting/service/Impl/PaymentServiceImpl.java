package com.accounting.service.Impl;

import com.accounting.repository.PaymentRepository;
import com.accounting.dto.CompanyDto;
import com.accounting.dto.PaymentDto;
import com.accounting.entity.Company;
import com.accounting.entity.Payment;
import com.accounting.enums.Months;
import com.accounting.mapper.MapperUtil;
import com.accounting.service.CompanyService;
import com.accounting.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MapperUtil mapperUtil;
    private final CompanyService companyService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MapperUtil mapperUtil, CompanyService companyService) {
        this.paymentRepository = paymentRepository;
        this.mapperUtil = mapperUtil;
        this.companyService = companyService;
    }

    @Override
    public List<PaymentDto> getAllPaymentsByYear(int year) {

        LocalDate instance = LocalDate.now().withYear(year);
        LocalDate dateStart = instance.with(firstDayOfYear());
        LocalDate dateEnd = instance.with(lastDayOfYear());
        var company = companyService.getCompanyOfLoggedInUser();

        List<Payment> payments = paymentRepository.findAllByYearBetweenAndCompanyId(dateStart, dateEnd, company.getId());
        return payments.stream()
                .map(obj -> mapperUtil.convert(obj, new PaymentDto()))
                .sorted(Comparator.comparing(PaymentDto::getMonth))
                .collect(Collectors.toList());
    }

    @Override
    public void createPaymentsIfNotExist(int year) {

        LocalDate instance = LocalDate.now().withYear(year);
        LocalDate dateStart = instance.with(firstDayOfYear());
        LocalDate dateEnd = instance.with(lastDayOfYear());
        CompanyDto companyDto = companyService.getCompanyOfLoggedInUser();

        List<Payment> payments = paymentRepository.findAllByYearBetweenAndCompanyId(dateStart, dateEnd, companyDto.getId());


        if (payments.size() == 0) {
            for (Months month : Months.values()){
                Payment payment = new Payment();
                payment.setMonth(month);
                payment.setYear(LocalDate.now().withYear(year));
                payment.setPaid(false);
                payment.setAmount(250);
                payment.setCompany(mapperUtil.convert(companyDto, new Company()));
                paymentRepository.save(payment);
            }
        }

    }

    @Override
    public PaymentDto getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id).get();
        return mapperUtil.convert(payment, new PaymentDto());
    }

    @Override
    public PaymentDto updatePayment(Long id) {

        Payment payment = paymentRepository.findById(id).get();
        payment.setPaid(true);
        return mapperUtil.convert(paymentRepository.save(payment), new PaymentDto());
    }


}