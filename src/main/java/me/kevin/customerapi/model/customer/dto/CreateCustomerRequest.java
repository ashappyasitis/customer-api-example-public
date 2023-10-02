package me.kevin.customerapi.model.customer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kevin.customerapi.validation.group.NotEmptyGroup;
import me.kevin.customerapi.validation.group.PatternCheckGroup;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class CreateCustomerRequest {

    @Pattern(regexp = "^(ENTERPRISE|INDIVIDUAL)$", message = "{me.kevin.customerapi.constraints.PatternCheck.message}", groups = {PatternCheckGroup.class})
    private String customerType;

    @NotBlank(message = "{me.kevin.customerapi.constraints.NotBlank.message}", groups = {NotEmptyGroup.class})
    private String companyName;

    @Pattern(regexp = "^(DIRECT)$", message = "{me.kevin.customerapi.constraints.PatternCheck.message}", groups = {PatternCheckGroup.class})
    private String contractType;

    @Pattern(regexp = "^(DIRECT)$", message = "{me.kevin.customerapi.constraints.PatternCheck.message}", groups = {PatternCheckGroup.class})
    private String operationType;

    @NotBlank(message = "{me.kevin.customerapi.constraints.NotBlank.message}", groups = {NotEmptyGroup.class})
    private String createdBy;

    public CreateCustomerRequest validate() {
        companyName = StringUtils.trim(companyName);
        createdBy = StringUtils.trim(createdBy);

        return this;
    }
}
