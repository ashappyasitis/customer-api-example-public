package me.kevin.customerapi.model.customer.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kevin.customerapi.validation.group.NotEmptyGroup;


@Getter
@Setter
@NoArgsConstructor
public class ReadCustomerRequest {
    @NotNull(message = "{me.kevin.customerapi.constraints.NotEmpty.message}", groups = {NotEmptyGroup.class})
    private Long customerCode;
}
