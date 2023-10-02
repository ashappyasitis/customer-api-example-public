package me.kevin.customerapi.model.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.kevin.customerapi.iface.Pageable;
import me.kevin.customerapi.model.valueobject.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class SearchCustomersResponse {
    private Page page;
    private List<CustomerResponse> customerResponses;

    public SearchCustomersResponse(Pageable pageable) {
        this.page = new Page(pageable, 0L);
        this.customerResponses = new ArrayList<>();
    }

    public SearchCustomersResponse(List<CustomerResponse> customerResponseList, Pageable pageable, long totalElements) {
        this.page = new Page(pageable, totalElements);
        this.customerResponses = customerResponseList;
    }
}
