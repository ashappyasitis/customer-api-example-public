package me.kevin.customerapi.model.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class CreateCustomerResponse {
    private long customerCode;
    private String companyName;
}
