package me.kevin.customerapi.domain.customer;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.SearchCustomerParams;
import me.kevin.customerapi.model.customer.dto.SearchCustomersRequest;
import me.kevin.customerapi.model.customer.dto.SearchCustomersResponse;
import me.kevin.customerapi.model.entity.Customer;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SearchCustomerHandler {
    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public SearchCustomersResponse search(SearchCustomersRequest request) {
        List<Customer> customerList = null;
        SearchCustomerParams searchCustomerParams = customerServiceMapper.toSearchCustomerParams(request);

        long totalElements = customerMapper.selectCountCustomers(searchCustomerParams);
        if (totalElements > 0) {
            customerList = customerMapper.selectCustomers(searchCustomerParams);
        }

        return customerServiceMapper.toSearchCustomersResponse(customerList, searchCustomerParams, totalElements);
    }
}
