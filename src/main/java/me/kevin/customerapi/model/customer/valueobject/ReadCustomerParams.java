package me.kevin.customerapi.model.customer.valueobject;

import lombok.Builder;

@Builder
public record ReadCustomerParams(
        long customerCode
) {
}
