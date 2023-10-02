package me.kevin.customerapi.model.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import me.kevin.customerapi.iface.Pageable;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SearchCustomerParams implements Pageable {
    @Getter
    String companyName;
    Integer pageNumber;
    Integer pageSize;

    @Override
    public int getPageNumber() {
        return 0;
    }

    @Override
    public int getPageSize() {
        return 0;
    }

    @Override
    public long getOffset() {
        return (long) pageNumber * pageSize;
    }
}
