package me.kevin.customerapi.model.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CustomerResponse {
    private Long customerCode;
    private String customerType;
    private String companyName;
    private String contractType;
    private String operationType;
}
