package me.kevin.customerapi.model.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateCustomerParams {
    private Long customerCode;
    private String customerType;
    private String companyName;
    private String contractType;
    private String operationType;
    private String createdBy;
    private String updatedBy;
}
