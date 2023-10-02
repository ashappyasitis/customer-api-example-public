package me.kevin.customerapi.model.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kevin.customerapi.validation.group.NotEmptyGroup;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class DeleteCustomerRequest {
    @NotNull(message = "{me.kevin.customerapi.constraints.NotEmpty.message}", groups = {NotEmptyGroup.class})
    private Long customerCode;

    @NotBlank(message = "{me.kevin.customerapi.constraints.NotBlank.message}", groups = {NotEmptyGroup.class})
    private String updatedBy;

    public DeleteCustomerRequest validate() {
        updatedBy = StringUtils.trim(updatedBy);

        return this;
    }
}
