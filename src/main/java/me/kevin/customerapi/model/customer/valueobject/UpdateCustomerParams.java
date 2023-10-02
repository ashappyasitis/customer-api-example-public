package me.kevin.customerapi.model.customer.valueobject;

import lombok.Builder;

@Builder
public record UpdateCustomerParams(
        long customerCode,
        String customerType,
        String companyName,
        String contractType,
        String operationType,
        String updatedBy
) {
}
