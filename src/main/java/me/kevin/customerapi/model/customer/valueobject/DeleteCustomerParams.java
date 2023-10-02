package me.kevin.customerapi.model.customer.valueobject;

import lombok.Builder;

@Builder
public record DeleteCustomerParams(
        long customerCode,
        String updatedBy
) {
}
