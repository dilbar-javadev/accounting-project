package com.accounting.dto;

import com.accounting.enums.Months;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDto {
    private Long id;
    private CompanyDto company;
    private Months month;
    private int amount;
    private boolean isPaid;
    private LocalDate year;
}
