package me.kevin.customerapi.model.customer.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.kevin.customerapi.validation.group.NotEmptyGroup;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@NoArgsConstructor
public class SearchCustomersRequest {
    @NotBlank(message = "{me.kevin.customerapi.constraints.NotBlank.message}", groups = {NotEmptyGroup.class})
    private String companyName;

    private Integer pageNumber;
    private Integer pageSize;

    public SearchCustomersRequest validate() {
        companyName = StringUtils.trim(companyName);

        // Default for the paging
        if (pageNumber == null) {
            pageNumber = 0;
        }
        if (pageSize == null) {
            pageSize = 20;
        }

        return this;
    }
}
